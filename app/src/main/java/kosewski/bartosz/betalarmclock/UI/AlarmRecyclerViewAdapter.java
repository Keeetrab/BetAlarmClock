package kosewski.bartosz.betalarmclock.UI;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kosewski.bartosz.betalarmclock.Alarm;
import kosewski.bartosz.betalarmclock.R;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Alarm} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.ViewHolder> {

    private final List<Alarm> mAlarms;
    private final OnListFragmentInteractionListener mListener;

    public AlarmRecyclerViewAdapter(List<Alarm> items, OnListFragmentInteractionListener listener) {
        mAlarms = items;
        mListener = listener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bindAlarm(mAlarms.get(position));
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
