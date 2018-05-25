package monetry.com.monetry;

/**
 * Created by KD on 3/2/2018.
 */

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;


public class InterstitialAdLogic extends AppCompatActivity {
    private static int silverRatio;

    private InterstitialAd interstitialAd;

    public InterstitialAdLogic(final Context activityContext) {
        Log.i("InterstitialAdLogic","Constructer Invoked");
       interstitialAd = new InterstitialAd(activityContext);
//        interstitialAd.setAdUnitId(getString(R.string.interstetial_ad_id));
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        interstitialAd.loadAd(new AdRequest.Builder().build());

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                if (interstitialAd.isLoaded()) {
                    //interstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                silverRatio++;
                Log.i("Interstitial Ad","silverRatio : " + silverRatio);
                if(silverRatio == 13){
                    Toast toast = Toast.makeText(activityContext, "Click on this AD!", Toast.LENGTH_LONG);
                    ViewGroup group = (ViewGroup) toast.getView();
                    TextView messageTextView = (TextView) group.getChildAt(0);
                    messageTextView.setTextSize(25);
                    toast.show();
                }
                else if(silverRatio >= 16 ){
                    silverRatio = 0;
                }
                else{
                    String firebaseUid = Main.firebaseUidOfUser;
                    Integer silverTemp = Main.silverInFirebase;
                    silverTemp++;
                    if (firebaseUid != null) {
                        Main.mUserDatabaseRefrence.child(firebaseUid).child("silver").setValue(silverTemp);
                    }
                }
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                if(silverRatio >=13 && silverRatio <= 15) { // logic for ratio
                    silverRatio = 0;
                    Random random = new Random();
                    int timeInMilliSeconds = random.nextInt(20000) + 10000;     // 10 to 30 seconds
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Toast.makeText(activityContext, "Gold Added!", Toast.LENGTH_SHORT).show();
                            String firebaseUid = Main.firebaseUidOfUser;
                            Integer goldTemp = Main.goldInFirebase;
                            goldTemp++;
                            if (firebaseUid != null) {
                                Main.mUserDatabaseRefrence.child(firebaseUid).child("gold").setValue(goldTemp);
                            }
                        }
                    }, timeInMilliSeconds);
                }
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
            }
        });


    }




}
