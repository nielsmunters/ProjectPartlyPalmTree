package be.pppt.v1;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Projectile extends SomethingOnTheScene implements DoSomething {

	
	private double damage = 1;
	private int timeLeft = 500;
	
	private SomethingOnTheScene parent = null;

	private double speed = 1000;
	/**
	 * @param args
	 */

	
	public Projectile(Projectile toCopyProjectile) {
		// TODO Auto-generated constructor stub
		super(new Point(0,0), null, toCopyProjectile.getName());
		this.damage = toCopyProjectile.getDamage();
		this.timeLeft = toCopyProjectile.getTimeLeft();
		this.timeLeft = (int)(Math.random()*timeLeft/5 + timeLeft - timeLeft/10);
		
		this.speed = toCopyProjectile.getSpeed();
		//this.speed = (int)(Math.random()*speed/5 + speed - speed/10);
		this.speed = speed;
	}
	
	public Projectile(Point position, Point direction, String name,
			double damage, int timeLeft, SomethingOnTheScene parent,
			double speed) {
		super(position, direction, name);
		this.damage = damage;
		this.timeLeft = (int)(Math.random()*timeLeft/5 + timeLeft - timeLeft/10);
		this.parent = parent;
		//this.speed = (int)(Math.random()*speed/5 + speed - speed/10);
		this.speed = speed;
	}

	public void paint(Graphics2D g2d)
	{
		AffineTransform old = g2d.getTransform();
		g2d.rotate(this.getDirection().getRotation());
		
		super.paint(g2d);
		
		g2d.setTransform(old);
	}
	
	public void move(int timePast) {
		double percentageX = getDirection().getX() / getDirection().distance();
		double percentageY = getDirection().getY() / getDirection().distance();
		
		double timeFactor = timePast / 1000.0;
		
		double movementInX = speed * percentageX * timeFactor;
		double movementInY = speed * percentageY * timeFactor;
		
		getPosition().setX(getPosition().getX() + movementInX);
		getPosition().setY(getPosition().getY() + movementInY);
	}

	@Override
	public void doSomething(int timePast) {
		// TODO Auto-generated method stub
		move(timePast);
		
		
		
		Stctd maybeTouch = getMyWindow().oneStctdAroundObject(this);
		
		if (maybeTouch != null) { 
			touched(maybeTouch);
		}
		
		timeLeft -= timePast;
		
		if (timeLeft < 0) {
			die();
		}
	}
	
	public int getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	
	public void die() {
		getMyWindow().removeProjectile(this);
	}
	
	public void touched(Stctd aThing) {
		if (aThing == parent)
		{			
			
		}
		else if (parent instanceof User && aThing == ((User)parent).getMyVehicle()) {
			
		}
		else {
			aThing.takeDamage(damage);
			//aThing.getMyForce().addForce(this.getDirection(), (int) (damage * 10));
			timeLeft = 0;
		}
		
	}
	
	
	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public SomethingOnTheScene getParent() {
		return parent;
	}

	public void setParent(SomethingOnTheScene parent) {
		this.parent = parent;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
