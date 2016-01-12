package kosewski.bartosz.betalarmclock;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

/**
 * Created by Bartosz on 06.01.2016.
 */
public class BetAlarmClock extends Application {

    public static SQLiteDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();



    }
}
