package functions;


import net.UDPClient;

/**
 * 
 * @author Felix Bell
 *
 */
public class CarDataUDPConnection extends Thread{
	
	private static int neutralPosition = 2047;
	
	private boolean running = false;
	private int steering = neutralPosition;
	private int throttle = neutralPosition;

	private int sleepTime = 20;
	
	private UDPClient rcClient;
	
	public CarDataUDPConnection() {
		rcClient = new UDPClient("192.168.178.91",5005);
	}
	
	public CarDataUDPConnection(String ipAddress, int port) {
		rcClient = new UDPClient(ipAddress,port);
	}
	
	public CarDataUDPConnection(String ipAddress, int port, int frequency) {
		rcClient = new UDPClient(ipAddress,port);
		sleepTime = 1000/frequency;
	}
	
	public void run() {
		running = true;
		while(running) {
			rcClient.sendData(getAsByteArray());
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void kill() {
		running = false;
	}
	
	public void setSteering(int steering) {
		System.out.println("Set steering to: " + steering);
		this.steering = steering;
	}
	
	public void setThrottle(int throttle) {
		this.throttle = throttle;
	}
	
	public void setIpAddress(String ipAddress) {
		rcClient.setIpAddress(ipAddress);
	}
	
	public void setPort(int port) {
		rcClient.setPort(port);
	}
	
	public byte[] getAsByteArray() {
		byte[] array = new byte[4];
		
		array[0] = (byte) (throttle>>8 & 0x0F);
		array[1] = (byte) (throttle & 0xFF);
		array[2] = (byte) ((steering>>8 & 0x0F) + 16);
		array[3] = (byte) (steering & 0xFF);
		
		return array;
	}
}
