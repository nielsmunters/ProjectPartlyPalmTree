package be.pppt.v1;

import java.util.ArrayList;
import java.util.Iterator;

public class Vehicle extends Stctd implements DoSomething, CollisionByDirection {

	private double acceleration = 2;
	private double airResistance = 0.4;
	private double brakeAcceleration = 3;

	private double maxSpeed = 800;
	private double backSpeedFactor = 0.3;

	private double rotationFactor =  Math.toRadians(150); // in graden

	private final int maxPassengers;
	private ArrayList<User> passengers = new ArrayList<>();


	public Vehicle(Point position, double lives, double maxLives, double speed,
			double sprintFactor, double workFactor, String name,
			double acceleration, double airResistance,
			double brakeAcceleration, double maxSpeed, int maxPassengers) {
		super(position, lives, maxLives, speed, sprintFactor, workFactor, name, false, 0);

		this.acceleration = acceleration;
		this.airResistance = airResistance;
		this.brakeAcceleration = brakeAcceleration;

		this.maxSpeed = maxSpeed;

		this.maxPassengers = maxPassengers;

	}
	
	
	public Point getDropOutPosition(User toGetOut) {
		Point newPos = new Point(this.getPosition());
		Size vehicleSize = this.getMySize();
		Point dirVehicle = this.getDirection();
		Point searchDir = new Point(dirVehicle);
		
		Size userSize = toGetOut.getMySize();
		
		
		double minDistance = userSize.radius() + vehicleSize.height / 2;
		Point extra = new Point(minDistance,vehicleSize.width / 2);
		extra.rotate(dirVehicle.getRotation() - Math.PI / 2);
		
		//System.out.println(extra.getX() + "|" + extra.getY() + " - " + dirVehicle.getX() + "|" + dirVehicle.getY());
		//System.out.println(extra.getRotation() + " - " + dirVehicle.getRotation());
		
		newPos.countUpPoint(extra);
		searchDir.setDistance(1);
		SomethingOnTheScene testObject = new SomethingOnTheScene(newPos, new Point(1,1), "test1");
		
		//aan de linkerkant
		
		double lengthToTest = vehicleSize.width + userSize.radius();
		searchDir.rotate(- Math.PI);
		
		for (int i = 0; i <= lengthToTest; i++){
			if (this.getMyWindow().oneStctdAroundObject(testObject) == null) {
				return testObject.getPosition();
			}
			else {
				testObject.getPosition().countUpPoint(searchDir);
			}
		}
		
		//aan de achterkant
		
		lengthToTest = vehicleSize.height + userSize.radius()*2;
		searchDir.rotate(- Math.PI/2);
		
		for (int i = 0; i <= lengthToTest; i++){
			if (this.getMyWindow().oneStctdAroundObject(testObject) == null) {
				return testObject.getPosition();
			}
			else {
				testObject.getPosition().countUpPoint(searchDir);
			}
		}

		//aan de rechterkant
		
		lengthToTest = vehicleSize.width + userSize.radius()*2;
		searchDir.rotate(- Math.PI/2);
		
		for (int i = 0; i <= lengthToTest; i++){
			if (this.getMyWindow().oneStctdAroundObject(testObject) == null) {
				return testObject.getPosition();
			}
			else {
				testObject.getPosition().countUpPoint(searchDir);
			}
		}

		//aan de voorkant
		
		lengthToTest = vehicleSize.height + userSize.radius()*2;
		searchDir.rotate(- Math.PI/2);
		
		for (int i = 0; i <= lengthToTest; i++){
			if (this.getMyWindow().oneStctdAroundObject(testObject) == null) {
				return testObject.getPosition();
			}
			else {
				testObject.getPosition().countUpPoint(searchDir);
			}
		}
		
		//aan de linkerkant opnieuw
		
		lengthToTest = userSize.radius();
		searchDir.rotate(- Math.PI);
		
		for (int i = 0; i <= lengthToTest; i++){
			if (this.getMyWindow().oneStctdAroundObject(testObject) == null) {
				return testObject.getPosition();
			}
			else {
				testObject.getPosition().countUpPoint(searchDir);
			}
		}
		
			
		return null;
	}

	
	public boolean addPassenger(User newPassenger) {
		if (passengers.size() < maxPassengers) {
			passengers.add(newPassenger);
			return true;
		}
		return false;
	}

