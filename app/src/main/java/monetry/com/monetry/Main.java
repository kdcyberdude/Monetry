package monetry.com.monetry;

import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
public class Main extends AppCompatActivity implements View.OnClickListener {



    static public ArrayList<Blog> firebaseBlogs = new ArrayList<>();
    static public ArrayList<String> websiteURLS = new ArrayList<>();

    private FirebaseDatabase mFirebaseDatabase;

    public static DatabaseReference mBlogsDatabaseRefrence;
    public static DatabaseReference mWebsitesDatabaseReference;
    public static DatabaseReference mUserDataDatabaseReference;
    public static DatabaseReference mUserRequestDatabaseReference;
    public static DatabaseReference mUserDatabaseRefrence;
    public static String firebaseUidOfUser;
    public static Integer goldInFirebase;
    public static Integer silverInFirebase;

    private ChildEventListener mBlogChildEventListener;
    private ChildEventListener mWebsiteChildEventListener;

    private FirebaseAuth mFirebaseAuth;
    protected FirebaseAuth.AuthStateListener mAuthStateListener;

    private CardView readingCard;
    private CardView rewardCard;
    private CardView videosCard;
    private CardView websiteCard;
    private CardView accountCard;

    public UsersData userData;

    public static final int RC_SIGN_IN = 1;

    private AdView mAdViewBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);



        readingCard = findViewById(R.id.readingCard);
        rewardCard = findViewById(R.id.rewardCard);
        videosCard = findViewById(R.id.videosCard);
        websiteCard = findViewById(R.id.websiteCard);
        accountCard = findViewById(R.id.accountCard);

        readingCard.setOnClickListener(this);
        rewardCard.setOnClickListener(this);
        videosCard.setOnClickListener(this);
        websiteCard.setOnClickListener(this);
        accountCard.setOnClickListener(this);

        mAdViewBottom = findViewById(R.id.adViewBottom);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewBottom.loadAd(adRequest);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mBlogsDatabaseRefrence = mFirebaseDatabase.getReference().child("readingBlogs");
        mWebsitesDatabaseReference = mFirebaseDatabase.getReference().child("websiteLinks");
        mUserDataDatabaseReference = mFirebaseDatabase.getReference().child("usersData");
        mUserRequestDatabaseReference = mFirebaseDatabase.getReference().child("usersRequest");
        mUserDatabaseRefrence = mUserDataDatabaseReference;

        mFirebaseAuth = FirebaseAuth.getInstance();






        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //user is logged in
                    mUserDatabaseRefrence.child(user.getUid());     // not used .... but it's write
                    onSignedInIntialize();

                    String name = user.getDisplayName();
                    String email = user.getEmail();
                    String firebaseUid = user.getUid();
                    setUserDataOnFirebase(name,email,0,0,firebaseUid);
                    firebaseUidOfUser = firebaseUid;

                } else {            // not logged in
                    //sign in flow comes here where firebaseUI plays their role
                    onSignOutCleanUp();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(
                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);
                    setContentView(R.layout.main);
                }
            }
        };



    }

    private void setUserDataOnFirebase(final String name,final String email,final int gold,final int silver,final String firebaseUid) {

        DatabaseReference usersDataRefrence = FirebaseDatabase.getInstance().getReference();
        DatabaseReference user = usersDataRefrence.child("usersData").child(firebaseUid);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    //create new user having 0 coins
                    userData = new UsersData(name,email,gold ,silver , firebaseUid);
                    mUserDataDatabaseReference.child(firebaseUid).setValue(userData);
                }
                else if(dataSnapshot.exists()){
                    userData = dataSnapshot.getValue(UsersData.class);
                    if(userData != null) {
                        goldInFirebase = userData.getgold();
                        silverInFirebase = userData.getSilver();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        user.addValueEventListener(eventListener);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {

            if (resultCode == RESULT_OK) {
                Toast.makeText(Main.this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(Main.this, "Sign in cancelled!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mBlogChildEventListener != null || mWebsiteChildEventListener != null)
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.readingCard:
                startActivity(new Intent(Main.this,ReadingInstructions.class));
                break;
            case R.id.rewardCard:
                startActivity(new Intent(Main.this,RewardInstruction.class));
                break;
            case R.id.videosCard:
                startActivity(new Intent(Main.this,VideosInstruction.class));
                break;
            case R.id.websiteCard:
                startActivity(new Intent(Main.this,WebsiteInstruction.class));
                break;
            case R.id.accountCard:
                Intent i = new Intent(this,RequestMoney.class);
                if(userData != null) {

                    i.putExtra("UsersData.class", userData);
                    //sends the userData object to request money class by implemention Serializable in UsersData class
                    startActivity(i);
                }
                break;
        }
    }




    private void onSignOutCleanUp() {
        firebaseBlogs.clear();
        firebaseUidOfUser = null;
        silverInFirebase = 0;
        goldInFirebase = 0;
        websiteURLS.clear();
        detachDatabaseReadListener();
    }

    private void onSignedInIntialize() {
     //   mFirebaseAuth.addAuthStateListener(mAuthStateListener);
        attachDatabaseReadListiener();
    }


    private void attachDatabaseReadListiener() {

        if (mBlogChildEventListener == null) {
                mBlogChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Blog blog = dataSnapshot.getValue(Blog.class);
                        firebaseBlogs.add(blog);
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }
                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                    }
                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                };
            mBlogsDatabaseRefrence.addChildEventListener(mBlogChildEventListener);

            if(mWebsiteChildEventListener == null) {
                mWebsiteChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String url = dataSnapshot.getValue(String.class);
                        websiteURLS.add(url);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                };
                mWebsitesDatabaseReference.addChildEventListener(mWebsiteChildEventListener);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_out, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.signOut:
                AuthUI.getInstance().signOut(this); //sign out
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void detachDatabaseReadListener() {
        if (mBlogChildEventListener != null || mWebsiteChildEventListener != null) {
            mBlogsDatabaseRefrence.removeEventListener(mBlogChildEventListener);
            mWebsitesDatabaseReference.removeEventListener(mWebsiteChildEventListener);
        }
        userData = null;
        goldInFirebase = 0;
        silverInFirebase = 0;
        mBlogChildEventListener = null;
        mWebsiteChildEventListener = null;
    }


}
