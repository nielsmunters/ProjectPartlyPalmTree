package be.pppt.v1.Items;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.text.StyledEditorKit.BoldAction;

import be.pppt.v1.DoSomething;
import be.pppt.v1.Point;
import be.pppt.v1.Projectile;
import be.pppt.v1.Stctd;


public abstract class ShootItem extends Item {

	private Projectile modelProjectile;
	private int accuracy = 10;
	
	private int bulletsInHolder = 50;
	private final int maxBulletsInHolder;
	private int bullets = 150;
	private final int maxBullets;
	
	private int bulletsInSalvoFired = 3;
	private int bulletsInSalvo = 3;
	
	private boolean hasFired = false;
	
	private int timeSpendFireRate = 200;
	private final int fireRate;
	
	private int timeSpendReloadTime = 1000;
	private final int reloadTime;

	private boolean used = false;
	
	
	private boolean modes[] = {true,true,true,true};
	private String modeName[] = {"Automatic", "Automatic Burst", "Simple Burst", "Semi Automatic"};
	private int mode = 0;
	


	public ShootItem(Point position, String name, Projectile modelProjectile,
			int accuracy, int maxBulletsInHolder, int maxBullets,
			int bulletsInSalvo, int fireRate, int reloadTime, boolean automaticFire, boolean automaticBurstFire, boolean burstFire, boolean semiAutomaticFire) {
		super(position, name);
		this.modelProjectile = modelProjectile;
		this.accuracy = accuracy;
		
		this.maxBulletsInHolder = maxBulletsInHolder;
		this.bulletsInHolder = maxBulletsInHolder;
		this.maxBullets = maxBullets;
		this.bullets = maxBullets;		
		this.bulletsInSalvo = bulletsInSalvo;
		
		this.fireRate = fireRate;
		this.timeSpendFireRate = fireRate;
		this.reloadTime = reloadTime;
		this.timeSpendReloadTime = reloadTime;
		
		
		this.modes[0] = automaticFire;
		this.modes[1] = automaticBurstFire;
		this.modes[2] = burstFire;
		this.modes[3] = semiAutomaticFire;
		
		checkMode();
	}

	
	public void startUse(Stctd stctdUsesIt) {
		super.startUse(stctdUsesIt);
		modelProjectile.setParent(stctdUsesIt);
		used = true;
		hasFired = false;
	}
	
	public void stillUsing(int timePast, Stctd stctdUsesIt) {
		
	//is het wapen niet aan het herladen
		if (timeSpendReloadTime > reloadTime) {
			
	//zit de nieuwe kogel klaar
			if (timeSpendFireRate > fireRate) {
				//System.out.println(timeSpendReloadTime + " <|> " + timeSpendFireRate);
		
				switch (mode) {
				//automatic
					case 0:
						if (used) {
							fireProcess();
						}
						
				
						break;
				//automatic burst
					case 1:
						if (bulletsInSalvoFired < bulletsInSalvo) {
							fireProcess();
							bulletsInSalvoFired++;
							
							if (bulletsInSalvoFired >= bulletsInSalvo) {
								timeSpendFireRate = - 4 * fireRate;
							}
							
						}
						else if (used) {
							bulletsInSalvoFired = 0;
						}
						//System.out.println("trying 1");
						
						break;
				//simpel burst
					case 2:
						if (bulletsInSalvoFired < bulletsInSalvo) {
							fireProcess();
							bulletsInSalvoFired++;
							
							if (bulletsInSalvoFired >= bulletsInSalvo) {
								timeSpendFireRate = - 2 * fireRate;
							}
							
						}
						else if (used && !hasFired) {
							bulletsInSalvoFired = 0;
							
							hasFired = true;
						}
						//System.out.println("trying 2");
						
						break;
				//half automatic
					case 3:
						if (used && !hasFired) {
							fireProcess();
						}
						//System.out.println("trying 3");
						
						hasFired = true;
						
						break;
						
				}
				
				
			}
			else {
			//word het wapen gebruikt
				timeSpendFireRate += timePast;
			}
		}
		//verder de kogel klaarmaken
		else {
			timeSpendReloadTime += timePast;
			
			if (timeSpendReloadTime > reloadTime) {
				finishReload();
			}
			
		}

	}
	
