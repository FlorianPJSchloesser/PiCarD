package main;

import java.net.Socket;

import control.*;
import net.*;

public class Main {
	private TCPServer tcpServer;
	private UDPServer udpServer;
	private PWMControl pwmControl;
	
	private int tcpPort;
	private int udpPort;
		
	public Main(int pTcpPort, int pUdpPort) {
		tcpPort = pTcpPort;
		udpPort = pUdpPort;
		pwmControl = new PWMControl();
		tcpServer = new TCPServer(this, tcpPort);
		udpServer = new UDPServer(this, udpPort);
		udpServer.start();
	}
	
	
	public void handleTcp(Socket s) {
		
	}
	
	public void handleUdp(byte[] data) {
		
	}
	
	public void setChannel(int channel, int value) {
		System.out.println("Set Channel #" + channel + " to: " + value);
		pwmControl.setPin(channel, value);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main(5006, 5005);
	}
	
}
