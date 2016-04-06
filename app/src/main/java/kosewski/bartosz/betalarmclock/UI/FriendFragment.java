package kosewski.bartosz.betalarmclock.UI;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.api.client.util.ArrayMap;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.AppData;
import com.kinvey.java.Query;
import com.kinvey.java.User;
import com.kinvey.java.model.KinveyReference;

import java.util.ArrayList;

import kosewski.bartosz.betalarmclock.R;
import kosewski.bartosz.betalarmclock.UI.Adapters.FriendRecyclerViewAdapter;
import kosewski.bartosz.betalarmclock.Utils.KinveyConstants;
import kosewski.bartosz.betalarmclock.Utils.KinveyUtils;

public class FriendFragment extends Fragment {

    public static final String TAG = FriendFragment.class.getSimpleName();

    public ArrayList<ArrayMap> mFriends;
    public Client mKinveyClient;

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
        mKinveyClient = KinveyUtils.getClient(getContext());
        mFriends = getUserFriends();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_list, container, false);


        // Set the adapter
      if (view instanceof RecyclerView) {
          Context context = view.getContext();
          mRecyclerView = (RecyclerView) view;
          mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
          mRecyclerView.setAdapter(new FriendRecyclerViewAdapter(mFriends));
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

    private ArrayList<ArrayMap> getUserFriends() {
        AsyncAppData<KinveyReference> friends = mKinveyClient.appData(KinveyConstants.FRIENDS, KinveyReference.class);
        Query query = mKinveyClient.query();

        friends.get(query, new KinveyListCallback<KinveyReference>() {
            @Override
            public void onSuccess(KinveyReference[] kinveyReferences) {
                Log.i(TAG, "Got : " + kinveyReferences.length);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });

        return null;
    }

}
