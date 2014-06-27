package be.pppt.v1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import be.pppt.v1.Items.Item;


public abstract class Stctd extends SomethingOnTheScene {

	private double lives = 2;
	private double maxLives = 6;
	private Force myForce = new Force();
	private double speed = 100;
	private double sprintFactor = 1.5;
	private double workFactor = 0.5;
	private Inventory myInventory = new Inventory(10);
	
	private int teamNumber = 0;


	private boolean isSprinting = false;
	private boolean isWorking = false;
	
	private SpeechBubble myBubble = new SpeechBubble();


	private boolean drawItem = false;


	public Stctd(Point position, double lives, double maxLives, double speed,
			double sprintFactor, double workFactor, String name, boolean drawItem, int teamNumber) {
		super(position, new Point(10,0), name);
		
		this.lives = lives;
		this.maxLives = maxLives;
		this.speed = speed;
		this.sprintFactor = sprintFactor;
		this.workFactor = workFactor;
		this.drawItem = drawItem;
		
		this.teamNumber = teamNumber;
	}
	
	public boolean move(Point directionToWalkTo, int timePast) {
		//if (myForce.isForceMoving()) {
		//	myForce.forceMove(timePast, this);
		//	return false;
		//}
		//else {
			return moveNormal(directionToWalkTo, timePast);
		//}
	}
	//om te moven volgens het target
	public boolean move(int timePast)
	{
		return move(getDirection(), timePast);
	}
	//bewegen volgens een gegeven richting
	public boolean moveNormal(Point directionToWalkTo, int timePast)
	{
		double percentageX = directionToWalkTo.getX() / directionToWalkTo.distance();
		double percentageY = directionToWalkTo.getY() / directionToWalkTo.distance();
		
		double timeFactor = timePast / 1000.0;
		
		boolean returnBool = true;
		
		double factor = 1;
		if (isSprinting)
		{
			factor *= sprintFactor;
			//System.out.println(isSprinting() + "" + factor);
		}
		else if (isWorking)
		{
			factor *= workFactor;
		}
		
		double movementInX = speed * factor * percentageX * timeFactor;
		double movementInY = speed * factor * percentageY * timeFactor;
		
		getPosition().setX(getPosition().getX() + movementInX);
		
		if (getMyWindow().oneStctdAroundObject(this) != null)
		{
			//System.out.println(this + " - " + getMyWindow().oneStctdAroundObject(this) + this.getMySize().getWidth());
			getPosition().setX(getPosition().getX() - movementInX);
			if(this instanceof Vehicle) {
				//((Vehicle)this).vehicleCollide();
				setSpeed(0);
			}
			
			returnBool = false;
		}
		
		getPosition().setY(getPosition().getY() + movementInY);
		
		if (getMyWindow().oneStctdAroundObject(this) != null)
		{
			//System.out.println(this + " - " + getMyWindow().oneStctdAroundObject(this) + this.getMySize().getWidth());
			getPosition().setY(getPosition().getY() - movementInY);
			if(this instanceof Vehicle) {
				//((Vehicle)this).vehicleCollide();
				setSpeed(0);
			}
			
			returnBool = false;
		}
		
		return returnBool;
	}
	
	public boolean checkLive() {
		if (lives <= 0) {
			die();
			return true;
		}
		
		return false;
	}
	
	public void paintLevel1(Graphics2D g2d) {
		//collision circle
		
		/*Point relPos = getMyWindow().getDrawingCoordinates(this);
		
		int x = (int)relPos.getX();
		int y = (int)relPos.getY();
		
		int width = (int) (getMySize().getWidth() / 0.707106781);
		int height = (int) (getMySize().getHeight() / 0.707106781);
		
		int newX = x - width/2;
		int newY = y - height/2;
		
		g2d.setColor(Color.red);
		g2d.fillRect(newX, newY, width, 3);
		
		double livesLeft = lives / maxLives;
		
		g2d.setColor(Color.green);
		g2d.fillRect(newX, newY, (int) (width * livesLeft), 3);*/
	}
	
	public void paint(Graphics2D g2d)
	{
		AffineTransform old = g2d.getTransform();
		g2d.rotate(this.getDirection().getRotation());
		
		if (drawItem) {
			Point weaponPoint = new Point(this.getPosition());
			
		
			weaponPoint = this.getMyWindow().getDrawingCoordinatesRotated(weaponPoint, getDirection());
			weaponPoint.setX(weaponPoint.getX() + this.getMySize().getWidth() / 1.5);
			//weaponPoint.setY(weaponPoint.getY() + this.getMySize().getHeight() / 4);
			
			this.getMyInventory().getItem().drawItemInHand(g2d, weaponPoint);
		}
		
		super.paint(g2d);		
		
		g2d.setTransform(old);
	}
	
	
	public void takeDamage(double damage)
	{
		lives -= damage;
	}
	
	public void die()
	{
		dropEverything();
		
		getMyWindow().removeStctd(this);
	}
	public void dropEverything() {
		int amoutItems = myInventory.getAmoutItemsInventory();
		
		for (int i = 0; i < amoutItems; i++)
		{
			this.dropItem(0);
		}
	}
	
	
	public void dropItem() {
		dropItem(myInventory.getPointer());
	}
	public void dropItem(int pointer) {
		Item itemToDrop = this.getMyInventory().getItem(pointer);
		
		if (this.getMyInventory().removeItem()) {
			this.getMyWindow().addItem(itemToDrop);
			
			itemToDrop.setPosition(this.getPosition());
		}
	}
	
	public void startUseItem() {
		getMyInventory().getItem().startUse(this);
	}
	public void usingItem(int timePast) {
		getMyInventory().getItem().stillUsing(timePast, this);
	}
	public void stopUseItem() {
		getMyInventory().getItem().stopUse(this);
	}
	
	
	/***********************************
	 * Allemaal getters en setters!! :o
	 * @return
	 */

	public double getLives() {
		return lives;
	}

	public void setLives(double lives) {
		this.lives = lives;
	}
	
	public Inventory getMyInventory() {
		return myInventory;
	}

	public void setMyInventory(Inventory myInventory) {
		this.myInventory = myInventory;
	}

	public double getMaxLives() {
		return maxLives;
	}

	public void setMaxLives(double maxLives) {
		this.maxLives = maxLives;
	}

	public Force getMyForce() {
		return myForce;
	}
	
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getSprintFactor() {
		return sprintFactor;
	}

	public void setSprintFactor(double sprintFactor) {
		this.sprintFactor = sprintFactor;
	}

	public double getWorkFactor() {
		return workFactor;
	}

	public void setWorkFactor(double workFactor) {
		this.workFactor = workFactor;
	}

	
	
	public int getTeamNumber() {
		return teamNumber;
	}

	public void setTeamNumber(int teamNumber) {
		this.teamNumber = teamNumber;
	}
	


	public boolean isSprinting() {
		return isSprinting;
	}


	public void setSprinting(boolean isSprinting) {
		this.isSprinting = isSprinting;
	}


	public boolean isWorking() {
		return isWorking;
	}


	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}
	
	
	public SpeechBubble getMyBubble() {
		return myBubble;
	}

	
}
