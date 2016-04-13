package kosewski.bartosz.betalarmclock.Ringing;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Bartosz on 13.04.2016.
 */
public class AlarmRingtonePlayer {
    private static final String TAG = AlarmRingtonePlayer.class.getSimpleName();
    private MediaPlayer mPlayer;
    private Context mContext;

    public AlarmRingtonePlayer(Context context) {
        mContext = context;
    }

    public void initialize() {
        try {
            mPlayer = new MediaPlayer();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void cleanup() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void play(Uri toneUri) {
        try {
            if (mPlayer != null && !mPlayer.isPlaying()) {
                mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
                mPlayer.setDataSource(mContext, toneUri);
                mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mPlayer.setLooping(true);
                mPlayer.prepareAsync();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void stop() {
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop();
            }
            mPlayer.reset();
        }
    }

}
