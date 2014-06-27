package be.pppt.v1;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.sql.rowset.spi.SyncResolver;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import be.pppt.v1.Items.Item;
import be.pppt.v1.Items.ShootItem;


public class GameWindow extends Canvas {

	private BufferStrategy strategy;
	private Size maxSizeObject = new Size(0,0);
	
	private ArrayList<DoSomething> theThingsThatDoSomething = new ArrayList<>();
	private ArrayList<Item> theItems = new ArrayList<>();
	private ArrayList<Stctd> theStctds = new ArrayList<>();
	private ArrayList<Projectile> theProjectiles = new ArrayList<>();
	private ArrayList<SomethingOnTheScene> theBackgroundThings = new ArrayList<>();
	private ArrayList<User> theUsers = new ArrayList<>();
	
	private Point StartCornerDrawing = new Point(0,0);
	
	private User theUserOfTheScene;
	
	private long lastLoopTime = System.currentTimeMillis();
	
	private boolean debug = true;
	
	//frames per second
	private int largestWidth = 300;
	private int largestHeight = 300;
	private double totaal = 0;
	private int frames = 0;
	private double fps = 0;
	
	private double minFrames = 1000;
	private double maxFrames = 0;
	
	
	public GameWindow(int width, int height, boolean debug) {
		this.debug = debug;
		
		JFrame container = new JFrame("PPPT v2.0.1.3");
		
		// get hold the content of the frame and set up the resolution of the game

		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(width,height));
		panel.setLayout(null);
		
		// setup our canvas size and put it into the content of the frame

		new CommandListener(this);
		
		setBounds(0,0,width,height);
		panel.add(this);
		
		
		this.addMouseListener(new MouseHandler());
		this.addMouseWheelListener(new WheelHandler());
		// Tell AWT not to bother repainting our canvas since we're

		// going to do that our self in accelerated mode

		setIgnoreRepaint(true);
		
		// finally make the window visible 

		
		
		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		container.setLocation(100, 100);
		
		// add a listener to respond to the user closing the window. If they

		// do we'd like to exit the game

		this.addKeyListener(new KeyHandler());

		// request the focus so key events come to us

		requestFocus();

		// create the buffering strategy which will allow AWT

		// to manage our accelerated graphics

