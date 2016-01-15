package kosewski.bartosz.betalarmclock.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kosewski.bartosz.betalarmclock.Alarm;
import kosewski.bartosz.betalarmclock.R;
import kosewski.bartosz.betalarmclock.Utils.Constants;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Alarm} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 *
 */
public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.ViewHolder> {

    private final List<Alarm> mAlarms;
    private final Context mContext;

    public AlarmRecyclerViewAdapter(List<Alarm> items, Context context) {
        mAlarms = items;
        mContext = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bindAlarm(mAlarms.get(position));
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent editIntent = new Intent(mContext, EditAlarm.class);
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

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTimeView = (TextView) view.findViewById(R.id.timeView);
        }

        public void bindAlarm (Alarm alarm){
            mTimeView.setText(alarm.getHour() + ":" + alarm.getMinutes());
        }
    }
}
