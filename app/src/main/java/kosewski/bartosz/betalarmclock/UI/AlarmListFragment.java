package kosewski.bartosz.betalarmclock.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kosewski.bartosz.betalarmclock.Model.Alarm;
import kosewski.bartosz.betalarmclock.Database.AlarmDataSource;
import kosewski.bartosz.betalarmclock.R;
import kosewski.bartosz.betalarmclock.UI.Adapters.AlarmRecyclerViewAdapter;


public class AlarmListFragment extends Fragment {


    private AlarmDataSource mDataSource;
    private Context mContext;
    private List<Alarm> mAlarmList;
    private RecyclerView mRecyclerView;


    public AlarmListFragment() {}


    @SuppressWarnings("unused")
    public static AlarmListFragment newInstance() {
        AlarmListFragment fragment = new AlarmListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        mDataSource = new AlarmDataSource(mContext);
        mAlarmList = mDataSource.readAlarms();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAlarmList = mDataSource.readAlarms();
        mRecyclerView.setAdapter(new AlarmRecyclerViewAdapter(mAlarmList,mContext));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerView.setAdapter(new AlarmRecyclerViewAdapter(mAlarmList, mContext));
        }
        return view;
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