		createBufferStrategy(3);
		strategy = getBufferStrategy();
		
	}
	
	
	public void gameLoop() {
		boolean running = true;
		while(running) {
			int timePast = (int) (System.currentTimeMillis() - lastLoopTime);
			lastLoopTime = System.currentTimeMillis();
			
			letTheThingsDoSomething(timePast);
			
			checkDeads();
			
			drawEveryThing();
			
			
			if (debug) {
				fps = (1000.0 / timePast);
				
				totaal += timePast;
				frames++;
				
				System.out.println("avg: " + (frames*1000/totaal) + "fps this frame: " + timePast + " ms! past => " + fps + " fps with a min of: " + minFrames + " fps and a max of: " + maxFrames + " fps");
				
				if (fps > maxFrames) {
					maxFrames = (int)fps;
				}
				else if (fps < minFrames) {
					minFrames = (int)fps;
				}
				
				if (totaal == 10000) {
					totaal = 0;
					frames = 0;
				}
			}
			
		}
	}
	
	
	public void letTheThingsDoSomething(int timePast)
	{
		for(int i = 0; i < theThingsThatDoSomething.size(); i++)
		{
			DoSomething thingBuf = theThingsThatDoSomething.get(i);
			if (thingBuf instanceof Stctd) {
				Stctd stctdbuf = (Stctd)thingBuf;
				
				if (isStctdNearUser(stctdbuf)) {
					thingBuf.doSomething(timePast);
				}
			}
			else {
				thingBuf.doSomething(timePast);
			}
			
		}
	}

	public void checkDeads() {
		for (int i = 0; i < theStctds.size(); i++) {
			theStctds.get(i).checkLive();
		}
	}
	
	
	private boolean isStctdNearUser(Stctd nearMe) {
		
		Point posNearMe = nearMe.getPosition();
		
		for(User aUser : theUsers) {
			Point beginPoint = new Point(aUser.getPosition());
			beginPoint.countUpPoint(new Point(- this.getWidth(),- this.getHeight()));
			Point endPoint = new Point(aUser.getPosition());
			endPoint.countUpPoint(new Point( this.getWidth(), this.getHeight()));
			
			if(beginPoint.getX() < posNearMe.getX() && posNearMe.getX() < endPoint.getX()) {
				if (beginPoint.getY() < posNearMe.getY() && posNearMe.getY() < endPoint.getY()) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	
	private void drawSomthingOnTheScene(SomethingOnTheScene theThing, Graphics2D g2d)
	{   	
		Size theThingsSize = theThing.getMySize();
		Point theThingsPos = theThing.getPosition();
		
    	int x = (int)theThingsPos.getX() - theThingsSize.getWidth()/2;
    	int y = (int)theThingsPos.getY() - theThingsSize.getHeight()/2;
    	
		g2d.drawImage(theThing.getMyImage(), x, y, null);
		
		Point direction = theThing.getDirection();
		direction.setDistance(150);
		g2d.setColor(Color.orange);
		g2d.drawLine((int)theThingsPos.getX(), (int)theThingsPos.getY(), (int)(theThingsPos.getX() + direction.getX()), (int)(theThingsPos.getY() + direction.getY()));
	}
	
	private void drawItems(Graphics2D g2d, ArrayList<Item> itemsToDraw)
	{
        for(int i = 0; i < itemsToDraw.size(); i++)
        {

        	SomethingOnTheScene bufferItem = (SomethingOnTheScene)itemsToDraw.get(i);
        	
        	//drawSomthingOnTheScene(bufferItem, g2d);
        	bufferItem.paint(g2d);
        }
	}
	
	private void drawStctd(Graphics2D g2d, ArrayList<Stctd> stctdsToDraw) {
        for(int i = 0; i < stctdsToDraw.size(); i++)
        {

        	SomethingOnTheScene bufferStctd = (SomethingOnTheScene)stctdsToDraw.get(i);
        	
        	//drawSomthingOnTheScene(bufferStctd, g2d);
        	bufferStctd.paint(g2d);
        }
	}
	
	private void drawProjectile(Graphics2D g2d) {
        for(int i = 0; i < theProjectiles.size(); i++)
        {

        	SomethingOnTheScene bufferProjectile = (SomethingOnTheScene)theProjectiles.get(i);
        	
        	//drawSomthingOnTheScene(bufferStctd, g2d);
        	bufferProjectile.paint(g2d);
        }
	}
	
	private void drawLevel1(Graphics2D g2d, ArrayList<Stctd> stctdsToDraw) {
		for(int i = 0; i < stctdsToDraw.size(); i++)
        {

        	Stctd bufferStctd = stctdsToDraw.get(i);
        	
        	//drawSomthingOnTheScene(bufferStctd, g2d);
        	bufferStctd.paintLevel1(g2d);
        }
	}
	
	private void drawBackGround(Graphics2D g2d, ArrayList<SomethingOnTheScene> backtilesToDraw) {
		for(SomethingOnTheScene thing : backtilesToDraw) {
			thing.paint(g2d);
		}
	}
	
	private void drawSpeechBubble(Graphics2D g2d) {
		for(DoSomething thing : theThingsThatDoSomething) {
			if (thing instanceof Stctd) {
				Stctd stctdThing = ((Stctd)thing);
				Point begin = this.getDrawingCoordinates(stctdThing);
				
				stctdThing.getMyBubble().paint(g2d, (int)(begin.getX() - 50), (int)(begin.getY() - stctdThing.getMySize().getHeight() - 20), Color.WHITE, new Font("bubble", Font.BOLD, 16));
			
			}
		}
	}
	
	
	public void drawEveryThing() {
		Graphics2D g2d = (Graphics2D) strategy.getDrawGraphics();
		
		updateCornerPoint();
		
		g2d.setColor(new Color(80,45,45));
    	g2d.fillRect(0,0,this.getWidth(),this.getHeight());
		
    	Point beginPoint = new Point(StartCornerDrawing);
    	beginPoint.countUpPoint(new Point(- largestWidth,- largestHeight));
    	Point endPoint = new Point(StartCornerDrawing);
    	endPoint.countUpPoint(new Point(this.getSize().getWidth() + largestWidth,this.getSize().getHeight() + largestHeight));
    	
    	ArrayList<Stctd> stctdsToDraw = this.stctdsInAnArea(beginPoint, endPoint);
    	
    	drawBackGround(g2d, backGroundsInAnArea(beginPoint, endPoint));
    	drawLevel1(g2d, stctdsToDraw);    	
		drawItems(g2d, this.itemsInAnArea(beginPoint, endPoint));
		drawProjectile(g2d);
		drawStctd(g2d, stctdsToDraw);
		drawSpeechBubble(g2d);
		
		g2d.drawString(theUserOfTheScene.getLives() + "", this.getWidth() - 70, this.getHeight() - 50 );
		
		if (theUserOfTheScene != null)
		{
			getTheUserOfTheScene().drawInfo(g2d);
		}
		
		if (debug) {
			g2d.drawString((frames*1000/totaal) + "", this.getWidth() - 30, 10 );
		}
		//ImageIcon ii = new ImageIcon(this.getClass().getResource("test.gif"));
        //g2d.drawImage(ii.getImage(),0,0,ii.getIconWidth(),ii.getIconHeight(),null);
		
		g2d.dispose();
		strategy.show();
	}
	
	
	public void updateCornerPoint() {
		PointerInfo mouseInfo = MouseInfo.getPointerInfo();
		double mouseX = mouseInfo.getLocation().getX();
		double mouseY = mouseInfo.getLocation().getY();
		
		double frameX = this.getLocationOnScreen().getX();
		double frameY = this.getLocationOnScreen().getY();
		
		double mouseRelativeCenterX = mouseX - frameX - this.getWidth() / 2;
		double mouseRelativeCenterY = mouseY - frameY - this.getHeight() / 2;
		
		Point userCoor = new Point(0,0);
		
		if (theUserOfTheScene != null) {
			userCoor = getTheUserOfTheScene().getPosition();
		}
		
		double centerX = userCoor.getX() + (1.0/2) * mouseRelativeCenterX;
		double centerY = userCoor.getY() + (1.0/2) * mouseRelativeCenterY;
		
		double cornerX = centerX - this.getWidth() / 2;
		double cornerY = centerY - this.getHeight() / 2;
		
		StartCornerDrawing.set(cornerX,cornerY);
	}
	
	public Point getDrawingCoordinatesRotated(SomethingOnTheScene fromThis)
	{
		Point posThing = fromThis.getPosition();
		double x = posThing.getX() - StartCornerDrawing.getX();
		double y = posThing.getY() - StartCornerDrawing.getY();
		
		Point drawingPos = new Point(x,y);
		drawingPos.rotate(- fromThis.getDirection().getRotation());
		
		//return new Point(x,y);
		return drawingPos;
	}
	
	public Point getDrawingCoordinatesRotated(Point fromThis,Point direction)
	{
		Point posThing = fromThis;
		double x = posThing.getX() - StartCornerDrawing.getX();
		double y = posThing.getY() - StartCornerDrawing.getY();
		
		Point drawingPos = new Point(x,y);
		drawingPos.rotate(- direction.getRotation());
		
		//return new Point(x,y);
		return drawingPos;
	}
	
	public Point getDrawingCoordinates(SomethingOnTheScene fromThis) {
		Point posThing = fromThis.getPosition();
		double x = posThing.getX() - StartCornerDrawing.getX();
		double y = posThing.getY() - StartCornerDrawing.getY();
		
		Point drawingPos = new Point(x,y);
		
		//return new Point(x,y);
		return drawingPos;
	}
	
	
	
	public void addBackGround(SomethingOnTheScene toAdd) {
		if (theBackgroundThings.indexOf(toAdd) == -1) {	
			theBackgroundThings.add(toAdd);
			
			toAdd.setMyWindow(this);
		}

	}
	
	public void addItem(Item itemToAdd)
	{
		if (theItems.indexOf(itemToAdd) == -1)
		{
			theItems.add(itemToAdd);
			itemToAdd.setMyWindow(this);
		}

	}
	
	public void addStctd(Stctd stctdToAdd)
	{
		if (theStctds.indexOf(stctdToAdd) == -1)
		{
			theStctds.add(stctdToAdd);
			maxSizeObject.maximizeSize(stctdToAdd.getMySize());
			stctdToAdd.setMyWindow(this);
		}
	}
	
	public void addProjectile(Projectile projectileToAdd) {
		if (theProjectiles.indexOf(projectileToAdd) == -1)
		{
			theProjectiles.add(projectileToAdd);
			projectileToAdd.setMyWindow(this);
		}
	}
	
	public void addThingThatDoesSomething(DoSomething thingThatDoesSomething)
	{
		if (theThingsThatDoSomething.indexOf(thingThatDoesSomething) == -1)
		{			
			
			if (thingThatDoesSomething instanceof Projectile) {
				this.addProjectile((Projectile)thingThatDoesSomething);
			}
			else if (thingThatDoesSomething instanceof Stctd) {
				this.addStctd((Stctd)thingThatDoesSomething);
			}
			
			theThingsThatDoSomething.add(thingThatDoesSomething);
			
		}
	}

	public void addUser(User userToAdd) {
		if (!theUsers.contains(userToAdd)) {
			theUsers.add(userToAdd);
			addThingThatDoesSomething(userToAdd);
		}
		
	}
	
	
	
	public void removeBackground(SomethingOnTheScene toRemove) {
		if(theBackgroundThings.contains(toRemove)) {
			theBackgroundThings.remove(toRemove);
		}
	}
	
	public void removeProjectile(Projectile projectileToRemove) {
		
		if (theThingsThatDoSomething.contains(projectileToRemove)) {
			theThingsThatDoSomething.remove(projectileToRemove);
			projectileToRemove.setMyWindow(null);
		}
		if (theProjectiles.contains(projectileToRemove)) {
			theProjectiles.remove(projectileToRemove);
			projectileToRemove.setMyWindow(null);
		}
	}
	
	public void removeItem(Item itemToDelete)
	{
		if (theItems.contains(itemToDelete))
		{
			theItems.remove(itemToDelete);
			itemToDelete.setMyWindow(null);
		}
	}
	
	public void removeStctd(Stctd stctdToDelete)
	{
		if(theStctds.contains(stctdToDelete))
		{
			theStctds.remove(stctdToDelete);
			stctdToDelete.setMyWindow(null);
		}
		if(theThingsThatDoSomething.contains(stctdToDelete))
		{
			theThingsThatDoSomething.remove(stctdToDelete);
			stctdToDelete.setMyWindow(null);
		}
	}
	
	
	
	public Stctd oneStctdAroundObject(SomethingOnTheScene aroundThisObject) {
		return oneStctdAroundObject(aroundThisObject, 1);
	}
	
	public Stctd oneStctdAroundObject(SomethingOnTheScene aroundThisObject, double distanceFactor) {
		Point posStctd = aroundThisObject.getPosition();
		Size sizeStctd = aroundThisObject.getMySize();
		
		double width = sizeStctd.radius() + maxSizeObject.getWidth() + 10;
		double height = sizeStctd.radius() + maxSizeObject.getHeight() + 10;
		
		Point beginPoint = new Point(posStctd.getX() - width, posStctd.getY() - height);
		Point endPoint = new Point(posStctd.getX() + width, posStctd.getY() + height);
		ArrayList<Stctd> maybeTouch = this.stctdsInAnArea(beginPoint, endPoint);
		
		ArrayList<User> listToIgnore = new ArrayList<>();
		
		if (aroundThisObject instanceof Vehicle) {
			listToIgnore = ((Vehicle)aroundThisObject).getPassengers();
		}
		
		for (Stctd toTest : maybeTouch) {
			if (doDoesTwoCollide(aroundThisObject, toTest, distanceFactor) && !listToIgnore.contains(toTest)) {
				return toTest;
			}
		}
		/*Point posOtherStctd;
		Size sizeOtherStctd;
		double difrenceX = 0;
		double difrenceY = 0;
		
		int minDifrenceX = 0;
		int minDifrenceY = 0;
		
		for (Stctd toTest : theStctds)
		{
			posOtherStctd = toTest.getPosition();
			sizeOtherStctd = toTest.getMySize();
			
			difrenceX = Math.abs(posStctd.getX() - posOtherStctd.getX());
			difrenceY = Math.abs(posStctd.getY() - posOtherStctd.getY());
			
			minDifrenceX = sizeStctd.getWidth() / 2 + sizeOtherStctd.getWidth() / 2;
			minDifrenceY = sizeStctd.getHeight() / 2 + sizeOtherStctd.getHeight() / 2;
			
			if (difrenceX < minDifrenceX * distanceFactor && difrenceY < minDifrenceY * distanceFactor && !listToIgnore.contains(toTest) && aroundThisObject != toTest)
			{
				//System.out.println(difrenceX + " < " + minDifrenceX + " && " + difrenceY + " < " + minDifrenceY);
				return toTest;
			}
		}*/
		
		return null;
	}
	
	public ArrayList<Stctd> stctdsAroundObject(SomethingOnTheScene aroundThisObject) {
		
		return stctdsAroundObject(aroundThisObject, 1);
	}

	public ArrayList<Stctd> stctdsAroundObject(SomethingOnTheScene aroundThisObject, double distanceFactor) {
		ArrayList<Stctd> toReturn = new ArrayList<>();
		
		Point posStctd = aroundThisObject.getPosition();
		Size sizeStctd = aroundThisObject.getMySize();
		
		double width = sizeStctd.radius() + maxSizeObject.getWidth() + 10;
		double height = sizeStctd.radius() + maxSizeObject.getHeight() + 10;
		
		Point beginPoint = new Point(posStctd.getX() - width, posStctd.getY() - height);
		Point endPoint = new Point(posStctd.getX() + width, posStctd.getY() + height);
		ArrayList<Stctd> maybeTouch = this.stctdsInAnArea(beginPoint, endPoint);
		
		
		ArrayList<User> listToIgnore = new ArrayList<>();
		
		if (aroundThisObject instanceof Vehicle) {
			listToIgnore = ((Vehicle)aroundThisObject).getPassengers();
		}
		
		for (Stctd toTest : maybeTouch) {
			if (doDoesTwoCollide(aroundThisObject, toTest, distanceFactor)) {
				toReturn.add(toTest);
			}
		}
		
		
		/*Point posOtherStctd;
		Size sizeOtherStctd;
		double difrenceX = 0;
		double difrenceY = 0;
		
		int minDifrenceX = 0;
		int minDifrenceY = 0;
		
		for (Stctd toTest : theStctds)
		{
			posOtherStctd = toTest.getPosition();
			sizeOtherStctd = toTest.getMySize();
			
			difrenceX = Math.abs(posStctd.getX() - posOtherStctd.getX());
			difrenceY = Math.abs(posStctd.getY() - posOtherStctd.getY());
			
			minDifrenceX = sizeStctd.getWidth() / 2 + sizeOtherStctd.getWidth() / 2;
			minDifrenceY = sizeStctd.getHeight() / 2 + sizeOtherStctd.getHeight() / 2;
			
			if (difrenceX < minDifrenceX * distanceFactor && difrenceY < minDifrenceY * distanceFactor && aroundThisObject != toTest)
			{
				//System.out.println(difrenceX + " < " + minDifrenceX + " && " + difrenceY + " < " + minDifrenceY);
				toReturn.add(toTest);
			}
		}*/
		
		toReturn.removeAll(listToIgnore);
		
		return toReturn;
	}
	
	public ArrayList<Stctd> stctdsThatCollidesWithPoint(Point[] checkPoint) {
		
		ArrayList<Stctd> toReturn = new ArrayList<>();
		
		for (Stctd toTest : theStctds) {
			if (toTest instanceof CollisionByDirection && isOnePointInRotatedRect(checkPoint, toTest)) {
				toReturn.add(toTest);
			}
			else if (isOnePointInRect(checkPoint, toTest.getPosition(), toTest.getMySize())) {
				toReturn.add(toTest);
			}
		}
		
		return toReturn;
		
	}
	
	private boolean doDoesTwoCollide(SomethingOnTheScene thing1, SomethingOnTheScene thing2, double distanceFactor) {
		if (thing1 != thing2) {
			if (thing1 instanceof CollisionByDirection) {
				return collisionByCollisionAndOtherCollide(thing1, thing2, distanceFactor);
			}
			else if(thing2 instanceof CollisionByDirection) {
				return collisionByCollisionAndOtherCollide(thing2, thing1, distanceFactor);
			}
			else {
				//System.out.println(thing1.toString() + " " + thing2.toString());
				return doTwoNormalThingsCollide(thing1, thing2, distanceFactor);
			}
		}
		else
		{
			return false;
		}
	}
	
	private boolean doTwoNormalThingsCollide(SomethingOnTheScene thing1,SomethingOnTheScene thing2, double distanceFactor) {
		Point posThing1 = thing1.getPosition();
		Point posThing2 = thing2.getPosition();
		Size sizeThing1 = thing1.getMySize();
		Size sizeThing2 = thing2.getMySize();
		
		double xPosThing1 = posThing1.getX();
		double yPosThing1 = posThing1.getY();
		double xPosThing2 = posThing2.getX();
		double yPosThing2 = posThing2.getY();
		
		double halfWidthThing1 = sizeThing1.getWidth() / 2;
		double halfHeightThing1 = sizeThing1.getHeight() / 2;
		double halfWidthThing2 = sizeThing2.getWidth() / 2;
		double halfHeightThing2 = sizeThing2.getHeight() / 2;
		
		double minWidth = halfWidthThing1 + halfWidthThing2;
		double minHeight = halfHeightThing1 + halfHeightThing2;
		minWidth *= distanceFactor;
		minHeight *= distanceFactor;
		
		double differenceX = Math.abs(xPosThing1 - xPosThing2);
		double differenceY = Math.abs(yPosThing1 - yPosThing2);
		
		if (minWidth > differenceX && minHeight > differenceY) {
			return true;
		}
		return false;
	}
	
	private boolean collisionByCollisionAndOtherCollide(SomethingOnTheScene theVehicle,SomethingOnTheScene aThing, double distanceFactor) {
		Point[] cornersVehicle = getCornersOf(theVehicle, distanceFactor);
		Point[] cornersThing = getCornersOf(aThing, distanceFactor);
		
		if (isOnePointInRotatedRect(cornersThing, theVehicle) || isOnePointInRotatedRect(cornersVehicle, aThing)) {
			return true;
		}
		return false;
	}
	
	private Point[] getCornersOf(SomethingOnTheScene thisThing, double distanceFactor) {
		Size sizeThisThing = thisThing.getMySize();
		double halfWidthThing = sizeThisThing.getWidth() / 2;
		double halfHeightThing = sizeThisThing.getHeight() / 2;
		
		halfWidthThing *= distanceFactor;
		halfHeightThing *= distanceFactor;
		
		Point positionThing = thisThing.getPosition();
		double xPos = positionThing.getX();
		double yPos = positionThing.getY();
		
		Point[] rotatedPoints = new Point[4];
		rotatedPoints[0] = new Point(xPos - halfWidthThing, yPos - halfHeightThing);
		rotatedPoints[1] = new Point(xPos - halfWidthThing, yPos + halfHeightThing);
		rotatedPoints[2] = new Point(xPos + halfWidthThing, yPos - halfHeightThing);
		rotatedPoints[3] = new Point(xPos + halfWidthThing, yPos + halfHeightThing);
		
		if (thisThing instanceof CollisionByDirection) {
			double rotation = thisThing.getDirection().getRotation();
		
			for (int i = 0; i < rotatedPoints.length; i++) {
				rotatedPoints[i].rotate(positionThing,rotation);
			}
		}
		
		return rotatedPoints;		
	}
	
	private boolean isOnePointInRotatedRect(Point[] checkPoint,SomethingOnTheScene thing2) {
		if (thing2 instanceof Vehicle) {
			
			Point rotatedMidPoint = new Point(thing2.getPosition());
			double rotation = thing2.getDirection().getRotation();
			rotatedMidPoint.rotate(-rotation);
			
			Point[] rotatedCheckPoint = new Point[checkPoint.length];
			
			for (int i = 0; i < checkPoint.length;i++) {
				rotatedCheckPoint[i] = new Point(checkPoint[i]);
				rotatedCheckPoint[i].rotate(-rotation);
			}
			
			return isOnePointInRect(rotatedCheckPoint,rotatedMidPoint,thing2.getMySize());
		}
		else
		{
			return isOnePointInRect(checkPoint, thing2.getPosition(), thing2.getMySize());
		}
	}
	
	private boolean isOnePointInRect(Point[] checkPoint, Point midPoint, Size normalSize) {
		double xMidPoint = midPoint.getX();
		double yMidPoint = midPoint.getY();
		double halfWidthSize = normalSize.getWidth() / 2;
		double halfHeightSize = normalSize.getHeight() / 2;
		
		double xOnderGrens = xMidPoint - halfWidthSize;
		double xBovenGrens = xMidPoint + halfWidthSize;
		double yOnderGrens = yMidPoint - halfHeightSize;
		double yBovenGrens = yMidPoint + halfHeightSize;
		
		for (int i = 0; i < checkPoint.length;i++) {
			double xPoint = checkPoint[i].getX();
			double yPoint = checkPoint[i].getY();
		
			if (xOnderGrens <= xPoint && xPoint <= xBovenGrens && yOnderGrens <= yPoint && yPoint <= yBovenGrens) {
				return true;
			}
		}
		
		return false;
	}
	//returned het grootste absulute getal
	
	
	
	private double whatIsTheheighestABS(double num1, double num2) {
		num1 = Math.abs(num1);
		num2 = Math.abs(num2);
		
		if (num1 > num2) {
			return num1;
		}
		else {
			return num2;
		}
	}
	
	
	
	public ArrayList<Stctd> stctdInRadius(Stctd stctdMidPoint, double radius) {
		Point posOther;
		Point midPoint = stctdMidPoint.getPosition();
		
		ArrayList<Stctd> toReturn = new ArrayList<>();
		
		for (Stctd toTest : theStctds) {
			posOther = toTest.getPosition();
			
			if (posOther.distance(midPoint) <= radius && stctdMidPoint != toTest) {
				toReturn.add(toTest);
			}
		}
		
		return toReturn;
	}
	
	
	
	public ArrayList<SomethingOnTheScene> backGroundsInAnArea(Point beginPoint,Point endPoint)
	{
		ArrayList<SomethingOnTheScene> allBackgroundsInArea = new ArrayList<>();
		
		for (SomethingOnTheScene toTest : theBackgroundThings)
		{
			if(beginPoint.getX() < toTest.getPosition().getX() && endPoint.getX() > toTest.getPosition().getX())
			{
				if(beginPoint.getY() < toTest.getPosition().getY() && endPoint.getY() > toTest.getPosition().getY())
				{
					allBackgroundsInArea.add(toTest);
				}
			}
		}
		
		
		return allBackgroundsInArea;
	}
	
	public ArrayList<Stctd> stctdsInAnArea(Point beginPoint,Point endPoint)
	{
		ArrayList<Stctd> allStctdsInArea = new ArrayList<Stctd>();
		
		for (Stctd toTest : theStctds)
		{
			if(beginPoint.getX() < toTest.getPosition().getX() && endPoint.getX() > toTest.getPosition().getX())
			{
				if(beginPoint.getY() < toTest.getPosition().getY() && endPoint.getY() > toTest.getPosition().getY())
				{
					allStctdsInArea.add(toTest);
				}
			}
		}
		
		
		return allStctdsInArea;
	}

	public Item itemInAnArea(Point beginPoint,Point endPoint) {
		for (Item toTest : theItems)
		{
			if(beginPoint.getX() < toTest.getPosition().getX() && endPoint.getX() > toTest.getPosition().getX())
			{
				if(beginPoint.getY() < toTest.getPosition().getY() && endPoint.getY() > toTest.getPosition().getY())
				{
					return toTest;
				}
			}
		}
		return null;
	}
	
	public ArrayList<Item> itemsInAnArea(Point beginPoint,Point endPoint)
	{
		ArrayList<Item> allItemsInArea = new ArrayList<>();
		
		for (Item toTest : theItems)
		{
			if(beginPoint.getX() < toTest.getPosition().getX() && endPoint.getX() > toTest.getPosition().getX())
			{
				if(beginPoint.getY() < toTest.getPosition().getY() && endPoint.getY() > toTest.getPosition().getY())
				{
					allItemsInArea.add(toTest);
				}
			}
		}
		
		
		return allItemsInArea;
	}
	
	
	
	public class MouseHandler implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			getTheUserOfTheScene().startUseItem();
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			getTheUserOfTheScene().stopUseItem();			
		}
		
	}
	
	public void giveUserMouseAction(String action, int userID) {
		if (action.equals("LEFTPRESSED")) {
			getTheUserOfTheScene().startUseItem();
		}
		else if (action.equals("LEFTRELEASED")) {
			getTheUserOfTheScene().stopUseItem();
		}
	}
	
	public class WheelHandler implements MouseWheelListener {
		
		public void mouseWheelMoved(MouseWheelEvent e) {
	       
			int notches = e.getWheelRotation();
			if (notches < 0) {
	    	   	//System.out.println("Mouse wheel moved UP ");
	    	   	giveUserMouseWheelAction("DOWN", 0);
	       	} else {
	       		//System.out.println("Mouse wheel moved DOWN ");
	       		giveUserMouseWheelAction("UP", 0);
	       	}
		}
	}
	
	public void giveUserMouseWheelAction(String action, int userID) {
		if (action.equals("UP")) {	
			theUserOfTheScene.getMyInventory().upInventory();
		}
		else if (action.equals("DOWN")) {
			theUserOfTheScene.getMyInventory().downInventory();
		}
	}
	
	
	public class KeyHandler implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			String testKey = Character.toUpperCase(arg0.getKeyChar()) + "";
			
			if (arg0.getKeyCode() == KeyEvent.VK_SHIFT) {
				testKey = "SHIFT";
			}
			
			//System.out.println(testKey);
			
			giveUserKeyPressAction(testKey, 0);
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			String testKey = Character.toUpperCase(arg0.getKeyChar()) + "";
			
			if (arg0.getKeyCode() == KeyEvent.VK_SHIFT) {
				testKey = "SHIFT";
			}
			
			//System.out.println(testKey);
			
			giveUserKeyReleasedAction(testKey, 0);
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public synchronized void giveUserKeyPressAction(String testKey, int userID)
	{
		User userToDoAnAction = theUserOfTheScene;
		
		if (testKey.equals("Q") || testKey.equals("A")) {
			userToDoAnAction.goLeft();
		}
		else if (testKey.equals("W") || testKey.equals("Z")) {
			userToDoAnAction.goUp();
		}
		else if (testKey.equals("D")) {
			userToDoAnAction.goRight();
		}
		else if (testKey.equals("S")) {
			userToDoAnAction.goDown();
		}
		
		
		else if (testKey.equals("E")) {
			userToDoAnAction.inOrOutCar();
		}
		else if (testKey.equals("F")) {
			userToDoAnAction.tryPickUpItem();
		}
		else if (testKey.equals("G")) {
			this.getTheUserOfTheScene().dropItem();
		}
		
		
		else if (testKey.equals("R")) {
			Item carried = userToDoAnAction.getMyInventory().getItem();
			
			if (carried instanceof ShootItem) {
				((ShootItem) carried).startReload();
			}
		}
		
		
		else if (testKey.equals("O")){
			userToDoAnAction.getMyInventory().upInventory();
		}
		else if (testKey.equals("L")){
			userToDoAnAction.getMyInventory().downInventory();	
		}
		else if (testKey.equals("M")) {
			Item carried = userToDoAnAction.getMyInventory().getItem();
			
			if (carried instanceof ShootItem) {
				((ShootItem) carried).nextMode();
			}
		}
		
		
		else if (testKey.equals("SHIFT")) {
			userToDoAnAction.setSprinting(true);
		}
		
		userToDoAnAction.doesMoveDirectionChange();
	}
	
	public synchronized void giveUserKeyReleasedAction(String testKey, int userID)
	{
		User userToDoAnAction = theUserOfTheScene;
		
		if (testKey.equals("Q") || testKey.equals("A"))
		{
			userToDoAnAction.stopLeft();
		}
		else if (testKey.equals("W") || testKey.equals("Z"))
		{
			userToDoAnAction.stopUp();
		}
		else if (testKey.equals("D"))
		{
			userToDoAnAction.stopRight();
		}
		else if (testKey.equals("S"))
		{
			userToDoAnAction.stopDown();
		}
		else if (testKey.equals("SHIFT")) {
			userToDoAnAction.setSprinting(false);
		}
	}
	
	
	public synchronized void giveUserNewPos(Point newPos, int user) {
		theUsers.get(user).setPosition(newPos);
	}
	
	
	
	
    public User getTheUserOfTheScene() {
		
		return theUserOfTheScene;    	
	}

	public void setTheUserOfTheScene(User theUserOfTheScene) {
		this.theUserOfTheScene = theUserOfTheScene;
		
		this.addUser(theUserOfTheScene);
	}
	
	
	
}
