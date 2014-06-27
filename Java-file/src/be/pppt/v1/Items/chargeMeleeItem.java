package be.pppt.v1.Items;

import java.awt.Graphics2D;

import be.pppt.v1.Point;
import be.pppt.v1.Stctd;


public class chargeMeleeItem extends meleeItem {

	private int timeToMaxCharge = 1000;
	private int timeCharging = 0;
	private boolean isCharging = false;

	public chargeMeleeItem(Point position, String name, double maxDamage,
			double distance, double angle, int timeSpendSwingTime,
			double precision, int timeToMaxCharge, int timeCharging,
			boolean isCharging) {
		super(position, name, maxDamage, distance, angle, timeSpendSwingTime,
				precision);
		this.timeToMaxCharge = timeToMaxCharge;
		this.timeCharging = timeCharging;
		this.isCharging = isCharging;
	}

	@Override
	public void hit(Stctd hitThing, double factor) {
		// TODO Auto-generated method stub
		hitThing.takeDamage(getMaxDamage() * factor);
	}

	@Override
	public void drawItemInfo(Graphics2D g2d, Point beginPoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startUse(Stctd stctdUsesIt) {
		// TODO Auto-generated method stub
		super.startUse(stctdUsesIt);
		
		isCharging = true;
	}

	@Override
	public void stillUsing(int timePast, Stctd stctdUsesIt) {
		// TODO Auto-generated method stub
		if (isCharging) {
			timeCharging += timePast;
			
			if (timeCharging > timeToMaxCharge) {
				timeCharging = timeToMaxCharge;
			}
		}
		
	}

	@Override
	public void stopUse(Stctd stctdUsesIt) {
		// TODO Auto-generated method stub
		super.stopUse(stctdUsesIt);
		double factor = Math.pow(timeCharging, 2) / Math.pow(timeToMaxCharge, 2);
		
		tryHit(factor);
		timeCharging = 0;
	}
	

}
