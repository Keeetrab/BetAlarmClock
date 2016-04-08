package kosewski.bartosz.betalarmclock.UI.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.api.client.util.ArrayMap;
import com.parse.ParseUser;

import kosewski.bartosz.betalarmclock.R;

import java.util.ArrayList;
import java.util.List;


//TODO: Replace the implementation with code for your data type.

public class FriendRecyclerViewAdapter extends RecyclerView.Adapter<FriendRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = FriendRecyclerViewAdapter.class.getSimpleName();
    private List<ParseUser> mFriends;

    public FriendRecyclerViewAdapter(List<ParseUser> items) {
        mFriends = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_friend_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ParseUser user = mFriends.get(position);
        holder.mUser = user;
        holder.mUsername.setText(user.getUsername());

    }

    @Override
    public int getItemCount() {
        int length = 0;
        if(mFriends!= null) {
            length = mFriends.size();
        }
        Log.i(TAG, "getItemCount length : " + length);
        return length;
    }

    public void refill(List<ParseUser> users) {
        if(mFriends != null) {
            mFriends.clear();
            mFriends.addAll(users);
        } else {
            mFriends = users;
        }

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public ParseUser mUser;
        public TextView mUsername;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUsername = (TextView) view.findViewById(R.id.nameField);
        }
    }
}
