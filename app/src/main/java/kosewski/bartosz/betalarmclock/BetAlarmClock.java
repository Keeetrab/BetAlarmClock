package kosewski.bartosz.betalarmclock;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyPingCallback;


/**
 * Created by Bartosz on 06.01.2016.
 */
public class BetAlarmClock extends Application {

    private static final String TAG = BetAlarmClock.class.getSimpleName();
    public static SQLiteDatabase mDatabase;
    private Client mKinveyClient = null;

    @Override
    public void onCreate() {
        super.onCreate();

        mKinveyClient.ping(new KinveyPingCallback() {
            public void onFailure(Throwable t) {
                Log.e(TAG, "Kinvey Ping Failed", t);
            }

            public void onSuccess(Boolean b) {
                Log.d(TAG, "Kinvey Ping Success");
            }
        });
    }

    public Client getClient() {
        if(mKinveyClient == null){
            mKinveyClient = new Client.Builder("kid_bkH7gX3Gkb","a21e535a533a4e9e8b1d093a1d8081fe",(getApplicationContext())).build();
        }

        return mKinveyClient;
    }
}
