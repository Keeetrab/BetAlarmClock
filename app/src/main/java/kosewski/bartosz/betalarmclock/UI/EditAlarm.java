package kosewski.bartosz.betalarmclock.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.List;

import kosewski.bartosz.betalarmclock.Alarm;
import kosewski.bartosz.betalarmclock.AlarmReceiver;
import kosewski.bartosz.betalarmclock.Database.AlarmDataSource;
import kosewski.bartosz.betalarmclock.R;
import kosewski.bartosz.betalarmclock.Utils.Constants;

public class EditAlarm extends AppCompatActivity {
    private static final String TAG = EditAlarm.class.getSimpleName();
    private TimePicker mTimePicker;
    private ImageView mDeleteButton;
    private int mHour;
    private int mMinutes;
    private Alarm mAlarm;
    private List<Alarm> mAlarms;
    private AlarmDataSource mDataSource = new AlarmDataSource(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getEditedAlarmData();

        // UI
        setContentView(R.layout.activity_edit_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTimePicker = (TimePicker) findViewById(R.id.timePicker);
                // Editing existing Alarm
        if(mAlarm != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mTimePicker.setHour(mAlarm.getHour());
                mTimePicker.setMinute(mAlarm.getMinutes());
            } else {
                mTimePicker.setCurrentHour(mAlarm.getHour());
                mTimePicker.setCurrentMinute(mAlarm.getMinutes());
            }

            mDeleteButton = (ImageView) findViewById(R.id.deleteImageView);
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteAlarm(mAlarm);
                    setAlarms();
                    finish();
                }
            });
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm();
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private void deleteAlarm(Alarm alarm) {
        cancelAlarms();
        mDataSource.delete(alarm.getId());
    }

    private void getEditedAlarmData() {
        Intent intent = getIntent();
        if(intent.hasExtra(Constants.ALARM)){
            mAlarm = intent.getParcelableExtra(Constants.ALARM);
        }

    }

    private void setAlarm() {
        //Cancel all alarms first
        cancelAlarms();

        //Create new alarm or update current one

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mHour = mTimePicker.getHour();
            mMinutes = mTimePicker.getMinute();
        } else {
            mHour = mTimePicker.getCurrentHour();
            mMinutes = mTimePicker.getCurrentMinute();
        }

        if(mAlarm != null){
            mAlarm.setHour(mHour);
            mAlarm.setMinutes(mMinutes);

            mDataSource.update(mAlarm);
        } else {
            Alarm alarm = new Alarm(mHour, mMinutes);
            mDataSource.create(alarm);
        }
        //Set all alarms

        setAlarms();
    }

    private void cancelAlarms() {
        List<Alarm> alarms = mDataSource.readAlarms();

        if(alarms != null) {
            for(Alarm alarm : alarms){
                PendingIntent pendingIntent = createPendingIntent(EditAlarm.this, alarm);
                Log.i(TAG, "Canceling alarm: " + alarm.getId() + "Time: " + alarm.getHour() + ":" + alarm.getMinutes());

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);

            }
        }
    }

    private void setAlarms() {
        List<Alarm> alarms = mDataSource.readAlarms();

        for(Alarm alarm : alarms) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
            calendar.set(Calendar.MINUTE, alarm.getMinutes());
            calendar.set(Calendar.SECOND, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            
            PendingIntent pendingIntent = createPendingIntent(EditAlarm.this, alarm);
            Log.i(TAG, "Setting Alarm: " + alarm.getId() + "Time: " + alarm.getHour() + ":" + alarm.getMinutes());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }
    }

    private PendingIntent createPendingIntent(Context context, Alarm alarm) {
        
        Intent alarmIntent = new Intent(EditAlarm.this, AlarmReceiver.class);
        alarmIntent.putExtra("ID", alarm.getId());
        
        return PendingIntent.getBroadcast(context, alarm.getId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
