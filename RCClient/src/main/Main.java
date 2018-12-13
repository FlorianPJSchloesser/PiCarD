package main;

import java.util.Scanner;

import com.exlumina.j360.*;

public class Main{

	public static void main(String[] args) {
		Main main = new Main();
	}
	
	private RCClient client;
	
	public Main() {
		client = new RCClient();
		client.start();
		Scanner reader = new Scanner(System.in);
		
		Controller c = Controller.C1;
		SteeringListener steeringListener = new SteeringListener();
		ThrottleListener throttleListener = new ThrottleListener();
		c.leftThumbX.addValueChangedListener(steeringListener);
		c.rightThumbY.addValueChangedListener(throttleListener);
		while(true) {
			int steering = reader.nextInt();
			System.out.println("Set steering to:" + steering);
			client.setSteering(steering);
		}
	}


	public void value(int arg0) {
		// TODO Auto-generated method stub
		int value = (arg0 + 32768) * (4095 - 0) / (32767 + 32768) + 0;
		client.setSteering(value);
	}
	
	public class SteeringListener implements ValueListener{
		public void value(int arg0) {
			// TODO Auto-generated method stub
			int value = (arg0 + 32768) * (0 - 4095) / (32767 + 32768) + 4095;
			client.setSteering(value);
		}
	}
	
	public class ThrottleListener implements ValueListener{
		public void value(int arg0) {
			// TODO Auto-generated method stub
			int value = (arg0 + 32768) * (0 - 4095) / (32767 + 32768) + 4095;
			client.setThrottle(value);
		}
	}

}
