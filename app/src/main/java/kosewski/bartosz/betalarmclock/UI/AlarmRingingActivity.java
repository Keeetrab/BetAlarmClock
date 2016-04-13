package kosewski.bartosz.betalarmclock.UI;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import kosewski.bartosz.betalarmclock.Model.Alarm;
import kosewski.bartosz.betalarmclock.Database.AlarmDataSource;
import kosewski.bartosz.betalarmclock.R;
import kosewski.bartosz.betalarmclock.Scheduling.AlarmScheduler;
import kosewski.bartosz.betalarmclock.Utils.Constants;
import kosewski.bartosz.betalarmclock.Utils.GeneralUtilities;

public class AlarmRingingActivity extends AppCompatActivity {
    private Vibrator mVibrator;
    private Ringtone mRingtone;
    private AlarmDataSource mDataSource;
    private Alarm mAlarm;

    private boolean mIsClosedProperly = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GeneralUtilities.setLockingScreenFlags(getWindow());

        setContentView(R.layout.activity_alarm);

        getAlarm();


        // The ringing effects
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        mRingtone = RingtoneManager.getRingtone(this, alarmUri);
        mRingtone.play();
        mVibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = { 0, 300, 200, 300, 100, 100 };
        mVibrator.vibrate(pattern, 0);

        // UI
        Button stopButton = (Button) findViewById(R.id.finishButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        cancelAlarm();
            }
        });

        Button snoozeButton = (Button) findViewById(R.id.snoozeButton);
        snoozeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snooze();
            }
        });
    }

    @Override
    protected void onPause() {
        ///TODO tu zmienic bo dziala nawet na snoozie
        if(!mIsClosedProperly){
          snooze();
        }
        super.onPause();
    }

    private void cancelAlarm() {
        stopRinging();

        if(mAlarm.isOneShot()){
            mAlarm.setIsEnabled(false);
            mDataSource.update(mAlarm);
        }

        //Schedule new alarms
        AlarmScheduler.cancelAlarms(this);
        AlarmScheduler.setAlarms(this);

        mIsClosedProperly = true;
        finish();
    }


    private void snooze() {
        stopRinging();
        
        AlarmScheduler.cancelSingleAlarm(this, mAlarm);
        AlarmScheduler.snoozeAlarm(this, mAlarm, Constants.SNOOZE_DURATION);

        mIsClosedProperly = true;
        finish();
    }


    private void stopRinging() {
        mVibrator.cancel();
        mRingtone.stop();
    }

    private void getAlarm() {
        Intent intent = getIntent();
        int alarmId = intent.getIntExtra("ID", -1);

        mDataSource = new AlarmDataSource(this);
        mAlarm = mDataSource.readAlarmById(alarmId);
    }

    @Override
    public void onBackPressed() {

    }
}
