package be.pppt.v1;

import java.util.ArrayList;

public abstract class ActionBlock implements DoSomething {

	private boolean used = false;
	private Point beginPoint;
	private Point endPoint;
	private GameWindow theWindow;


	public ActionBlock(boolean used, Point beginPosition, Point endPosition,
			GameWindow theWindow) {
		super();
		this.used = used;
		this.beginPoint = beginPosition;
		this.endPoint = endPosition;
		this.theWindow = theWindow;
	}


	@Override
	public void doSomething(int timePast) {
		// TODO Auto-generated method stub
		if (!used) {
			ArrayList<Stctd> possibleUser = theWindow.stctdsInAnArea(beginPoint, endPoint);
			
			if(possibleUser.contains(theWindow.getTheUserOfTheScene())) {
				whatHappens(theWindow.getTheUserOfTheScene());
				used = true;
			}
		}
	}
	
	public abstract void whatHappens(Stctd stctdToDoSomthing);
}
