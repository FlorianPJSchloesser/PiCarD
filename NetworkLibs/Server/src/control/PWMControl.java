package control;

import java.math.BigDecimal;
import com.pi4j.gpio.extension.pca.PCA9685GpioProvider;
import com.pi4j.gpio.extension.pca.PCA9685Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;

public class PWMControl {
	private static final int SERVO_DURATION_MIN = 900;
    private static final int SERVO_DURATION_NEUTRAL = 1500;
    private static final int SERVO_DURATION_MAX = 2100;
    private static final int BIT_RESOLUTION = 4096;
    
    // 4096 Steps (12 Bit)
    // T = 4096 * 0.000005s = 0.02048s
    // f = 1 / T = 48.828125
    private BigDecimal frequency;
    private BigDecimal frequencyCorrectionFactor;
    
    private I2CBus bus;
    private PCA9685GpioProvider provider;
    
    private GpioPinPwmOutput[] outputs;
    
    public PWMControl() {
    	frequency = new BigDecimal("48.828");
    	frequencyCorrectionFactor = new BigDecimal("1");
    	try {
			bus = I2CFactory.getInstance(I2CBus.BUS_1);
			provider = new PCA9685GpioProvider(bus, 0x40, frequency, frequencyCorrectionFactor);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	outputs = provisionPwmOutputs(provider);
    	
    	
    	provider.reset();
    }
    
    public void setPin(int pin, int value) {
    	Pin pPin = PCA9685Pin.ALL[pin];
    	int duration = mapDutyCycle(value);
    	provider.setPwm(pPin, duration);
    }
    
    private int mapDutyCycle(int value) {
    	return (value - 0) * (SERVO_DURATION_MAX - SERVO_DURATION_MIN) / (4095 - 0) + SERVO_DURATION_MIN;
    }    
	
    private static GpioPinPwmOutput[] provisionPwmOutputs(final PCA9685GpioProvider gpioProvider) {
        GpioController gpio = GpioFactory.getInstance();
        GpioPinPwmOutput myOutputs[] = {
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_00, "Pulse 00"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_01, "Pulse 01"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_02, "Pulse 02"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_03, "Pulse 03"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_04, "Pulse 04"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_05, "Pulse 05"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_06, "Pulse 06"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_07, "Pulse 07"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_08, "Pulse 08"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_09, "Pulse 09"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_10, "Pulse 10"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_11, "Pulse 11"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_12, "Pulse 12"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_13, "Pulse 13"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_14, "Pulse 14"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_15, "Pulse 15")};
        return myOutputs;
    }
}
