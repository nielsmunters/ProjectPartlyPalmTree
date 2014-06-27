package be.pppt.v1;

public class MeleeCollisionRect extends SomethingOnTheScene implements CollisionByDirection {

	public MeleeCollisionRect(Point position, Point direction, int distance) {
		super(position, direction, "none");
		// TODO Auto-generated constructor stub
		this.setMySize(new Size(distance, distance * 2));
	}

}
