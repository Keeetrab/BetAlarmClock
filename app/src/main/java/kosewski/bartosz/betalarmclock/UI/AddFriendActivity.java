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

import java.util.ArrayList;

import kosewski.bartosz.betalarmclock.R;
import kosewski.bartosz.betalarmclock.UI.Adapters.AddFriendRecyclerViewAdapter;
import kosewski.bartosz.betalarmclock.Utils.KinveyConstants;
import kosewski.bartosz.betalarmclock.Utils.KinveyUtils;

public class AddFriendActivity extends AppCompatActivity {

    private static final String TAG = AddFriendActivity.class.getSimpleName();
    public ArrayList<ArrayMap> mFriends;

    public EditText mSearchField;
    public TextView mSearchedField;
    public CheckBox mAddFriendCheckBox;
    public ProgressBar mProgressBar;
    public RecyclerView mRecyclerView;

    public Client mKinveyClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mKinveyClient = KinveyUtils.getClient(this);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mSearchedField = (TextView) findViewById(R.id.searchedTextView);
        mAddFriendCheckBox = (CheckBox) findViewById(R.id.addFriendCheckBox);
        mAddFriendCheckBox.setVisibility(View.INVISIBLE);

        // List of current friends

        //TODO  zrobione na Arraymap


        mFriends = getUserFriends();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new AddFriendRecyclerViewAdapter(mFriends));



        //FAB

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

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

                mSearchedField.setText(searchedName);
                //Search for a user
                AsyncUserDiscovery users = mKinveyClient.userDiscovery();
                users.lookupByUserName(searchedName.toString(), new KinveyUserListCallback() {
                    @Override
                    public void onSuccess(User[] users) {
                        //if user found show checkbox to add a friend
                        if(users.length != 0){
                            mAddFriendCheckBox.setVisibility(View.VISIBLE);
                        }
                        mProgressBar.setVisibility(View.INVISIBLE);

                        Log.i(TAG, "received " + users.length + " users");
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.i(TAG, "received " + throwable);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        //Add friend checkbox

        mAddFriendCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String friendId = mSearchField.getText().toString();

                if(isChecked){
                    //Add friend
                    checkIfFirstFriend();

                    KinveyReference friend = new KinveyReference(User.USER_COLLECTION_NAME, friendId);
                    mKinveyClient.appData(User.USER_COLLECTION_NAME, KinveyReference.class).save(friend, new KinveyClientCallback<KinveyReference>() {
                        @Override
                        public void onSuccess(KinveyReference kinveyReference) {
                            Toast.makeText(AddFriendActivity.this, "update successful", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            Toast.makeText(AddFriendActivity.this, "update successful", Toast.LENGTH_SHORT).show();
                        }
                    });
                   /* ((ArrayList<KinveyReference>) mKinveyClient.user().get(KinveyConstants.FRIENDS)).add(friend);
*/


                } else {
                    //Remove friend

                }
            }
        });

    }

    private void checkIfFirstFriend() {
        if (mKinveyClient.user().get(KinveyConstants.FRIENDS) == null){
            mKinveyClient.user().put(KinveyConstants.FRIENDS, new ArrayList<KinveyReference>());

            mKinveyClient.user().update(new KinveyUserCallback() {
                @Override
                public void onSuccess(User user) {
                    Toast.makeText(AddFriendActivity.this, "update successful", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Toast.makeText(AddFriendActivity.this, "update successful", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private ArrayList<ArrayMap> getUserFriends() {
        return (ArrayList<ArrayMap>) mKinveyClient.user().get(KinveyConstants.FRIENDS);
    }
}
