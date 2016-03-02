package kosewski.bartosz.betalarmclock;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.user.User;
import com.shephertz.app42.paas.sdk.android.user.UserService;

/**
 * Created by Bartosz on 06.01.2016.
 */
public class BetAlarmClock extends Application {

    private static final String TAG = BetAlarmClock.class.getSimpleName();
    public static SQLiteDatabase mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        App42API.initialize(this, "4f14819a0826ae695083257ff9f5bb2f4b78976b1b6f0d43e8e53a86bd6b0b27", "7edc0797bd9b34f08b9cc30b2f3491d8842bfef2e2972471c480e407945fb456");
    }
}
