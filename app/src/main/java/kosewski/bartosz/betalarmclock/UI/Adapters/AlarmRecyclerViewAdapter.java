package kosewski.bartosz.betalarmclock.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import kosewski.bartosz.betalarmclock.Model.Alarm;
import kosewski.bartosz.betalarmclock.Database.AlarmDataSource;
import kosewski.bartosz.betalarmclock.R;
import kosewski.bartosz.betalarmclock.Scheduling.AlarmScheduler;
import kosewski.bartosz.betalarmclock.UI.EditAlarmActivity;
import kosewski.bartosz.betalarmclock.Utils.Constants;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Alarm} and makes a call to the
 * specified {OnListFragmentInteractionListener}.
 *
 */
public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = AlarmRecyclerViewAdapter.class.getSimpleName();

    private final List<Alarm> mAlarms;
    private final Context mContext;
    private final AlarmDataSource mDataSource;

    public AlarmRecyclerViewAdapter(List<Alarm> items, Context context) {
        mAlarms = items;
        mContext = context;
        mDataSource = new AlarmDataSource(context);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_alarm_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bindAlarm(mAlarms.get(position));
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent editIntent = new Intent(mContext, EditAlarmActivity.class);
                Alarm alarm = mAlarms.get(position);
                editIntent.putExtra(Constants.ALARM, alarm);
                editIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(editIntent);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mAlarms.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTimeView;
        public final Switch mEnabledSwitch;

        public final TextView mMonday;
        public final TextView mTuesday;
        public final TextView mWednesday;
        public final TextView mThursday;
        public final TextView mFriday;
        public final TextView mSaturday;
        public final TextView mSunday;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTimeView = (TextView) view.findViewById(R.id.timeView);
            mEnabledSwitch = (Switch) view.findViewById(R.id.onOffSwitch);

            mMonday = (TextView) view.findViewById(R.id.monTextView);
            mTuesday = (TextView) view.findViewById(R.id.tueTextView);
            mWednesday = (TextView) view.findViewById(R.id.wedTextView);
            mThursday = (TextView) view.findViewById(R.id.thuTextView);
            mFriday = (TextView) view.findViewById(R.id.friTextView);
            mSaturday = (TextView) view.findViewById(R.id.satTextView);
            mSunday = (TextView) view.findViewById(R.id.sunTextView);
        }

        public void bindAlarm (final Alarm alarm){
            mTimeView.setText(formatAlarmTime(alarm));
            mEnabledSwitch.setChecked(alarm.isEnabled());
            mEnabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    alarm.setIsEnabled(isChecked);
                    mDataSource.update(alarm);
                    AlarmScheduler.cancelAlarms(mContext);
                    AlarmScheduler.setAlarms(mContext);
                    Log.i(TAG, "Alarm is Enabled : " + alarm.isEnabled());
                }
            });
            setDays(alarm.getDays());

        }

        //Todo napisac to po ludzku

        private void setDays(boolean[] days) {
            if(days[0]){
                mMonday.setTextColor(mView.getContext().getResources().getColor(R.color.colorAccent));
            }
            if(days[1]){
                mTuesday.setTextColor(mView.getContext().getResources().getColor(R.color.colorAccent));
            }
            if(days[2]){
                mWednesday.setTextColor(mView.getContext().getResources().getColor(R.color.colorAccent));
            }
            if(days[3]){
                mThursday.setTextColor(mView.getContext().getResources().getColor(R.color.colorAccent));
            }
            if(days[4]){
                mFriday.setTextColor(mView.getContext().getResources().getColor(R.color.colorAccent));
            }
            if(days[5]){
                mSaturday.setTextColor(mView.getContext().getResources().getColor(R.color.colorAccent));
            }
            if(days[6]){
                mSunday.setTextColor(mView.getContext().getResources().getColor(R.color.colorAccent));
            }
        }

        private String formatAlarmTime (Alarm alarm) {
            String hour = (alarm.getHour() < 10 ? "0" : "") + alarm.getHour();
            String minutes = (alarm.getMinutes() < 10 ? "0" : "") + alarm.getMinutes();
            return hour + ":" + minutes;
        }
    }
}
