package be.pppt.v1.Items;

import be.pppt.v1.Point;
import be.pppt.v1.Stctd;

public class Hand extends meleeItem{

	private static String imageName = "hand";
	
	public Hand(Point position, double maxDamage,
			double distance, double angle, int timeSpendSwingTime,
			double precision) {
		super(position, imageName, maxDamage, distance, angle, timeSpendSwingTime, precision);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void hit(Stctd hitThing, double factor) {
		// TODO Auto-generated method stub
		hitThing.takeDamage(getMaxDamage() * factor);
	}


}
