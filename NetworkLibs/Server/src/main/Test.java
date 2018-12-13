package main;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int channel, value = 0;
		byte data1 = (byte) 0x0F;
		byte data2 = (byte) 0xFF;
		channel = (int) (data1 & 0xF0)>>4;
    	value = (int) (((data1 & 0x0F)<<8) + (data2&0xFF));
    	System.out.println(channel + ":" + value);
    	System.out.println(data2&0xFF);
    	System.out.println((data1 & 0x0F)<<8);
	}

}
