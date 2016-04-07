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
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import kosewski.bartosz.betalarmclock.R;

/**
 * Created by Bartosz on 16.03.2016.
 */
public class AddFriendRecyclerViewAdapter extends RecyclerView.Adapter<AddFriendRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = AddFriendRecyclerViewAdapter.class.getSimpleName();
    private List<ParseUser> mUsers;
    private final OnCheckboxClickListener mListener;

    public AddFriendRecyclerViewAdapter (List<ParseUser> users, OnCheckboxClickListener listener){
        mUsers = users;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_addfriend_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ParseUser user = mUsers.get(position);
        holder.mUser = user;
        holder.mUsername.setText(user.getUsername());
        holder.mCheckBox.setChecked(true);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mListener.OnCheckboxClicked(user, isChecked);
            }
        });
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

    public void refill(List<ParseUser> users) {
        mUsers.clear();
        mUsers.addAll(users);
        notifyDataSetChanged();
    }

    public interface OnCheckboxClickListener {
        void OnCheckboxClicked(ParseUser user, Boolean isChecked);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public ParseUser mUser;
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
