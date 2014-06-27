package be.pppt.v1.Items;

import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import be.pppt.v1.Point;
import be.pppt.v1.Size;
import be.pppt.v1.Stctd;


public abstract class meleeItem extends Item {

	private double maxDamage = 2;
	private double distance = 70;
	private double angle = Math.PI / 2;
	
	private int timeSpendSwingTime = 500;
	private final int swingTime = 100;
	
	private double precision = 5;
	
	public meleeItem(Point position, String name, double maxDamage,
			double distance, double angle, int timeSpendSwingTime,
			double precision) {
		super(position, name);
		this.maxDamage = maxDamage;
		this.distance = distance;
		this.angle = angle;
		this.timeSpendSwingTime = timeSpendSwingTime;
		this.precision = precision;
	}

	public abstract void hit(Stctd hitThing, double factor) ;
	
	public void drawItemInfo(Graphics2D g2d, Point beginPoint) {
		super.drawItemInfo(g2d, beginPoint);
		
		final int imageX = (int)beginPoint.getX() + 10;
		final int imageY = (int)beginPoint.getY() + 30;
		final int imageWidth = 24;
		
		g2d.setFont(new Font("test", Font.PLAIN, 12));
		g2d.drawString("damage: " + maxDamage, imageX + imageWidth + 10, imageY + 25);
	}
	
	public void drawItemInHand(Graphics2D g2d, Point beginPoint) {
		if (timeSpendSwingTime < swingTime) {
			double swingFactor = (double)timeSpendSwingTime / swingTime;
			Size sizeUser = this.getStctdThatUsesMe().getMySize();
			
			int drawingX = (int)(beginPoint.getX() );
			int drawingY = (int)(beginPoint.getY() + sizeUser.getHeight() * swingFactor - sizeUser.getHeight() / 2);
			
			this.paintNormal(g2d, drawingX, drawingY);
		}
		else {
			this.paintNormal(g2d, (int)beginPoint.getX(), (int)beginPoint.getY() - 24);
		}
	}
	

	@Override
	public void startUse(Stctd stctdUsesIt) {
		// TODO Auto-generated method stub
		super.startUse(stctdUsesIt);
		if (timeSpendSwingTime > swingTime) {
			tryHit(1);
			timeSpendSwingTime = 0;
		}
		
	}

	@Override
	public void stillUsing(int timePast, Stctd stctdUsesIt) {
		// TODO Auto-generated method stub
		timeSpendSwingTime += timePast;
	}

	@Override
	public void stopUse(Stctd stctdUsesIt) {
		// TODO Auto-generated method stub
		super.stopUse(stctdUsesIt);
	}

	public void tryHit(double factor) {
		//kiwis are dangerous
		Stctd whoUsesIt = this.getStctdThatUsesMe();		
		
		if (whoUsesIt != null) {
			Point mid = new Point(whoUsesIt.getDirection());
			mid.setDistance(distance);
			Point RightFlank = (new Point(mid));
			RightFlank.rotate( - angle / 2);
			
			Point[] hitPoints = new Point[(int)precision + 1];
			
			for (int i = 0; i <= precision; i++) {
				Point buf = new Point(RightFlank);
				
				Double extraAngle = i/precision * angle;
				buf.rotate(extraAngle);
				buf.countUpPoint(whoUsesIt.getPosition());
				
				hitPoints[i] = buf;
			}
			
			ArrayList<Stctd> hitedStctds = whoUsesIt.getMyWindow().stctdsThatCollidesWithPoint(hitPoints);
			
			for (int i = 0; i < hitedStctds.size(); i++) {
				Stctd ahitedStctd = hitedStctds.get(i);
				
				//ahitedStctd.getMyForce().addForce(whoUsesIt.getDirection(), (int) (getMaxDamage() * 10));
				this.hit(ahitedStctd, factor);
			}
		}
	}

	public double getMaxDamage() {
		return maxDamage;
	}

	public void setMaxDamage(double maxDamage) {
		this.maxDamage = maxDamage;
	}
}
