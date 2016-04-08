package kosewski.bartosz.betalarmclock.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

import kosewski.bartosz.betalarmclock.R;
import kosewski.bartosz.betalarmclock.UI.Adapters.AddFriendRecyclerViewAdapter;
import kosewski.bartosz.betalarmclock.UI.Adapters.FriendRecyclerViewAdapter;
import kosewski.bartosz.betalarmclock.Utils.ParseConstants;

public class FriendFragment extends Fragment {

    public static final String TAG = FriendFragment.class.getSimpleName();

    public Context mContext;
    public RecyclerView mRecyclerView;

    protected ParseRelation<ParseUser> mFriendsRelation;
    protected ParseUser mCurrentUser;
    protected List<ParseUser> mFriends;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FriendFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentUser = ParseUser.getCurrentUser();
        mFriendsRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_list, container, false);

        // Set the adapter
      if (view instanceof RecyclerView) {
          mRecyclerView = (RecyclerView) view;
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        populateFriendsList();
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

    private void populateFriendsList() {
        mFriendsRelation.getQuery()
                .addAscendingOrder(ParseConstants.KEY_USERNAME)
                .findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> list, ParseException e) {
                        if (e == null) {
                            mFriends = list;

                            // Fill and set Adapter
                            if (mRecyclerView.getAdapter() == null) {
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                                mRecyclerView.setAdapter(new FriendRecyclerViewAdapter(mFriends));
                            } else {
                                ((FriendRecyclerViewAdapter) mRecyclerView.getAdapter()).refill(mFriends);
                            }

                        } else {
                            Log.i(TAG, e.getMessage());
                            Toast.makeText(getContext(), R.string.error_toast, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
