package be.pppt.v1;

import java.util.ArrayList;

import be.pppt.v1.Items.Item;

public class ZombieAI extends SimpleAi{

	private int smellDistance = 300;
	private Stctd targetStctd = null;
	
	private int timeSpendHitDelay = 400;
	private int hitDelay = 1000;
	
	public ZombieAI(Point position, double lives, double maxLives,
			double speed, double sprintFactor, double workFactor, String name,
			int smellDistance, int hitDelay) {
		super(position, lives, maxLives, speed, sprintFactor, workFactor, name);
		
		this.smellDistance = smellDistance;
		this.hitDelay = hitDelay;
		timeSpendHitDelay = hitDelay;
	}

	public void doSomething(int timePast) {
		Item itemHolding = this.getMyInventory().getItem();
		
		itemHolding.stillUsing(timePast, this);
		timeSpendHitDelay += timePast;
		
		if (targetStctd == null) {
			targetStctd = smellPeople();
			
			super.doSomething(timePast);
		}
		else {
			this.setSprinting(true);
			
			Point newDirection = new Point(targetStctd.getPosition());
			newDirection.minusPoint(this.getPosition());
			
			this.setDirection(newDirection);
			
			double distance = targetStctd.getPosition().distance(this.getPosition());
			
			if (70 >= distance) {
				if(timeSpendHitDelay >= hitDelay) {
					itemHolding.startUse(this);
					itemHolding.stopUse(this);
					
					timeSpendHitDelay = (int) (Math.random() * hitDelay);
				}
				
			}
			else {
				this.move(timePast);
			}
			
		}
	}

	public Stctd smellPeople() {
		ArrayList<Stctd> possiblePeople = getMyWindow().stctdInRadius(this, smellDistance);
		
		int userTeamNumber = User.userTeamNumber;
		
		for (Stctd aStctd : possiblePeople) {
			if (aStctd.getTeamNumber() == userTeamNumber) {
				return aStctd;
			}
		}
		
		
		return null;
	}
}
