package be.pppt.v1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class SomethingOnTheScene {

	private static int counterID = 0;
	
	private int id;
	
	private Point position = new Point(0,0);
	private Point direction = new Point(10,0);
	private Size mySize = new Size(48,48);
	
	private String wayOfCollision = "point";
	
	private String name = "noName";
	private final static String imageSource = "images/";
	private Image myImage;
	
	private GameWindow myWindow;

	private static ArrayList<String> namesOfTheImages = new ArrayList<>();
	private static ArrayList<Image> imagesAlreadyLoaded = new ArrayList<>();

	public SomethingOnTheScene(Point position, Point direction, String name) {
		super();
		this.position = position;
		this.direction = direction;
		setNextId();
		
		setName(name);
	}
	
	public SomethingOnTheScene(SomethingOnTheScene toCopy) {
		this.position = new Point(toCopy.getPosition());
		this.myImage = toCopy.getMyImage();
		this.name = toCopy.getName();
		this.direction = toCopy.getDirection();
		this.mySize = toCopy.getMySize();
		setNextId();
	}
		
	private synchronized void setNextId() {
		this.id = counterID;
		counterID++;
	}
	
	public synchronized void setId(int id) {
		this.id = id;
		if (counterID < id) {
			counterID = id + 1;
		}
	}
	
	public int getId() {
		return id;
	}
	
	public void paint( Graphics2D g2d)
	{   	
		Size theThingsSize = this.getMySize();
		Point theThingsPos = getMyWindow().getDrawingCoordinatesRotated(this);//this.getPosition();
		
    	int x = (int)theThingsPos.getX() - theThingsSize.getWidth()/2;
    	int y = (int)theThingsPos.getY() - theThingsSize.getHeight()/2;
    	
    	
		g2d.drawImage(this.getMyImage(), x, y, null);
		
		//g2d.setColor(Color.green);
		//g2d.drawLine((int)theThingsPos.getX(), (int)theThingsPos.getY(),(int)theThingsPos.getX() + 200, (int)theThingsPos.getY());
	}
	
	public void paintNormal(Graphics2D g2d, int newx, int newy) {
		Size theThingsSize = this.getMySize();
		
		int x = newx - theThingsSize.getWidth()/2;
    	int y = newy - theThingsSize.getHeight()/2;
		
		g2d.drawImage(this.getMyImage(), x, y, null);
	}

	
	
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = new Point(position);
	}
	public Point getDirection() {
		return direction;
	}
	public void setDirection(Point direction) {
		this.direction = new Point(direction);
	}
	public Size getMySize() {
		return mySize;
	}
	public void setMySize(Size mySize) {
		this.mySize = new Size(mySize);
	}
	
	public String getWayOfCollision() {
		return wayOfCollision;
	}
	public void setWayOfCollision(String wayOfCollision) {
		this.wayOfCollision = wayOfCollision;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		boolean bestaat = false;
		
		for (int i = 0; i < namesOfTheImages.size(); i++) {
			if (name.equals(namesOfTheImages.get(i))) {
				myImage = imagesAlreadyLoaded.get(i);
				
				bestaat = true;
				break;
			}
		}
		
		if (!bestaat) {
			ImageIcon ii = new ImageIcon(this.getClass().getResource(name + ".png"));
			myImage = ii.getImage();
			
			System.out.println("Image gelezen: " + name + ".png");
			
			namesOfTheImages.add(name);
			imagesAlreadyLoaded.add(myImage);
		}
		
		mySize = new Size(myImage.getWidth(null), myImage.getHeight(null));
		
		this.name = name;
	}
	
	public Image getMyImage() {
		return myImage;
	}



	public GameWindow getMyWindow() {
		return myWindow;
	}



	public void setMyWindow(GameWindow myWindow) {
		this.myWindow = myWindow;
	}
}
