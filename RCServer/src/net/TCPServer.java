package net;

import java.io.*;
import java.net.*;
import main.*;

public class TCPServer extends Thread{
	private Main main;
	private int port;
	private boolean running;
	private ServerSocket server;
		
	public TCPServer(Main pMain, int pPort) {
		main = pMain;
		port = pPort;
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		running = true;
	}
	
	public void run() {
		
		while(running) {
			try {
				Socket s = server.accept();
				while(running) {
				
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stopServer() {
		running = false;
	}
	
}
