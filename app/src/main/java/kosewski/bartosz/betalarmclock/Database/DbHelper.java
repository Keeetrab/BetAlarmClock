package kosewski.bartosz.betalarmclock.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Bartosz on 06.01.2016.
 */
public class DbHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "alarms.db";
    private static final int DB_VERSION = 1;
    public static final String ALARMS_TABLE = "ALARMS";
    public static final String COLUMN_ALARM_HOUR = "HOUR";
    public static final String COLUMN_ALARM_MINUTES = "MINUTES";

    private static String CREATE_ALARMS =
            "CREATE TABLE " + ALARMS_TABLE + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ALARM_HOUR + " INTEGER, " +
                    COLUMN_ALARM_MINUTES + " INTEGER)";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ALARMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
