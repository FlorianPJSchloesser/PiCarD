package functions.rcremote;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Random;

/**
 * Created by Florian Schlösser on 06.04.2017.
 */
public class PreferencesWrapper {

    public final static String TAG = PreferencesWrapper.class.getSimpleName();

    private final static String PREF_ADDRESS = "server_address";
    private final static String PREF_PORT = "server_port";
    private final static String PREF_IDENTIFIER = "client_identifier";

    private SharedPreferences mPreferences;

    public PreferencesWrapper(Activity activity) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    public void setServerAddress (String address) {
        mPreferences.edit().putString(PREF_ADDRESS, address).commit();
    }

    public void setServerPort (int port) {
        mPreferences.edit().putInt(PREF_PORT, port).apply();
    }

    public String getServerAdress () {
        return mPreferences.getString(PREF_ADDRESS, "0.0.0.0");
    }

    public int getServerPort () {
        return mPreferences.getInt(PREF_PORT, 16382);
    }

    public int getClientIdentifier () {
        return mPreferences.getInt(PREF_IDENTIFIER, new Random().nextInt(253) + 1);
    }

    public void setClientIdentifier (int identifier) {
        mPreferences.edit().putInt(PREF_IDENTIFIER, identifier).apply();
    }

}
