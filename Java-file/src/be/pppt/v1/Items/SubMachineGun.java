package be.pppt.v1.Items;

import java.awt.Graphics2D;

import be.pppt.v1.Point;
import be.pppt.v1.Projectile;
import be.pppt.v1.Stctd;


public class SubMachineGun extends ShootItem {

	private static String nameImage = "smg";
	
	public SubMachineGun(Point position, Projectile modelProjectile) {
		super(position, nameImage, modelProjectile, 3, 50, 180, 3, 50, 2000, true, true, false, true);
		// TODO Auto-generated constructor stub
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

	@Override
	public void stopUse(Stctd stctdUsesIt) {
		// TODO Auto-generated method stub
		super.stopUse(stctdUsesIt);
	}

	@Override
	public void fire() {
		// TODO Auto-generated method stub
		shootProjectile(getStctdThatUsesMe());
		
	}
	
	

	@Override
	public void drawItemInfo(Graphics2D g2d, Point beginPoint) {
		// TODO Auto-generated method stub
		super.drawItemInfo(g2d, beginPoint);
	}
}
