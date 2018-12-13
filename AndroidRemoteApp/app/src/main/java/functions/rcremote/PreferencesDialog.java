package functions.rcremote;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import functions.rcremote.PreferencesWrapper;
import functions.rcremote.R;

/**
 * Created by Florian Schlösser on 09.04.2017.
 */
public class PreferencesDialog extends Activity implements View.OnClickListener {

    public final static String TAG = PreferencesDialog.class.getSimpleName();

    /* Views */
    private TextInputEditText mIPEdit;
    private TextInputEditText mPortEdit;
    private TextInputEditText mIdentifierEdit;
    private ImageButton mResetIPButton;
    private ImageButton mResetPortButton;
    private Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_server);

        mIPEdit = (TextInputEditText) findViewById(R.id.editIP);
        mPortEdit = (TextInputEditText) findViewById(R.id.editPort);
        mIdentifierEdit = (TextInputEditText) findViewById(R.id.editIdentifier);
        mResetPortButton = (ImageButton) findViewById(R.id.buttonPortDefault);
        mResetIPButton = (ImageButton) findViewById(R.id.buttonIPDefault);
        mSaveButton = (Button) findViewById(R.id.buttonSave);

        applyPreferences();

        mResetIPButton.setOnClickListener(this);
        mResetPortButton.setOnClickListener(this);
        mSaveButton.setOnClickListener(this);
    }

    private void applyPreferences () {
        PreferencesWrapper preferences = new PreferencesWrapper(this);

        String ipAdress = preferences.getServerAdress();
        String port = String.valueOf(preferences.getServerPort());
        String identifier = String.valueOf(preferences.getClientIdentifier());

        mIPEdit.setText(ipAdress.equals("") ? ipAdress : "");
        mPortEdit.setText(port.equals("") ? port : "");
        mIdentifierEdit.setText(identifier.equals("") ? identifier : "");
    }

    @Override
    public void onClick(View view) {
        if (view.equals(mResetIPButton)) {
            mIPEdit.setText("192.168.");
        } else if (view.equals(mResetPortButton)) {
            mPortEdit.setText("16382");
        } else if (view.equals(mSaveButton)) {
            PreferencesWrapper preferences = new PreferencesWrapper(this);
            preferences.setServerAddress(mIPEdit.getText().toString());
            preferences.setServerPort(Integer.valueOf(mPortEdit.getText().toString()));
            preferences.setClientIdentifier(Integer.valueOf(mIdentifierEdit.getText().toString()));
            finish();
        }
    }
}
