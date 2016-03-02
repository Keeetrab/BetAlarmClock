package kosewski.bartosz.betalarmclock.UI;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TimePicker;

import java.util.List;

import kosewski.bartosz.betalarmclock.Alarm;
import kosewski.bartosz.betalarmclock.Database.AlarmDataSource;
import kosewski.bartosz.betalarmclock.R;
import kosewski.bartosz.betalarmclock.Scheduling.AlarmScheduler;
import kosewski.bartosz.betalarmclock.Utils.Constants;

public class EditAlarm extends AppCompatActivity {
    private static final String TAG = EditAlarm.class.getSimpleName();
    private TimePicker mTimePicker;
    private ImageView mDeleteButton;
    private CheckBox[] mDayCheckBoxes;

    private int mHour;
    private int mMinutes;
    private Alarm mAlarm;
    private List<Alarm> mAlarms;
    private AlarmDataSource mDataSource = new AlarmDataSource(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAlarm = getEditedAlarmData();

        // UI
        setContentView(R.layout.activity_edit_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTimePicker = (TimePicker) findViewById(R.id.timePicker);
        setDayCheckboxes();

                // Editing existing Alarm
        if(mAlarm != null) {
            //Setting timepicker
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mTimePicker.setHour(mAlarm.getHour());
                mTimePicker.setMinute(mAlarm.getMinutes());
            } else {
                mTimePicker.setCurrentHour(mAlarm.getHour());
                mTimePicker.setCurrentMinute(mAlarm.getMinutes());
            }
            //Setting days
            boolean[] days = new boolean[7];
            System.arraycopy(mAlarm.getDays(), 0 , days, 0, days.length);

            for(int i = 0; i<7; i++){
                mDayCheckBoxes[i].setChecked(days[i]);
            }


            mDeleteButton = (ImageView) findViewById(R.id.deleteImageView);
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlarmScheduler.deleteAlarm(EditAlarm.this, mAlarm);
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

    private void setDayCheckboxes() {
        mDayCheckBoxes = new CheckBox[7];
        for(int i = 1; i < 8; i++){
            String boxName = "checkBox" + i;
            CheckBox checkBox = (CheckBox) findViewById(getResources().getIdentifier(boxName, "id", getPackageName()));
            mDayCheckBoxes[i-1] = checkBox;
        }
    }

    private Alarm getEditedAlarmData() {
        Intent intent = getIntent();
        if(intent.hasExtra(Constants.ALARM)){
            Alarm alarm = intent.getParcelableExtra(Constants.ALARM);
            return alarm;
        } else {
            return null;
        }
    }

    private void setAlarm() {
        //Cancel all alarms first
        AlarmScheduler.cancelAlarms(this);

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
            mAlarm.setDays(getRepeatingDays());

            mDataSource.update(mAlarm);
        } else {
            Alarm alarm = new Alarm(mHour, mMinutes, getRepeatingDays());
            mDataSource.create(alarm);
        }
        //Set all alarms

        AlarmScheduler.setAlarms(this);
    }

    private boolean[] getRepeatingDays() {
        boolean[] days = new boolean[7];
        for(int i = 0; i<7; i++){
            days[i] = mDayCheckBoxes[i].isChecked();
        }

        return days;
    }
}
