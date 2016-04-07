package kosewski.bartosz.betalarmclock;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.kinvey.android.Client;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;


import kosewski.bartosz.betalarmclock.Utils.ParseConstants;


/**
 * Created by Bartosz on 06.01.2016.
 */
public class BetAlarmClock extends Application {

    private static final String TAG = BetAlarmClock.class.getSimpleName();
    public static SQLiteDatabase mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, BuildConfig.PARSE_APPLICATION_ID, BuildConfig.PARSE_CLIENT_KEY);

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    public static void updateParseInstallation(ParseUser user){
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put(ParseConstants.KEY_USER_ID, user.getObjectId());
        installation.saveInBackground();
    }
}
