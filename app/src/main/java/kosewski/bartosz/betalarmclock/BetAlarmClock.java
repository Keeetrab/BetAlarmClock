package kosewski.bartosz.betalarmclock;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyPingCallback;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;


import kosewski.bartosz.betalarmclock.Utils.Constants;
import kosewski.bartosz.betalarmclock.Utils.KinveyConstants;
import kosewski.bartosz.betalarmclock.Utils.ParseConstants;


/**
 * Created by Bartosz on 06.01.2016.
 */
public class BetAlarmClock extends Application {

    private static final String TAG = BetAlarmClock.class.getSimpleName();
    public static SQLiteDatabase mDatabase;
    private static Client mKinveyClient = null;

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "aPYAGlnDIdVmPY4SjeHkrPBJpej1AdiGF9DItS13", "nkRw9nxgUAoRRTQY4JlfvuLQ8fzPKtOH1vvbKnWM");

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    public static void updateParseInstallation(ParseUser user){
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put(ParseConstants.KEY_USER_ID, user.getObjectId());
        installation.saveInBackground();
    }


    //TODO wykasowac
    public static Client getClient(Context context) {
        if (mKinveyClient == null){
            mKinveyClient = new Client.Builder(KinveyConstants.APP_ID,KinveyConstants.APP_SECRET
                    , context.getApplicationContext()).build();
        }


        return mKinveyClient;
    }
}
