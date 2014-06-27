package be.pppt.v1;

public class Brick extends Stctd{


	public Brick(Point position, String name) {
		super(position, 9999, 9999, 0, 0, 0, name, false, 0);
		// TODO Auto-generated constructor stub
	}

	public Brick(Point position, Point direction, String name) {
		super(position, 9999, 9999, 0, 0, 0, name, false, 0);
		this.setDirection(direction);
	}
	
	
	public Brick(Brick copyBrick) {
		super(new Point(copyBrick.getPosition()), 9999, 9999, 0, 0, 0, copyBrick.getName(), false, 0);
		this.setDirection(copyBrick.getDirection());
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */

}
