package be.pppt.v1;

import be.pppt.v1.Items.SubMachineGun;



public class SimpleAi extends Stctd implements DoSomething {
	
	private int timeSteps = 0;
	private int timeRest = 0;
	private final static int timeStepsNormaly = 3000;
	private final static int timeRestNormaly = 6000;
	
	public SimpleAi(Point position, double lives, double maxLives, double speed, double sprintFactor, double workFactor, String name)
	{
		super(position, lives, maxLives, speed, sprintFactor, workFactor, name, true, 2);
		
		//Projectile buf = new Projectile(new Point(0,0), new Point(0,0), "proj", 1, 1000, 500, this, 1000);
		//getMyInventory().addItem(new SubMachineGun(new Point(0,0), buf));
		
	}

	//beweeg als een ai
	@Override
	public void doSomething(int timePast) {
		// TODO Auto-generated method stub
		//rust een aantal tellen
		if (timeRest > 0)
		{
			timeRest -= timePast;
			
			if (Math.random() * timeRestNormaly/2 < timePast) {
				changeDirection();
				//System.out.println("changed!");
			}
		}
		//loop een aantal tellen
		else if (timeSteps > 0)
		{
			if (!move(timePast))
			{
				changeDirection();
			}
			
			timeSteps -= timePast;
		}
		//begin de cirkel opnieuw
		else
		{
			newCycle();
		}
	}
	
	//de rust en de loop tijd aan passen
	public void newCycle()
	{
		timeSteps = (int)(timeStepsNormaly + timeStepsNormaly * (Math.random() - 0.5) );
		timeRest = (int)(timeRestNormaly + timeRestNormaly * (Math.random() - 0.5) );
		
		//System.out.println(timeRest + " <|> " + timeSteps);
		
		changeDirection();
	}
	
	
	public void changeDirection()
	{		
		int newX = (int)(Math.random() * 50 - 25);
		int newY = (int)(Math.random() * 50 - 25);
		
		setDirection(new Point(newX, newY));
	}
	
}
