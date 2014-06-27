package be.pppt.v1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.util.ArrayList;

import be.pppt.v1.Items.Item;
import be.pppt.v1.Items.SubMachineGun;
import be.pppt.v1.Items.ShootItem;
import be.pppt.v1.Items.Shotgun;
import be.pppt.v1.Items.Sword;


public class User extends Stctd implements DoSomething {

	public final static int userTeamNumber = 1;
	
	private Point oldMoveDirection = new Point(0,0);
	private Point moveDirection = new Point(0, 0);
	private GameWindow myWindow;

	private Vehicle myVehicle = null;

	private Point mousePosition = new Point(0,0);	
	

	public User(Point position, double lives, double maxLives, double speed,
			double sprintFactor, double workFactor, String name) {
		super(position, lives, maxLives, speed, sprintFactor, workFactor, name, true, userTeamNumber);
		// TODO Auto-generated constructor stub
		
		getMyInventory().addItem(new SubMachineGun(new Point(0, 0), new Projectile(new Point(0,0), new Point(0,0), "proj", 1, 500, null, 1000)));
		getMyInventory().addItem(new Shotgun(new Point(0, 0), new Projectile(new Point(0,0), new Point(0,0), "proj", 0.5, 500, null, 1000), 20, 10));
		getMyInventory().addItem(new Sword(Point.nullPoint(), "sword", 2, 70, Math.PI / 2, 100, 5));
	}



	@Override
	public void doSomething(int timePast) {
		
		PointerInfo mouseInfo = MouseInfo.getPointerInfo();
		double mouseX = mouseInfo.getLocation().getX();
		double mouseY = mouseInfo.getLocation().getY();
		
		double frameX = getMyWindow().getLocationOnScreen().getX();
		double frameY = getMyWindow().getLocationOnScreen().getY();
				
		getDirection().setX(mouseX - frameX - getMyWindow().getWidth() / 2);
		getDirection().setY(mouseY - frameY - getMyWindow().getHeight() / 2);
		
		mousePosition.set(getDirection());
		
		usingItem(timePast);
		
		getMyBubble().update(timePast);
		
		if (myVehicle == null) {
			this.move(moveDirection, timePast);
		}
	}

	public void inOrOutCar() {
		if (myVehicle == null) {
			tryToGetInVehicle();
		}
		else {
			getOutVehice();
		}
	}
	
	public void getOutVehice() {
		Point possibleNewPos = getMyVehicle().getDropOutPosition(this);
		if (possibleNewPos != null) {
			this.getPosition().set(possibleNewPos);
			myVehicle.removePassenger(this);
		
			myVehicle = null;
		}
		else {
			//System.out.println("NEE");
		}
		
	}
	
	public void tryToGetInVehicle() {

		ArrayList<Stctd> StctdsInArea = getMyWindow().stctdsAroundObject(this, 1.5);
		
		for (int i = 0; i < StctdsInArea.size(); i++) {
			if (StctdsInArea.get(i) instanceof Vehicle) {
				Vehicle askPlace = (Vehicle) (StctdsInArea.get(i));
				if (askPlace.addPassenger(this)) {
					myVehicle = askPlace;

					break;
				}
			}
		}

	}

	public Point getMoveDirection() {
		return new Point(moveDirection);
	}

	public GameWindow getMyWindow() {
		return myWindow;
	}

	public void setMyWindow(GameWindow myWindow) {
		this.myWindow = myWindow;
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
	
	
	public void drawInfo(Graphics2D g2d) {
		int widthWindow = this.getMyWindow().getWidth();
		int heightWindow = this.getMyWindow().getHeight();
		
		Item carriedItem = getMyInventory().getItem();
		
		g2d.setColor(Color.orange);
		
		
		getMyInventory().drawInventory(g2d, new Point(0, 0));
		//drawWeaponIcon(g2d, new Point(widthWindow - 44, heightWindow - 44), carriedItem);
		//drawBulletsInWeapon(g2d, new Point(widthWindow - 100, heightWindow - 20));
	}
	

	public void goUp() {
		switch ((int) moveDirection.getY()) {
		case -1:
			moveDirection.setY(-1);

			break;
		case 0:
			moveDirection.setY(-1);

			break;
		case 1:
			moveDirection.setY(0);

			break;
		default:
			moveDirection.setY(-1);
		}
	}

	public void stopUp() {
		switch ((int) moveDirection.getY()) {
		case -1:
			moveDirection.setY(0);

			break;
		case 0:
			moveDirection.setY(1);

			break;
		case 1:
			moveDirection.setY(1);

			break;
		default:
			moveDirection.setY(1);
		}
	}

	public void goDown() {
		switch ((int) moveDirection.getY()) {
		case -1:
			moveDirection.setY(0);

			break;
		case 0:
			moveDirection.setY(1);

			break;
		case 1:
			moveDirection.setY(1);

			break;
		default:
			moveDirection.setY(1);
		}
	}

	public void stopDown() {
		switch ((int) moveDirection.getY()) {
		case -1:
			moveDirection.setY(-1);

			break;
		case 0:
			moveDirection.setY(-1);

			break;
		case 1:
			moveDirection.setY(0);

			break;
		default:
			moveDirection.setY(-1);
		}
	}

	public void goLeft() {
		switch ((int) moveDirection.getX()) {
		case -1:
			moveDirection.setX(-1);

			break;
		case 0:
			moveDirection.setX(-1);

			break;
		case 1:
			moveDirection.setX(0);

			break;
		default:
			moveDirection.setX(-1);
		}
	}

	public void stopLeft() {
		switch ((int) moveDirection.getX()) {
		case -1:
			moveDirection.setX(0);

			break;
		case 0:
			moveDirection.setX(1);

			break;
		case 1:
			moveDirection.setX(1);

			break;
		default:
			moveDirection.setX(1);
		}
	}

	public void goRight() {
		switch ((int) moveDirection.getX()) {
		case -1:
			moveDirection.setX(0);

			break;
		case 0:
			moveDirection.setX(1);

			break;
		case 1:
			moveDirection.setX(1);

			break;
		default:
			moveDirection.setX(1);
		}
	}

	public void stopRight() {
		switch ((int) moveDirection.getX()) {
		case -1:
			moveDirection.setX(-1);

			break;
		case 0:
			moveDirection.setX(-1);

			break;
		case 1:
			moveDirection.setX(0);

			break;
		default:
			moveDirection.setX(-1);
		}
	}
	
	public void doesMoveDirectionChange() {
		if (oldMoveDirection.getX() != moveDirection.getX() || oldMoveDirection.getY() != moveDirection.getY()) {
			setSprinting(false);
		}
		
		oldMoveDirection.set(moveDirection);
	}
	
	
	public void tryPickUpItem() {
		Point midPoint = this.getPosition();
		Size bufSize = this.getMySize();
		
		Point beginPoint = new Point(midPoint);
		beginPoint.countUpPoint(new Point( - bufSize.getWidth(), - bufSize.getHeight()));
		Point endPoint = new Point(midPoint);
		endPoint.countUpPoint(new Point( bufSize.getWidth(), bufSize.getHeight()));
		
		Item maybePickUp = this.getMyWindow().itemInAnArea(beginPoint, endPoint);
		if (maybePickUp != null) {
			
			if (this.getMyInventory().addItem(maybePickUp)){
				this.getMyWindow().removeItem(maybePickUp);
			}
		}
		
	}
	
	
	public Vehicle getMyVehicle() {
		return myVehicle;
	}

	public void setMyVehicle(Vehicle myVehicle) {
		this.myVehicle = myVehicle;
	}
}
