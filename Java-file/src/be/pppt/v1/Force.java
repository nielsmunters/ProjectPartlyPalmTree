package be.pppt.v1;

public class Force {
	private Point direction = new Point(0,0);
	
	private int timeSpendStun = 400;
	private int stun = 200;
	
	private final static int distanceFactor = 50; 
	
	public void forceMove(int timePast, Stctd aStctd) {
		double speed = aStctd.getSpeed();
		double distanceMoved = speed * timePast/1000 * 2;
		double distance = direction.distance();
		
		aStctd.moveNormal(direction, timePast * 2);
		
		if (distanceMoved >= distance) {
			direction = Point.nullPoint();
		}
		else {
			direction.setDistance(distance - distanceMoved);
		}
		
		if ( direction.equals(Point.nullPoint()) ) {
			 timeSpendStun += timePast;
		}
		
	}
	
	public void addForce( Point extraDirection, int distance) {
		extraDirection.setDistance(distance /** distanceFactor*/);
		direction.countUpPoint(extraDirection);
		timeSpendStun = 0;
	}
	
	public boolean isForceMoving() {
		if ( direction.equals(Point.nullPoint()) && timeSpendStun >= stun) {
			return false;
		}
		else {
			return true;
		}
	}
}
