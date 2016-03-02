package kosewski.bartosz.betalarmclock.UI;

import android.content.Context;
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

import kosewski.bartosz.betalarmclock.R;

public class AlarmActivity extends AppCompatActivity {
    private Vibrator mVibrator;
    private Ringtone mRingtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);


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
                        mVibrator.cancel();
                        mRingtone.stop();
                        finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
