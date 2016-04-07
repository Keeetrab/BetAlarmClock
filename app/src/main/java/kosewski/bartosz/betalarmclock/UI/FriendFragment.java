package kosewski.bartosz.betalarmclock.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kosewski.bartosz.betalarmclock.R;

public class FriendFragment extends Fragment {

    public static final String TAG = FriendFragment.class.getSimpleName();


    public RecyclerView mRecyclerView;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FriendFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_list, container, false);


        // Set the adapter
      if (view instanceof RecyclerView) {
          Context context = view.getContext();
          //TODO adapter
          /*mRecyclerView = (RecyclerView) view;
          mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
          mRecyclerView.setAdapter(new FriendRecyclerViewAdapter(mFriends));*/
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /*    @Override
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
