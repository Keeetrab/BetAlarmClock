package kosewski.bartosz.betalarmclock;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyPingCallback;

import kosewski.bartosz.betalarmclock.Utils.Constants;


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
        final Client mKinveyClient = new Client.Builder(Constants.APP_ID,Constants.APP_SECRET
                , this.getApplicationContext()).build();

    }

    public static Client getClient() {
        return mKinveyClient;
    }
}
