package kosewski.bartosz.betalarmclock.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Bartosz on 06.01.2016.
 */
public class DbHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "alarms.db";
    private static final int DB_VERSION = 4;
    public static final String ALARMS_TABLE = "ALARMS";
    public static final String COLUMN_ALARM_HOUR = "HOUR";
    public static final String COLUMN_ALARM_MINUTES = "MINUTES";
    public static final String COLUMN_ALARM_REPEATING_DAYS = "REPEATING_DAYS";
    public static final String COLUMN_ALARM_IS_ENABLED = "IS_ENABLED";
    public static final String COLUMN_TIMES_SNOOZED = "TIMES_SNOOZED";
    public static final String COLUMN_LAST_ACTIVE = "LAST_TIME_ACTIVE";

    private static String CREATE_ALARMS =
            "CREATE TABLE " + ALARMS_TABLE + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ALARM_HOUR + " INTEGER, " +
                    COLUMN_ALARM_MINUTES + " INTEGER, " +
                    COLUMN_ALARM_REPEATING_DAYS + " TEXT, " +
                    COLUMN_ALARM_IS_ENABLED + " INTEGER, " +
                    COLUMN_TIMES_SNOOZED + " INTEGER, " +
                    COLUMN_LAST_ACTIVE + " INTEGER)";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ALARMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DbHelper.class.getSimpleName(),
                "Upgrading database from version " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + ALARMS_TABLE);
        onCreate(db);
    }
}
