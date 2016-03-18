package kosewski.bartosz.betalarmclock.UI.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kinvey.java.User;

import java.util.List;

import kosewski.bartosz.betalarmclock.R;

/**
 * Created by Bartosz on 16.03.2016.
 */
public class AddFriendRecyclerViewAdapter extends RecyclerView.Adapter<AddFriendRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = AddFriendRecyclerViewAdapter.class.getSimpleName();
    private User[] mUsers;

    public AddFriendRecyclerViewAdapter (User[] users){
        mUsers = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_addfriend_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = mUsers[position];
        holder.mUser = user;
        holder.mUsername.setText(user.getUsername());

    }

    @Override
    public int getItemCount() {
        int length = 0;
        if(mUsers!= null) {
            length = mUsers.length;
        }
        Log.i(TAG, "getItemCount length : " + length);
        return length;
    }

    public void updateData(User[] users){
        mUsers = users;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public User mUser;
        public TextView mUsername;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mUsername = (TextView) itemView.findViewById(R.id.usernameField);
        }


    }
}
