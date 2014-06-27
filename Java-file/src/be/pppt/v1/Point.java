package be.pppt.v1;

public class Point {

	private double x,y = 0;
	/**
	 * @param args
	 */
	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Point(Point otherPoint) {
		super();
		this.x = otherPoint.x;
		this.y = otherPoint.y;
	}
	
	public static Point nullPoint() {
		return new Point(0,0);
	}
	
	public void set(Point copyPoint) {
		this.setX(copyPoint.getX());
		this.setY(copyPoint.getY());
	}
	
	public void set(double x, double y) {
		this.setX(x);
		this.setY(y);
	}
	
	public void setDistance(double distancePrefered)
	{
		if (distancePrefered != 0)
		{
			double normalRotation = this.getRotation();
			this.setX(distancePrefered);
			this.setY(0);
			
			this.setRotation(normalRotation);
		}
	}
	
	public double distance()
	{
		return distance(new Point(0,0));
	}

	public double distance(Point otherPoint)
	{
		double dx = otherPoint.getX() - this.getX();
		double dy = otherPoint.getY() - this.getY();
		
		double distanceBetween = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
				
		return distanceBetween + 0.00001;
	}
	
	public double getRotation()
	{
		return getRotation(new Point(0,0));
	}
	
	public double getRotation(Point otherPoint)
	{
		double dx = this.getX() - otherPoint.getX();
		double dy = this.getY() - otherPoint.getY();
		double distanceToOtherPoint = distance(otherPoint) + 0.000001;
		
		double sinus = dy / distanceToOtherPoint;
		
		if (dy >= 0)
		{
			if (dx >= 0)
			{
				return Math.asin(Math.abs(sinus));
			}
			else
			{
				return Math.PI - Math.asin(Math.abs(sinus));
			}
		}
		else 
		{
			if (dx >= 0)
			{
				return Math.PI * 2 - Math.asin(Math.abs(Math.abs(sinus)));
			}
			else
			{
				return Math.PI + Math.asin(Math.abs(Math.abs(sinus)));
			}
		}
	}
	
	//het punt met een hoeveelheid radialen verdraaien
	public void rotate(double extraRotation)
	{
		double totalRotation = getRotation() + extraRotation;
		
		//System.out.println(totalRotation + " = " + getRotation() +" + "+ extraRotation);

		setRotation(totalRotation);
	}
	
	//een nieuwe rotatie gevenbo
	public void setRotation(double newRotation)
	{
		double totalLength = distance();
		
		double newX = totalLength * Math.cos(newRotation);
		double newY = totalLength * Math.sin(newRotation);
		
		setX(newX);
		setY(newY);
	}
	
	
	public void rotate(Point aroundThis, double extraRotation) {
		double rotationNow = this.getRotation(aroundThis);
		
		setRotation(aroundThis, rotationNow + extraRotation);
	}
	
	//roteren rond een ander punt dan nul
	public void setRotation(Point aroundThis, double newRotation) {		
		double totalLength = this.distance(aroundThis);
		Point relative = new Point(totalLength, 0);
		
		relative.rotate(newRotation);
		
		this.set(aroundThis.getX() + relative.getX(),aroundThis.getY() + relative.getY());
	}
	
	
	public void countUpPoint(Point otherPoint)
	{
		setX(getX() + otherPoint.getX());
		setY(getY() + otherPoint.getY());
	}
	
	public void minusPoint(Point otherPoint)
	{
		setX(getX() - otherPoint.getX());
		setY(getY() - otherPoint.getY());
	}
	
	public boolean equals(Point otherPoint) {
		if (this.getX() == otherPoint.getX() && this.getY() == otherPoint.getY()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	

}