	public void removePassenger(User passengerToRemove) {
		if (passengers.contains(passengerToRemove)) {
			passengers.remove(passengerToRemove);
		} 
	}
	
	
	public void resistance(int timePast) {
		
		double airResistanceByTime = airResistance * timePast / 1000.0;
		
		if (getSpeed() > airResistanceByTime)
		{
			setSpeed(getSpeed() - airResistanceByTime);
		}
		else if (getSpeed() < - airResistanceByTime)
		{
			setSpeed(getSpeed() + airResistanceByTime);
		}
		else {
			setSpeed(0);
		}
	}
	
	public void turn(int timePast) {
		if (passengers.size() != 0) {
			User driver = passengers.get(0);
			int xDir = (int) driver.getMoveDirection().getX();
			
			double speedFactor = getSpeed() / getMaxSpeed();
			double timeFactor = timePast / 1000.0;
			
			double rotation =speedFactor * rotationFactor * timeFactor;
			
			if (xDir < 0) {
				this.getDirection().rotate( - rotation);
				
				if (getMyWindow().oneStctdAroundObject(this) != null)
				{
					this.getDirection().rotate( rotation * 2);
				}
				//System.out.println("turn left " + getDirection().getRotation());
			}
			else if (xDir > 0) {
				this.getDirection().rotate( rotation);
				
				if (getMyWindow().oneStctdAroundObject(this) != null)
				{
					this.getDirection().rotate(- rotation * 2);
				}
				//System.out.println("turn right " + getDirection().getRotation() +" "+ getDirection().getX() +" "+ getDirection().getY());
			}
		}
	}

	public void driveForward(int timePast) {
		
		double timeFactor = timePast / 1000.0;
		
		if (getSpeed() >= 0) {
			if (getSpeed() < maxSpeed) {
				setSpeed(getSpeed() + acceleration * timeFactor);
			}
		} else {
			setSpeed(getSpeed() + brakeAcceleration * timeFactor);
		}
	}

	public void driveBack(int timePast) {
		double timeFactor = timePast / 1000.0;
		
		if (getSpeed() <= 0) {
			if (getSpeed() > - backSpeedFactor * maxSpeed) {
				setSpeed(getSpeed() - acceleration * timeFactor);
			}
		} else {
			setSpeed(getSpeed() - brakeAcceleration * timeFactor);
		}
	}
	
	
	public void vehicleCollide() {
		double factor = getSpeed() / getMaxSpeed();
		takeDamage(factor * getMaxLives());
	}
	

	public void die() {		
		Iterator<User> passengerIterator = passengers.iterator();
		
		while(passengerIterator.hasNext()) {
			passengerIterator.next().die();
		}
		
		super.die();
	}
	
	
	@Override
	public void doSomething(int timePast) {
		// TODO Auto-generated method stub		
		if (passengers.size() != 0) {
			User driver = passengers.get(0);

			if (driver.getMoveDirection().getY() < 0) {
				driveForward(timePast);
			} else if (driver.getMoveDirection().getY() > 0) {
				driveBack(timePast);
			}
		}
		
		if (getSpeed() != 0) {
			move(timePast);
		}

		//draaien voor het bewegen om niet vast te komen zitten
		this.turn(timePast);
		this.resistance(timePast);

		
		givePassengersCoordinates();
	}
	
	public void givePassengersCoordinates() {
		Iterator<User> passengersToGiveCoordinates = passengers.iterator();

		int i = 0;

		while (passengersToGiveCoordinates.hasNext()) {
			passengers.get(i).setPosition(new Point(this.getPosition()));
			passengersToGiveCoordinates.next();

			i++;
		}
	}

	
	
	public ArrayList<User> getPassengers() {
		return passengers;
	}

	public void setPassengers(ArrayList<User> passengers) {
		this.passengers = passengers;
	}
	
	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	public double getAirResistance() {
		return airResistance;
	}

	public void setAirResistance(double airResistance) {
		this.airResistance = airResistance;
	}

	public double getBrakeAcceleration() {
		return brakeAcceleration;
	}

	public void setBrakeAcceleration(double brakeAcceleration) {
		this.brakeAcceleration = brakeAcceleration;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getMaxPassengers() {
		return maxPassengers;
	}

	public double getBackSpeedFactor() {
		return backSpeedFactor;
	}

	public void setBackSpeedFactor(double backSpeedFactor) {
		this.backSpeedFactor = backSpeedFactor;
	}


}
