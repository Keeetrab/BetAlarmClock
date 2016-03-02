package kosewski.bartosz.betalarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import java.awt.font.TextAttribute;

import kosewski.bartosz.betalarmclock.UI.AlarmActivity;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    private static final String TAG = AlarmReceiver.class.getSimpleName();

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Toast.makeText(context, "Alarm dziala", Toast.LENGTH_LONG).show();
        int id =  intent.getIntExtra("ID", -1);
        Log.i(TAG, "Alarm launched: id: " + id);

        Intent alarmIntent = new Intent(context, AlarmActivity.class);
        alarmIntent.putExtra("ID", id);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmIntent);
    }
}
