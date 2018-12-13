package functions.rcremote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.erz.joysticklibrary.JoyStick;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;

import main.RCClient;
import pl.pawelkleczkowski.customgauge.CustomGauge;

/**
 * The main screen providing the user with ui elements to control the RC vehicle.
 */
public class RemoteControlActivity extends AppCompatActivity implements View.OnClickListener {

    /* VIEWS*/
    private JoyStick mSteeringStick;
    private JoyStick mThrottleStick;
    private Button mConnectButton;
    private TextView mConnectionStatusLabel;
    private ProgressBar mConnectionProgressBar;
    private ImageButton mServerPreferencesButton;
    private CustomGauge mRpmView;
    private TextView mRpmLabel;
    private MultiStateToggleButton mLightsToggle;
    private MultiStateToggleButton mSpeedToggle;
    private TextView mLatencyLabel;

    private RCClient mRCClient;

    private boolean mConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_remote_control);

        mSteeringStick = (JoyStick) findViewById(R.id.joystick_steering);
        mThrottleStick = (JoyStick) findViewById(R.id.joystick_throttle);
        mConnectButton = (Button) findViewById(R.id.button_connect);
        mConnectionStatusLabel = (TextView) findViewById(R.id.label_connection);
        mConnectionProgressBar = (ProgressBar) findViewById(R.id.progress_connection);
        mServerPreferencesButton = (ImageButton) findViewById(R.id.button_server_prefs);
        mRpmView = (CustomGauge) findViewById(R.id.gauge_rpm);
        mRpmLabel = (TextView) findViewById(R.id.label_rpm);
        mLatencyLabel = (TextView) findViewById(R.id.label_latency);
        mLightsToggle = (MultiStateToggleButton) findViewById(R.id.mstb_lights);
        mSpeedToggle = (MultiStateToggleButton) findViewById(R.id.mstb_speeds);

        //Prepare joystick views
        mSteeringStick.setType(JoyStick.TYPE_2_AXIS_LEFT_RIGHT);
        mThrottleStick.setType(JoyStick.TYPE_2_AXIS_UP_DOWN);
        mSteeringStick.setListener(mSteeringListener);
        mThrottleStick.setListener(mThrottleListener);
        mSteeringStick.setPadColor(getColor(R.color.colorPrimary));
        mThrottleStick.setPadColor(getColor(R.color.colorPrimary));
        mSteeringStick.setButtonColor(getColor(R.color.colorAccent));
        mThrottleStick.setButtonColor(getColor(R.color.colorAccent));

        //Set listeners
        mConnectButton.setOnClickListener(this);
        mServerPreferencesButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.equals(mConnectButton)) {
            if (!mConnected) {
                PreferencesWrapper preferencesWrapper = new PreferencesWrapper(this);
                String ipAddress = preferencesWrapper.getServerAddress();
                int port = preferencesWrapper.getServerPort();

                mRCClient = new RCClient();
                mRCClient.setIpAddress(ipAddress);
                mRCClient.setPort(port);
                mRCClient.start();

                mConnected = true;
            } else {
                mRCClient.pause();
                mConnected = false;
            }
            updateUI();
        } else if (view.equals(mServerPreferencesButton)) {
            Intent startServerPreferencesDialogIntent = new Intent(this, PreferencesDialog.class);
            startActivity(startServerPreferencesDialogIntent);
        }
    }

    private void updateUI () {
        if (mConnected) {
            mConnectionStatusLabel.setVisibility(View.INVISIBLE);
            mServerPreferencesButton.setVisibility(View.GONE);
            mLatencyLabel.setVisibility(View.VISIBLE);
            mConnectButton.setText(getString(R.string.action_disconnect));
        } else {
            mRpmView.setValue(0);
            mRpmLabel.setText("0");
            mLightsToggle.setValue(0);
            mSpeedToggle.setValue(0);
            mServerPreferencesButton.setVisibility(View.VISIBLE);
            mLatencyLabel.setVisibility(View.GONE);
            mConnectionStatusLabel.setVisibility(View.VISIBLE);
            mConnectButton.setText(getString(R.string.action_connect));
        }
    }

    private JoyStick.JoyStickListener mThrottleListener = new JoyStick.JoyStickListener() {


        @Override
        public void onMove(JoyStick joyStick, double angle, double power, int direction) {
            switch (direction) {
                case -1:
                    throttle(2047);
                    break;
                case 2:
                    //down
                    int throttleDown = 2047 - (int) (2047 * (power / 100));
                    throttle(throttleDown);
                    break;
                case 6:
                    //up
                    int throttleUp = 2047 + (int) (2047 * (power / 100));
                    throttle(throttleUp);
                    break;
            }
        }

        @Override
        public void onTap() {

        }

        @Override
        public void onDoubleTap() {
            //TODO toggle "power level"
        }
    };

    private JoyStick.JoyStickListener mSteeringListener = new JoyStick.JoyStickListener() {

        @Override
        public void onMove(JoyStick joyStick, double angle, double power, int direction) {
            switch (direction) {
                case -1:
                    steer(2047);
                    break;
                case 0:
                    //left
                    int steerL = 2047 - (int) (2047 * (power / 100));
                    steer(steerL);
                    break;
                case 4:
                    //right
                    int steerR = 2047 + (int) (2047 * (power / 100));
                    steer(steerR);
                    break;
            }
        }

        @Override
        public void onTap() {

        }

        @Override
        public void onDoubleTap() {
            //TODO toggle lights
        }

    };

    private void throttle(int throttle) {
        mRCClient.setThrottle(throttle);
    }

    private void steer(int steer) {
        mRCClient.setSteering(steer);
    }
}
