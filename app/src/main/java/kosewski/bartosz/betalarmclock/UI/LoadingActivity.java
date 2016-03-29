package kosewski.bartosz.betalarmclock.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.kinvey.android.Client;

import kosewski.bartosz.betalarmclock.R;
import kosewski.bartosz.betalarmclock.Utils.KinveyUtils;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     /*   this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/
        setContentView(R.layout.activity_loading);

        IntentLauncher launcher = new IntentLauncher();
        launcher.execute();


    }

    private class IntentLauncher extends AsyncTask {

        @Override
        protected Client doInBackground(Object[] params) {
            Client client  = KinveyUtils.getClient(LoadingActivity.this);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return client;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            Intent launcher = new Intent(LoadingActivity.this, MainActivity.class);
            LoadingActivity.this.startActivity(launcher);
        }
    }
}
