package kosewski.bartosz.betalarmclock.UI.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.api.client.util.ArrayMap;
import com.kinvey.java.User;

import java.util.ArrayList;

import kosewski.bartosz.betalarmclock.R;

/**
 * Created by Bartosz on 16.03.2016.
 */
public class AddFriendRecyclerViewAdapter extends RecyclerView.Adapter<AddFriendRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = AddFriendRecyclerViewAdapter.class.getSimpleName();
    private ArrayList<ArrayMap> mUsers;

    public AddFriendRecyclerViewAdapter (ArrayList<ArrayMap> users){
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
        ArrayMap user = mUsers.get(position);
        holder.mUser = user;
        holder.mUsername.setText((String) user.get("_id"));
        holder.mCheckBox.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        int length = 0;
        if(mUsers!= null) {
            length = mUsers.size();
        }
        Log.i(TAG, "getItemCount length : " + length);
        return length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public ArrayMap mUser;
        public TextView mUsername;
        public CheckBox mCheckBox;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mUsername = (TextView) itemView.findViewById(R.id.usernameField);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.addFriendBox);
        }


    }
}
