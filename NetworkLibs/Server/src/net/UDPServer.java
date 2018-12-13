package net;

import java.io.*;
import java.net.*;

import main.*;

public class UDPServer extends Thread{
	private DatagramSocket udpSocket;
    private int port;
    private Main main;
    private boolean running;
    
    public UDPServer(Main main, int port) {
        this.port = port;
        this.main = main;
        try {
			this.udpSocket = new DatagramSocket(this.port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        running = true;
    }
    
    public void run() {
                       
        while (running) {
            
            byte[] buf = new byte[32];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            
            // blocks until a packet is received
            try {
				udpSocket.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            int length = packet.getLength();
            if(length%2==0) {
	            byte[] data = packet.getData();
	            int channel = 0;
	            int value = 0;
	            for(int i=0;i<packet.getLength();i=i+2) {
	            	channel = (int) (data[i] & 0xF0)>>4;
	            	value = (int) (((data[i] & 0x0F)<<8) + (data[i+1]&0xFF));
	            	main.setChannel(channel, value);
	            }
            }
            main.handleUdp(packet.getData());
        }
    }
    
    public void stopServer() {
    	running = false;
    }
}
