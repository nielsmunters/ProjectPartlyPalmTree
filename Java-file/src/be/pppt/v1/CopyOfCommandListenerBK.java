package be.pppt.v1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import be.pppt.v1.GameWindow;


public class CopyOfCommandListenerBK extends Thread {

	GameWindow myWindow = null;
	
	public CopyOfCommandListenerBK(GameWindow myWindow) {
		super();
		this.myWindow = myWindow;
		this.start();
	}

	public void run() {
		int port = 2000;
		
		try (
			ServerSocket serverSocket = new ServerSocket(port);
			Socket clientSocket = serverSocket.accept();     
			PrintWriter out =new PrintWriter(clientSocket.getOutputStream(), true);                   
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				) {
			System.out.println("ok ik heb verbinding!");
			
			//out.println(0 + "");
			
			String inputLine;
			while ((inputLine = in.readLine()) != null) {

				System.out.println(inputLine);
				String[] partsCommand = {inputLine.substring(0, 2), inputLine.substring(3, 8), inputLine.substring(9)};
				
				if (partsCommand[1].equals("PRESS")) {
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
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port " + port + " or listening for a connection");
			System.out.println(e.getMessage());
		}
    }
}
