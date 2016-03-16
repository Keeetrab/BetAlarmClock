package kosewski.bartosz.betalarmclock;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyPingCallback;

import kosewski.bartosz.betalarmclock.Utils.Constants;
import kosewski.bartosz.betalarmclock.Utils.KinveyConstants;


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

    }

    public static Client getClient(Context context) {
        if (mKinveyClient == null){
            mKinveyClient = new Client.Builder(KinveyConstants.APP_ID,KinveyConstants.APP_SECRET
                    , context.getApplicationContext()).build();
        }


        return mKinveyClient;
    }
}