	public void stopUse(Stctd stctdUsesIt) {
		super.stopUse(stctdUsesIt);
		used = false;
		
		hasFired = false;
	}
	
	
	
	private void fireProcess() {
		//System.out.println("ja?");
		if (bulletsInHolder > 0) {
			fire();
		
			timeSpendFireRate = 0;
			bulletsInHolder--;
		//kijken of de kogels op zijn
			if (bulletsInHolder <= 0) {
				startReload();
			}
		}
		
	}
	
	public void shootProjectile(Stctd stctdUsesIt) {
		Projectile toShoot = new Projectile(modelProjectile);
		
		toShoot.setPosition(stctdUsesIt.getPosition());
		toShoot.setDirection(stctdUsesIt.getDirection());
		toShoot.getDirection().rotate(Math.toRadians((Math.random() * accuracy*2 - accuracy)));
		toShoot.setParent(stctdUsesIt);
		
		stctdUsesIt.getMyWindow().addThingThatDoesSomething((DoSomething)(toShoot));
	}
	
	public abstract void fire();
	
	
	public void nextMode() {
		mode++;
		
		checkMode();
		
		//System.out.println(mode + "");
	}
	private void checkMode() {
		if (mode == modes.length) {
			mode = 0;
		}
		
		while (!modes[mode]) {
			mode++;
			//System.out.println(mode + "");
			if (mode == modes.length) {
				mode = 0;
			}
		}
	}
	
	
	public void startReload() {
		if (timeSpendReloadTime >= reloadTime) {
			if (getStctdThatUsesMe() != null) {
				getStctdThatUsesMe().getMyBubble().addSpeech("Reloading...", reloadTime);
			}
		
		
			bulletsInSalvoFired = bulletsInSalvo;
			bullets += bulletsInHolder;
			bulletsInHolder = 0;
		
			timeSpendReloadTime = 0;
		}
		
		
		
	}
	public void finishReload() {
		if (getStctdThatUsesMe() != null) {
			getStctdThatUsesMe().getMyBubble().addSpeech("Ready!", 1000);
		}
		
		
		if (bullets >= maxBulletsInHolder) {
			bulletsInHolder = maxBulletsInHolder;
			
			bullets -= maxBulletsInHolder;
			
			
		}
		else {
			bulletsInHolder = bullets;
			
			bullets = 0;
		}
	}
	public void stopReload() {
		timeSpendReloadTime = 0;
	}
	
	
	
	public void drawItemInfo(Graphics2D g2d, Point beginPoint) {
		// TODO Auto-generated method stub
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
		g2d.drawString(getBulletsInHolder() + " / " + getMaxBulletsInHolder(), imageX + imageWidth + 10, imageY + 10);
		
		g2d.setFont(new Font("test", Font.PLAIN, 12));
		g2d.drawString(getBullets() + " / " + getMaxBullets(), imageX + imageWidth + 10, imageY + 25);
		g2d.drawString(modeName[mode], imageX , imageY + 42);
	}
	
	/*
	 *  ||									||
	 *  || alle getters en setter hiero! :D ||
	 *  ||									||
	 * 	\/									\/
	 */
	
	public int getBullets() {
		return bullets;
	}

	public void setBullets(int bullets) {
		this.bullets = bullets;
	}
	
	public Projectile getModelProjectile() {
		return modelProjectile;
	}

	public void setModelProjectile(Projectile modelProjectile) {
		this.modelProjectile = modelProjectile;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public int getBulletsInHolder() {
		return bulletsInHolder;
	}

	public void setBulletsInHolder(int bulletsInHolder) {
		this.bulletsInHolder = bulletsInHolder;
	}

	public int getMaxBulletsInHolder() {
		return maxBulletsInHolder;
	}

	public int getMaxBullets() {
		return maxBullets;
	}

	public int getTimeSpendFireRate() {
		return timeSpendFireRate;
	}

	public void setTimeSpendFireRate(int timeSpendFireRate) {
		this.timeSpendFireRate = timeSpendFireRate;
	}

	public int getFireRate() {
		return fireRate;
	}

	public int getTimeSpendReloadTime() {
		return timeSpendReloadTime;
	}

	public void setTimeSpendReloadTime(int timeSpendReloadTime) {
		this.timeSpendReloadTime = timeSpendReloadTime;
	}

	public int getReloadTime() {
		return reloadTime;
	}
	
}
