package main;

import com.exlumina.j360.*;

public class Test implements ValueListener{

	public static void main(String[] args) {
		Test test = new Test();
		
	}
	
	private Controller c;
	
	public Test() {
		c = Controller.C1;
		c.rightThumbX.addValueChangedListener(this);
		
	}

	@Override
	public void value(int arg0) {
		// TODO Auto-generated method stub
		 int value = (arg0 + 32768) * (4095 - 0) / (32767 + 32768) + 0;
		
		System.out.println(value);
	}

}
