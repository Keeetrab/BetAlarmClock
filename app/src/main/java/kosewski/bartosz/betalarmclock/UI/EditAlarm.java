package kosewski.bartosz.betalarmclock.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;

import kosewski.bartosz.betalarmclock.Alarm;
import kosewski.bartosz.betalarmclock.AlarmReceiver;
import kosewski.bartosz.betalarmclock.Database.AlarmDataSource;
import kosewski.bartosz.betalarmclock.R;

public class EditAlarm extends AppCompatActivity {
    private TimePicker mTimePicker;
    private int mHour;
    private int mMinutes;
    private AlarmDataSource mDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTimePicker = (TimePicker) findViewById(R.id.timePicker);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm();
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDataSource = new AlarmDataSource(this);


    }

    private void setAlarm() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mHour = mTimePicker.getHour();
            mMinutes = mTimePicker.getMinute();
        } else {
            mHour = mTimePicker.getCurrentHour();
            mMinutes = mTimePicker.getCurrentMinute();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, mHour);
        calendar.set(Calendar.MINUTE, mMinutes);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(EditAlarm.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(EditAlarm.this, 0, alarmIntent, 0);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        Alarm alarm = new Alarm(mHour, mMinutes);
        mDataSource.create(alarm);

    }

}
