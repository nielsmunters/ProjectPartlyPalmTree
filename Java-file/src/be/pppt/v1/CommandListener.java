package be.pppt.v1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import be.pppt.v1.GameWindow;


public class CommandListener extends Thread {

	private GameWindow myWindow = null;
	private int portIN = 2001;
	private int portOUT = 2000;
	private int userID = 0;
	private String address;
	DatagramSocket socket = null;
	
	public CommandListener(GameWindow myWindow, int userID, String address) {
		super();
		this.myWindow = myWindow;
		this.start();
		
		this.userID = userID;
		this.address = address;
	}

	public void run() {
		
		try {
			socket = new DatagramSocket(portIN);
			
			byte[] buf = new byte[512];

            // receive request
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            while (true) {
            	socket.receive(packet);
            	String received = new String(packet.getData(), 0, packet.getLength());
            	
            	this.giveCommand(received);
            }
            
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("done");
    }
	
	public void giveCommand(String command) {
		String[] partsCommand = {command.substring(0, 2), command.substring(3, 8), command.substring(9)};
		
		if (partsCommand[1].equals("SEPOS")) {
			String[] positionParts = {partsCommand[2].substring(0,6),partsCommand[2].substring(7,0)};
			
			myWindow.giveUserNewPos(new Point(Integer.parseInt(positionParts[0]), Integer.parseInt(positionParts[1])),userID);
		}
		else if (partsCommand[1].equals("PRESS")) {
			myWindow.giveUserKeyPressAction(partsCommand[2], Integer.parseInt(partsCommand[0]));
		}
		else if (partsCommand[1].equals("RELEA")) {
			myWindow.giveUserKeyReleasedAction(partsCommand[2], Integer.parseInt(partsCommand[0]));
		}
		else if (partsCommand[1].equals("MOUSE")) {
			System.out.println("mouse?");
			myWindow.giveUserMouseAction(partsCommand[2], Integer.parseInt(partsCommand[0]));
		}
		else if (partsCommand[1].equals("WHEEL")) {
			myWindow.giveUserMouseWheelAction(partsCommand[2], Integer.parseInt(partsCommand[0]));
		}
	}
	
	public void sendCommand(String toSend) {
		
		byte[] buf = toSend.getBytes();
		
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, portOUT);
        try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
