package net;

import java.net.*;

public class UDPClient {
	private InetAddress IPAddress;
	private int port;
	
	public UDPClient(InetAddress IPAddress, int port) {
		this.IPAddress = IPAddress;
		this.port = port;
	}
	
	public UDPClient(String IPAdress, int port) {
		try {
			this.IPAddress = InetAddress.getByName(IPAdress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.port = port;
	}
	
	public void sendData(byte[] sendData) {
		DatagramSocket clientSocket;
		try {
			clientSocket = new DatagramSocket();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			clientSocket.send(sendPacket);
			clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void setIpAddress(String IpAddress) {
		try {
			this.IPAddress = InetAddress.getByName(IpAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void setPort(int port) {
		this.port = port;
	}
}
