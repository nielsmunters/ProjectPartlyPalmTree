package be.pppt.v1;

import java.util.ArrayList;
import java.util.Random;

import javax.xml.ws.Endpoint;

public class justTheMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameWindow theWindow = new GameWindow(1066,600,false);
		int L = 72;
		theWindow.setTheUserOfTheScene(new User(new Point(4*L,19*L),3000,3000,400,1.5,0.5,"userke"));
		
		
		//ROCKS
		
		Brick rock = new Brick(Point.nullPoint(),"rockWall");
		Brick rock2 = new Brick(Point.nullPoint(),"rockWall2");
		Brick rock3 = new Brick(Point.nullPoint(),"rockWall3");
		Brick rockhub = new Brick(Point.nullPoint(),"rockWallHub");
		
		ArrayList<Brick> rocks = new ArrayList<>();
		rocks.add(rock);
		rocks.add(rock2);
		rocks.add(rock3);
		
		Brick corner = new Brick(new Point(1*L,15*L), "rockWallHubCorner");
		
		
		//WALLS
		
		Brick wall = new Brick(Point.nullPoint(),"wall2");
		Brick wallhub = new Brick(Point.nullPoint(),"wallHub2");
		
		ArrayList<Brick> walls = new ArrayList<>();
		walls.add(wall);
		
		//SMALL WALL
		
		Brick smallWall = new Brick(Point.nullPoint(),"wall");
		Brick SmallWallhub = new Brick(Point.nullPoint(),"wallHub");
		
		ArrayList<Brick> smallWalls = new ArrayList<>();
		smallWalls.add(smallWall);
		
		//THE Tiles
		SomethingOnTheScene tile1 = new SomethingOnTheScene(Point.nullPoint(), new Point(1,0), "tiles");
		
		ArrayList<SomethingOnTheScene> tiles = new ArrayList<>();
		tiles.add(tile1);
		
		//THE GROUND
				SomethingOnTheScene ground2 = new SomethingOnTheScene(Point.nullPoint(), new Point(1,0), "ground2");
				SomethingOnTheScene ground3 = new SomethingOnTheScene(Point.nullPoint(), new Point(1,0), "ground3");
				SomethingOnTheScene ground4 = new SomethingOnTheScene(Point.nullPoint(), new Point(1,0), "ground4");
				SomethingOnTheScene ground5 = new SomethingOnTheScene(Point.nullPoint(), new Point(1,0), "ground5");
				SomethingOnTheScene ground6 = new SomethingOnTheScene(Point.nullPoint(), new Point(1,0), "ground6");
				SomethingOnTheScene ground7 = new SomethingOnTheScene(Point.nullPoint(), new Point(1,0), "ground7");
				SomethingOnTheScene ground8 = new SomethingOnTheScene(Point.nullPoint(), new Point(1,0), "ground8");
				
				ArrayList<SomethingOnTheScene> grounds = new ArrayList<>();
				
				//grounds.add(ground);
				grounds.add(ground2);
				grounds.add(ground3);
				grounds.add(ground4);
				grounds.add(ground5);
				grounds.add(ground6);
				grounds.add(ground7);
				grounds.add(ground8);
				
		//de trappen
				
				SomethingOnTheScene busSots = new SomethingOnTheScene(Point.nullPoint(), new Point(1,0), "stair");
				ArrayList<SomethingOnTheScene> stairs = new ArrayList<>();
				stairs.add(busSots);
				
				SomethingOnTheScene stair180 = new SomethingOnTheScene(Point.nullPoint(), new Point(1,0), "stair180");
				ArrayList<SomethingOnTheScene> stairs180 = new ArrayList<>();
				stairs180.add(stair180);
		
		
		
		//EERSTE VIERKANT
		backgroundTiles(theWindow, grounds, new Point(2*L, 10*L), 50, 50);
		backgroundTiles(theWindow, grounds, new Point(1*L, 23*L), 1, 10);
		wallMaker(theWindow, rocks, rockhub, 0*L,33*L,0*L,23*L);
		
		wallMaker(theWindow, rocks, rockhub, 0*L,23*L,8*L,23*L);
		wallMaker(theWindow, rocks, rockhub, 8*L,23*L,8*L,16*L);
		
		Brick buf = new Brick(corner);
		buf.setPosition(new Point(8*L,23*L));
		theWindow.addStctd(buf);
		
		wallMaker(theWindow, rocks, rockhub, -1*L,23*L,-1*L,15*L);
		wallMaker(theWindow, walls, wallhub, 0,23*L,0,16*L);
		
		wallMaker(theWindow, rocks, rockhub, -1*L,15*L,1*L,15*L);
		wallMaker(theWindow, walls, wallhub, 0,16*L,2*L,16*L);
		
		backgroundTiles(theWindow, tiles, new Point(1*L,17*L), 8, 7);
		
		theWindow.addStctd(new Brick(new Point(4*L,22.5*L),"house"));
		theWindow.addStctd(new Brick(new Point(7.5*L,20*L),"houseVertical"));
		
		wallMaker(theWindow, rocks, rockhub, 1*L,15*L,1*L,8*L);
		wallMaker(theWindow, walls, wallhub, 2*L,16*L,2*L,9*L);
		
		buf = new Brick(corner);
		buf.setPosition(new Point(1*L,15*L));
		theWindow.addStctd(buf);
		
		//de trappen
		
		backgroundTiles(theWindow, tiles, new Point(3*L,9*L), 17, 6);
		backgroundTiles(theWindow, stairs, new Point(3*L,14*L), 3, 6);
		
		wallMaker(theWindow, rocks, rockhub, 6*L,14*L,19*L,14*L);
		wallMaker(theWindow, rocks, rockhub, 19*L,14*L,19*L,9*L);
		buf = new Brick(corner);
		buf.setPosition(new Point(19*L,14*L));
		theWindow.addStctd(buf);
		
		wallMaker(theWindow, walls, wallhub, 6*L,16*L,6*L,14*L);
		
		wallMaker(theWindow, walls, wallhub, 8*L,16*L,6*L,16*L);
		
		wallMaker(theWindow, rocks, rockhub, 1*L,8*L,15*L,8*L);
		wallMaker(theWindow, walls, wallhub, 2*L,9*L,15*L,9*L);
		
		theWindow.addStctd(new Brick(new Point(10*L,13.5*L),"house"));
		
		//VIERKANT 2
		wallMaker(theWindow, smallWalls, SmallWallhub, 27*L,5*L,27*L,9*L);
		
		wallMaker(theWindow, walls, wallhub, 19*L,9*L,27*L,9*L);
		wallMaker(theWindow, rocks, rockhub, 31*L,24*L,31*L,0*L);
		backgroundTiles(theWindow, grounds, new Point(31*L,0*L), 10, 10);	
		backgroundTiles(theWindow, tiles, new Point(16*L,1*L), 16, 8);		
		
		wallMaker(theWindow, rocks, rockhub, 14*L,8*L,14*L,-1*L);
		wallMaker(theWindow, walls, wallhub, 15*L,9*L,15*L,0*L);
		
		wallMaker(theWindow, rocks, rockhub, 14*L,-1*L,41*L,-1*L);
		wallMaker(theWindow, walls, wallhub, 15*L,0*L,40*L,0*L);
		
		buf = new Brick(corner);
		buf.setPosition(new Point(14*L,8*L));
		theWindow.addStctd(buf);
		//theWindow.addStctd(new Brick(new Point(27*L,*L),"houseVertical"));
		theWindow.addStctd(new Brick(new Point(27*L,4.5*L),"houseVertical"));
		theWindow.addStctd(new Brick(new Point(22*L,8*L),"house"));
		
		theWindow.addStctd(new Brick(new Point(17*L,2*L),"houseCorner"));
		
		
		//GANG 2
		wallMaker(theWindow, rocks, rockhub, 27*L,12*L,27*L,24*L);
		wallMaker(theWindow, walls, wallhub, 27*L,9*L,27*L,12*L);
		
		theWindow.addStctd(new Brick(new Point(30.5*L,19*L),"houseVertical"));
		
		backgroundTiles(theWindow, tiles, new Point(27*L,8*L), 5, 16);
		
		//VIERKANT 3
		wallMaker(theWindow, walls, wallhub, 27*L,24*L,21*L,24*L);
		wallMaker(theWindow, walls, wallhub, 37*L,24*L,31*L,24*L);
		wallMaker(theWindow, walls, wallhub, 37*L,24*L,37*L,34*L);
		wallMaker(theWindow, walls, wallhub, 21*L,24*L,21*L,34*L);
		wallMaker(theWindow, walls, wallhub, 21*L,34*L,37*L,34*L);
		
		wallMaker(theWindow, walls, wallhub, 27*L,24*L,27*L,27*L);
		wallMaker(theWindow, walls, wallhub, 31*L,24*L,31*L,27*L);
		
		wallMaker(theWindow, rocks, rockhub, 10*L,38*L,10*L,28*L);
		wallMaker(theWindow, rocks, rockhub, 10*L,28*L,20*L,28*L);
		wallMaker(theWindow, rocks, rockhub, 20*L,28*L,20*L,35*L);
		wallMaker(theWindow, rocks, rockhub, 20*L,35*L,40*L,35*L);
		wallMaker(theWindow, rocks, rockhub, 40*L,35*L,40*L,45*L);
		
		theWindow.addStctd(new Brick(new Point(20*L,35*L), new Point(0,1),"rockWallHubCorner"));
		
		backgroundTiles(theWindow, tiles, new Point(21*L,24*L), 16, 10);
		
		backgroundTiles(theWindow, stairs180, new Point(28*L,25*L), 3, 6);
		
		theWindow.addStctd(new Brick(new Point(33*L,25.25*L), new Point(-1,0),"house"));
		theWindow.addStctd(new Brick(new Point(35.75*L,29*L),"houseVertical"));
		theWindow.addStctd(new Brick(new Point(35*L,32*L), new Point(-1,0),"houseCorner"));
		theWindow.addStctd(new Brick(new Point(28*L,32.75*L),"house"));
		theWindow.addStctd(new Brick(new Point(32*L,32.75*L),"house"));
		theWindow.addStctd(new Brick(new Point(22.25*L,30*L), new Point(-1,0),"houseVertical"));
		theWindow.addStctd(new Brick(new Point(23*L,26*L),"houseCorner"));
		
		
		theWindow.addThingThatDoesSomething(new ZombieAI(new Point(9*L,12*L), 10, 10, 100, 3, 0, "zombie", 400, 1000));
		theWindow.addThingThatDoesSomething(new ZombieAI(new Point(12*L,12*L),10, 10, 100, 3, 0, "zombie", 400, 1000));
		theWindow.addThingThatDoesSomething(new ZombieAI(new Point(6*L,12*L), 10, 10, 100, 3, 0, "zombie", 400, 1000));
		
		theWindow.addThingThatDoesSomething(new ZombieAI(new Point(21*L,3*L), 10, 10, 100, 3, 0, "zombie", 400, 1000));
		theWindow.addThingThatDoesSomething(new ZombieAI(new Point(25*L,6*L), 10, 10, 100, 3, 0, "zombie", 400, 1000));
		theWindow.addThingThatDoesSomething(new ZombieAI(new Point(21*L,5*L), 10, 10, 100, 3, 0, "zombie", 400, 1000));
		theWindow.addThingThatDoesSomething(new ZombieAI(new Point(21*L,4*L), 10, 10, 100, 3, 0, "zombie", 400, 1000));
		theWindow.addThingThatDoesSomething(new ZombieAI(new Point(25*L,7*L), 10, 10, 100, 3, 0, "zombie", 400, 1000));
		
		
		theWindow.getTheUserOfTheScene().getMyBubble().addSpeech("Use a,w,s,d or q,z,d,s to move! change your item with the scrollWheel!", 12000);
		
		
		theWindow.addThingThatDoesSomething(new ActionBlock(false, new Point(2.5*L,14.5*L),new Point(5.5*L,16.5*L), theWindow) {
			
			@Override
			public void whatHappens(Stctd stctdToDoSomthing) {
				// TODO Auto-generated method stub
				stctdToDoSomthing.getMyBubble().addSpeech("Press your left-mousebutton to use your weapon!", 8000);
			}
		});
		
		theWindow.addThingThatDoesSomething(new ActionBlock(false, new Point(9*L,9.5*L),new Point(11*L,13.5*L), theWindow) {
			
			@Override
			public void whatHappens(Stctd stctdToDoSomthing) {
				// TODO Auto-generated method stub
				stctdToDoSomthing.getMyBubble().addSpeech("Pick items up by pressingthe F-key!", 8000);
			}
		});
		
		theWindow.addThingThatDoesSomething(new ActionBlock(false, new Point(15*L,9.5*L),new Point(19*L,13.5*L), theWindow) {
			
			@Override
			public void whatHappens(Stctd stctdToDoSomthing) {
				// TODO Auto-generated method stub
				stctdToDoSomthing.getMyBubble().addSpeech("With the M-key, you can change the fire type!", 8000);
			}
		});
		
		theWindow.addThingThatDoesSomething(new ActionBlock(false, new Point(27.5*L,1*L),new Point(30.5*L,5*L), theWindow) {
			
			@Override
			public void whatHappens(Stctd stctdToDoSomthing) {
				// TODO Auto-generated method stub
				stctdToDoSomthing.getMyBubble().addSpeech("Do you want to sprint? Hold Shift!", 8000);
			}
		});
		
		
		
		
		theWindow.addThingThatDoesSomething(new ActionBlock(false, new Point(27.5*L,9*L),new Point(30.5*L,10*L), theWindow) {
			
			@Override
			public void whatHappens(Stctd stctdToDoSomthing) {
				// TODO Auto-generated method stub
				stctdToDoSomthing.getMyBubble().addSpeech("Now do you want a party?...", 8000);
			}
		});
		
		theWindow.addThingThatDoesSomething(new ActionBlock(false, new Point(27.5*L,11*L),new Point(30.5*L,12*L), theWindow) {
			
			@Override
			public void whatHappens(Stctd stctdToDoSomthing) {
				// TODO Auto-generated method stub
				stctdToDoSomthing.getMyBubble().addSpeech("After the exams?...", 8000);
			}
		});
		
		theWindow.addThingThatDoesSomething(new ActionBlock(false, new Point(27.5*L,13*L),new Point(30.5*L,14*L), theWindow) {
			
			@Override
			public void whatHappens(Stctd stctdToDoSomthing) {
				// TODO Auto-generated method stub
				stctdToDoSomthing.getMyBubble().addSpeech("On monday the 23th of july at Toms place?", 8000);
			}
		});
		
		theWindow.addThingThatDoesSomething(new ActionBlock(false, new Point(27.5*L,15*L),new Point(30.5*L,16*L), theWindow) {
			
			@Override
			public void whatHappens(Stctd stctdToDoSomthing) {
				// TODO Auto-generated method stub
				stctdToDoSomthing.getMyBubble().addSpeech("With all your friends?!", 8000);
			}
		});
		
		theWindow.addThingThatDoesSomething(new ActionBlock(false, new Point(27.5*L,17*L),new Point(30.5*L,18*L), theWindow) {
			
			@Override
			public void whatHappens(Stctd stctdToDoSomthing) {
				// TODO Auto-generated method stub
				stctdToDoSomthing.getMyBubble().addSpeech("Then go on FaceBook...", 8000);
			}
		});
		
		theWindow.addThingThatDoesSomething(new ActionBlock(false, new Point(27.5*L,19*L),new Point(30.5*L,20*L), theWindow) {
			
			@Override
			public void whatHappens(Stctd stctdToDoSomthing) {
				// TODO Auto-generated method stub
				stctdToDoSomthing.getMyBubble().addSpeech("And send to Tom, that you wil be there!", 8000);
			}
		});
		
		
		
		theWindow.addThingThatDoesSomething(new ActionBlock(false, new Point(27.5*L,21*L),new Point(30.5*L,22*L), theWindow) {
			
			@Override
			public void whatHappens(Stctd stctdToDoSomthing) {
				// TODO Auto-generated method stub
				stctdToDoSomthing.getMyBubble().addSpeech("And Btw...", 8000);
			}
		});
		
		theWindow.addThingThatDoesSomething(new ActionBlock(false, new Point(27.5*L,23*L),new Point(30.5*L,24*L), theWindow) {
			
			@Override
			public void whatHappens(Stctd stctdToDoSomthing) {
				// TODO Auto-generated method stub
				stctdToDoSomthing.getMyBubble().addSpeech("I just want to rape your ass! :D", 8000);
			}
		});
		
		theWindow.addThingThatDoesSomething(new ActionBlock(false, new Point(27.5*L,25*L),new Point(30.5*L,26*L), theWindow) {
			
			@Override
			public void whatHappens(Stctd stctdToDoSomthing) {
				// TODO Auto-generated method stub
				stctdToDoSomthing.getMyBubble().addSpeech("So please confirm that you come! ;)", 8000);
			}
		});

		theWindow.addThingThatDoesSomething(new ActionBlock(false, new Point(27.5*L,27*L),new Point(30.5*L,28*L), theWindow) {
			
			@Override
			public void whatHappens(Stctd stctdToDoSomthing) {
				// TODO Auto-generated method stub
				stctdToDoSomthing.getMyBubble().addSpeech("Have some fun with your exams! your lovely valentine Tom! <3", 8000);
			}
		});
		
		//theWindow.addThingThatDoesSomething(new Vehicle(new Point(2*L,19*L), 2, 2, 0, 1, 1, "autoke",200,60,400,800,5));
		//theWindow.addThingThatDoesSomething(new Vehicle(new Point(2*L,20*L), 2, 2, 0, 1, 1, "autoke",200,60,400,800,5));
		//theWindow.addThingThatDoesSomething(new Vehicle(new Point(2*L,18*L), 2, 2, 0, 1, 1, "autoke",200,60,400,800,5));
		//theWindow.addThingThatDoesSomething(new Vehicle(new Point(2*L,21*L), 2, 2, 0, 1, 1, "autoke",200,60,400,800,5));
		
		//theWindow.getTheUserOfTheScene().getMyBubble().addSpeech("Charlotte is de aaaaaallller liefste van de hele wereld! :D <3", 4000);
		
		//wallMaker(theWindow, wall, wallhub, new Point(0,0), new Point(300,400));
		
		//theWindow.addStctd(new brickass(new Point(50,-80),30,30,0,0,0,"shift"));
		
		/*Point test = new Point(2,2);
		System.out.println(test.getX() + " " + test.getY());
		test.setDistance(200);
		System.out.println(test.getX() + " " + test.getY());
		test.distance();*/
		
		theWindow.gameLoop();
	}
	
	public static void wallMaker(GameWindow window, ArrayList<Brick> toPlace, Brick wallHub, int beginX, int beginY, int endX, int endY) {
		wallMaker(window, toPlace, wallHub, new Point(beginX,beginY), new Point(endX,endY));
	}
	
	public static void wallMaker(GameWindow window, ArrayList<Brick> toPlace, Brick wallHub,  Point beginPoint, Point endPoint) {
		double distancePoints = beginPoint.distance(endPoint);
		//int heightBrick = toPlace.getMySize().getHeight();
		int widthBrick = toPlace.get(0).getMySize().getWidth();
		
		int amoutOfWallBlocks = (int) (distancePoints / widthBrick);
		
		Point direction = new Point(endPoint.getX() - beginPoint.getX(), endPoint.getY() - beginPoint.getY());
		direction.setDistance(widthBrick);
		
		Point currentPlace = new Point(beginPoint);
		
		currentPlace.countUpPoint(direction);
		
		for (int i = 0; i+1 < amoutOfWallBlocks; i++) {
			Brick buf = new Brick(toPlace.get((int) (Math.random() * toPlace.size())));
			buf.setPosition(currentPlace);
			buf.setDirection(direction);
			
			window.addStctd(buf);
			
			currentPlace.countUpPoint(direction);
		}
		
		
		Brick beginWall = new Brick(wallHub);
		Brick endWall = new Brick(wallHub);
		
		beginWall.setDirection(direction);
		endWall.setDirection(direction);
		beginWall.getDirection().rotate(-Math.PI / 2);
		
		beginWall.setPosition(beginPoint);
		endWall.setPosition(currentPlace);
		//beginWall.setMySize(toPlace.getMySize());
		//endWall.setMySize(toPlace.getMySize());
		
		window.addStctd(beginWall);
		window.addStctd(endWall);
	}
	
	public static void doubleWall(GameWindow window, ArrayList<Brick> toPlace, Brick wallHub, ArrayList<Brick> toPlace2, Brick wallHub2,  int beginX, int beginY, int endX, int endY) {
		doubleWall(window, toPlace, wallHub, toPlace2, wallHub2, new Point(beginX, beginY), new Point(endX, endY));
	}
	
	public static void doubleWall(GameWindow window, ArrayList<Brick> toPlace, Brick wallHub, ArrayList<Brick> toPlace2, Brick wallHub2,  Point beginPoint, Point endPoint) {
		double distance = toPlace.get(0).getMySize().getHeight();
		
		wallMaker(window, toPlace, wallHub, beginPoint, endPoint);
		
		Point direction = new Point(endPoint.getX() - beginPoint.getX(), endPoint.getY() - beginPoint.getY());
		direction.setDistance(distance);
		direction.rotate(-Math.PI/2);
		
		beginPoint.countUpPoint(direction);
		endPoint.countUpPoint(direction);
		
		wallMaker(window, toPlace2, wallHub2, beginPoint, endPoint);
		
	}
	
	
	
	public static void backgroundTiles(GameWindow window, ArrayList<SomethingOnTheScene> toPlace, Point beginPoint, int xTiles, int yTiles) {
		Point currentPoint = new Point(beginPoint);
		Size thingSize = toPlace.get(0).getMySize();
		
		for (int i = 0; i < xTiles; i++) {
			for (int j = 0; j < yTiles; j++) {
				SomethingOnTheScene buf = new SomethingOnTheScene(toPlace.get((int) (Math.random() * toPlace.size())));
				buf.setPosition(currentPoint);
				
				window.addBackGround(buf);
				currentPoint.setY(currentPoint.getY() + thingSize.getHeight());
			}
			currentPoint.setY(beginPoint.getY());
			currentPoint.setX(currentPoint.getX() + thingSize.getWidth());
		}
	}

}
