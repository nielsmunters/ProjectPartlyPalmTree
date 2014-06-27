package be.pppt.v1.Items;

import java.awt.Graphics2D;

import be.pppt.v1.Point;
import be.pppt.v1.Stctd;


public class Sword extends meleeItem{

	public Sword(Point position, String name, double maxDamage,
			double distance, double angle, int timeSpendSwingTime,
			double precision) {
		super(position, name, maxDamage, distance, angle, timeSpendSwingTime, precision);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void hit(Stctd hitThing, double factor) {
		// TODO Auto-generated method stub
		hitThing.takeDamage(getMaxDamage() * factor);
	}


	
}
