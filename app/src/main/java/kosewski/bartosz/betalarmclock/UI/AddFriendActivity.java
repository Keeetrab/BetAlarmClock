package kosewski.bartosz.betalarmclock.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.util.ArrayMap;
import com.kinvey.android.AsyncUserDiscovery;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.android.callback.KinveyUserListCallback;
import com.kinvey.java.User;
import com.kinvey.java.core.KinveyClientCallback;
import com.kinvey.java.model.KinveyReference;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import kosewski.bartosz.betalarmclock.R;
import kosewski.bartosz.betalarmclock.UI.Adapters.AddFriendRecyclerViewAdapter;
import kosewski.bartosz.betalarmclock.Utils.KinveyConstants;
import kosewski.bartosz.betalarmclock.Utils.KinveyUtils;
import kosewski.bartosz.betalarmclock.Utils.ParseConstants;

public class AddFriendActivity extends AppCompatActivity {

    private static final String TAG = AddFriendActivity.class.getSimpleName();

    public EditText mSearchField;
    public TextView mSearchedField;
    public CheckBox mAddFriendCheckBox;
    public ProgressBar mProgressBar;
    public RecyclerView mRecyclerView;

    public Client mKinveyClient;

    protected List<ParseUser> mFriends;
    protected ParseRelation<ParseUser> mFriendsRelation;
    protected ParseUser mCurrentUser;
    protected ParseUser mFoundUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCurrentUser = ParseUser.getCurrentUser();
        mFriendsRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);

        mKinveyClient = KinveyUtils.getClient(this);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mSearchedField = (TextView) findViewById(R.id.searchedTextView);
        mAddFriendCheckBox = (CheckBox) findViewById(R.id.addFriendCheckBox);
        mAddFriendCheckBox.setVisibility(View.INVISIBLE);


        // List of current friends
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        populateFriendsList();

        //FAB

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Search Field

        mSearchField = (EditText) findViewById(R.id.searchField);
        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence searchedName, int start, int before, int count) {
                //update textView while typing
                mAddFriendCheckBox.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);

                //Search for a user
                ParseQuery<ParseUser> query = ParseQuery.getQuery("_User");
                query.whereStartsWith(ParseConstants.KEY_USERNAME, searchedName.toString());
                query.whereNotEqualTo(ParseConstants.KEY_USERNAME, mCurrentUser.getUsername());
                if(searchedName.length() > 0) {
                    query.getFirstInBackground(new GetCallback<ParseUser>() {
                        @Override
                        public void done(ParseUser object, ParseException e) {
                            if (object != null) {
                                mAddFriendCheckBox.setVisibility(View.VISIBLE);
                                mFoundUser = object;
                                mSearchedField.setText(object.getUsername());
                            } else {
                                mSearchedField.setText(" ");
                            }
                        }
                    });
                } else {
                    mSearchedField.setText(" ");
                }
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        //Add friend checkbox

        mAddFriendCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mFriendsRelation.add(mFoundUser);
                    Log.i(TAG, "Added: " + mFoundUser.getUsername());
                } else {
                    //Remove friend
                    mFriendsRelation.remove(mFoundUser);
                    Log.i(TAG, "Deleted: " + mFoundUser.getUsername());
                }

                mCurrentUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                });
            }
        });

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
                            if(mRecyclerView.getAdapter() == null){
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(AddFriendActivity.this));
                                mRecyclerView.setAdapter(new AddFriendRecyclerViewAdapter(mFriends));
                            } else{
                                ((AddFriendRecyclerViewAdapter) mRecyclerView.getAdapter()).refill(list);
                            }

                        } else {
                            Log.i(TAG, e.getMessage());
                            Toast.makeText(AddFriendActivity.this, R.string.error_toast, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
