package be.pppt.v1.Items;

import java.awt.Graphics2D;

import be.pppt.v1.Point;
import be.pppt.v1.Projectile;
import be.pppt.v1.Stctd;


public class Shotgun extends ShootItem {

	private int amoutInABunch = 10;
	private static String nameImage = "shotgun";
	
	public Shotgun(Point position, Projectile modelProjectile, int accuracy,
			int amoutInABunch) {
		super(position, nameImage, modelProjectile, accuracy, 6, 18, 0, 700, 3000, false, false, false, true);
		// TODO Auto-generated constructor stub
		this.amoutInABunch = amoutInABunch;
	}
	
	public void useMe(Stctd stctdUsesIt) {
		for(int i = 0; i < amoutInABunch; i++) {
			shootProjectile(stctdUsesIt);
		}
	}

	@Override
	public void startUse(Stctd stctdUsesIt) {
		// TODO Auto-generated method stub
		super.startUse(stctdUsesIt);
	}

	@Override
	public void stillUsing(int timePast, Stctd stctdUsesIt) {
		// TODO Auto-generated method stub
		super.stillUsing(timePast, stctdUsesIt);
	}
	
	
	
	public int getAmoutInABunch() {
		return amoutInABunch;
	}

	public void setAmoutInABunch(int amoutInABunch) {
		this.amoutInABunch = amoutInABunch;
	}
	
	

	@Override
	public void fire() {
		// TODO Auto-generated method stub
		Stctd stctdToFire = getStctdThatUsesMe();
		
		for(int i = 0; i < amoutInABunch; i++) {
			shootProjectile(stctdToFire);
		}
	}

	
	
	@Override
	public void drawItemInfo(Graphics2D g2d, Point beginPoint) {
		// TODO Auto-generated method stub
		super.drawItemInfo(g2d, beginPoint);
	}


}
