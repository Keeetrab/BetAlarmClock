package kosewski.bartosz.betalarmclock.Utils;

import android.content.Context;
import android.os.AsyncTask;

import com.kinvey.android.Client;

import kosewski.bartosz.betalarmclock.BetAlarmClock;

/**
 * Created by Bartosz on 16.03.2016.
 */
public class KinveyUtils {
    public static Client getClient(Context context) {
        Client client = ((BetAlarmClock) context.getApplicationContext()).getClient(context);

        return client;
    }

}
