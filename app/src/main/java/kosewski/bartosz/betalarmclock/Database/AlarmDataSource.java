package kosewski.bartosz.betalarmclock.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import kosewski.bartosz.betalarmclock.Alarm;

/**
 * Created by Bartosz on 11.01.2016.
 */
public class AlarmDataSource {

    private Context mContext;
    private DbHelper mDbHelper;

    public AlarmDataSource(Context context) {
        mContext = context;
        mDbHelper = new DbHelper(context);
    }


    public void create(Alarm alarm){
        SQLiteDatabase db = open();
        db.beginTransaction();

        ContentValues values = new ContentValues();

        values.put(mDbHelper.COLUMN_ALARM_HOUR, alarm.getHour());
        values.put(mDbHelper.COLUMN_ALARM_MINUTES, alarm.getMinutes());

        db.insert(mDbHelper.ALARMS_TABLE, null, values);

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public List<Alarm> readAlarms() {
        SQLiteDatabase db = open();

        List<Alarm> alarmList = new ArrayList<Alarm>();
        String sql = "SELECT * FROM " + mDbHelper.ALARMS_TABLE + " ORDER BY " + BaseColumns._ID + " DESC";
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do {
                Alarm alarm = new Alarm (
                        getIntFromColumnName(cursor, mDbHelper.COLUMN_ALARM_HOUR),
                        getIntFromColumnName(cursor, mDbHelper.COLUMN_ALARM_MINUTES),
                        getIntFromColumnName(cursor, BaseColumns._ID));
                alarmList.add(alarm);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return alarmList;
    }

    public void update(Alarm alarm){
        SQLiteDatabase db = open();
        db.beginTransaction();

        ContentValues updateAlarmValues = new ContentValues();
        updateAlarmValues.put(mDbHelper.COLUMN_ALARM_HOUR, alarm.getHour());
        updateAlarmValues.put(mDbHelper.COLUMN_ALARM_MINUTES, alarm.getMinutes());

        String where = BaseColumns._ID + " = ?";
        String[] whereArgs = {Integer.toString(alarm.getId())};
        db.update(mDbHelper.ALARMS_TABLE, updateAlarmValues, where, whereArgs);

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void delete(int alarmId){
        SQLiteDatabase db = open();
        db.beginTransaction();

        String where = BaseColumns._ID + " = ?";
        String[] whereArgs = {Integer.toString(alarmId)};

        db.delete(mDbHelper.ALARMS_TABLE, where, whereArgs);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }



    private SQLiteDatabase open() {
        return mDbHelper.getWritableDatabase();
    }

    private int getIntFromColumnName(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getInt(columnIndex);
    }

    private String getStringFromColumnName(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }
}
