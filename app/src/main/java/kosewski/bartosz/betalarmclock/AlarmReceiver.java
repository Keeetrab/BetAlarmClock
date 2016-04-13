package kosewski.bartosz.betalarmclock;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import kosewski.bartosz.betalarmclock.Ringing.AlarmRingingActivity;

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

        Intent alarmIntent = new Intent(context, AlarmRingingActivity.class);
        alarmIntent.putExtra("ID", id);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmIntent);
    }
}
