package be.pppt.v1.Items;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import be.pppt.v1.Point;
import be.pppt.v1.SomethingOnTheScene;
import be.pppt.v1.Stctd;


public abstract class Item extends SomethingOnTheScene{
	
	private Stctd stctdThatUsesMe = null;
	private boolean used = false;


	public Item(Point position, String name) {
		super(position, new Point(10,0), name);
		
	}
	
	
	public void drawItemInHand(Graphics2D g2d, Point beginPoint) {
		if (used) {
			this.paintNormal(g2d, (int)beginPoint.getX() - 10, (int)beginPoint.getY());
		}
		else {
			this.paintNormal(g2d, (int)beginPoint.getX(), (int)beginPoint.getY());
		}
	}
	
	public void drawItemInfoSmall(Graphics2D g2d, Point beginPoint) {
		final int rectWidht = 44;
		final int rectHeight = 44;
		
		final int imageX = (int)beginPoint.getX() + 10;
		final int imageY = (int)beginPoint.getY() + 10;
		final int imageWidth = 24;
		final int imageHeight = 24;
		
		g2d.setColor(Color.orange);
		g2d.fillRect((int)beginPoint.getX(), (int)beginPoint.getY(), rectWidht, rectHeight);
		
		g2d.setColor(Color.white);
		g2d.fillRect(imageX, imageY, imageWidth, imageHeight);
		
		g2d.drawImage(getMyImage(), imageX, imageY, imageWidth, imageHeight, null);
	}	
	
	public void drawItemInfo(Graphics2D g2d, Point beginPoint)
	{
		final int rectWidht = 120;
		final int rectHeight = 80;
		
		final int imageX = (int)beginPoint.getX() + 10;
		final int imageY = (int)beginPoint.getY() + 30;
		final int imageWidth = 24;
		final int imageHeight = 24;
		
		g2d.setColor(Color.orange);
		g2d.fillRect((int)beginPoint.getX(), (int)beginPoint.getY(), rectWidht, rectHeight);
		
		g2d.setColor(Color.white);
		g2d.fillRect(imageX, imageY, imageWidth, imageHeight);
		g2d.drawImage(getMyImage(), imageX, imageY, imageWidth, imageHeight, null);
		
		g2d.setColor(Color.blue);
		g2d.setFont(new Font("test", Font.BOLD, 16));
		g2d.drawString(getName(), imageX, imageY - 12);
		g2d.drawLine(imageX, imageY - 7, imageX + rectWidht - 20, imageY - 7);		
	}
	
	public void startUse(Stctd stctdUsesIt) {
		stctdThatUsesMe = stctdUsesIt;
		used = true;
	}
	
	public abstract void stillUsing(int timePast, Stctd stctdUsesIt);
	
	public void stopUse(Stctd stctdUsesIt) {
		used = false;
	}
	
	public Stctd getStctdThatUsesMe() {
		return stctdThatUsesMe;
	}

	public void setStctdThatUsesMe(Stctd stctdThatUsesMe) {
		this.stctdThatUsesMe = stctdThatUsesMe;
	}
	
	public boolean isUsed() {
		return used;
	}

}
