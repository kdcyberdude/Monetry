package monetry.com.monetry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class ProceedToPayout extends AppCompatActivity  implements RewardedVideoAdListener {

    private AdView adView1;
    private AdView adView2;
    private AdView adView3;
    private AdView adView4;
    private AdView adView5;
    private AdView adView6;
    private AdView adView7;
    private AdView adView8;
    private AdView adView9;
    private AdView adView10;
    private AdView adView11;

    private RewardedVideoAd mRewardedVideoAd;
    Button getReward;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proceed_to_payout);

        adView1 = findViewById(R.id.adView1);
        adView2 = findViewById(R.id.adView2);
        adView3 = findViewById(R.id.adView3);
        adView4 = findViewById(R.id.adView4);
        adView5 = findViewById(R.id.adView5);
        adView6 = findViewById(R.id.adView6);
        adView7 = findViewById(R.id.adView7);
        adView8 = findViewById(R.id.adView8);
        adView9 = findViewById(R.id.adView9);
        adView10 = findViewById(R.id.adView10);
        adView11 = findViewById(R.id.adView11);



        AdRequest adRequest1 = new AdRequest.Builder().build();
        AdRequest adRequest2 = new AdRequest.Builder().build();
        AdRequest adRequest3 = new AdRequest.Builder().build();
        AdRequest adRequest4 = new AdRequest.Builder().build();
        AdRequest adRequest5 = new AdRequest.Builder().build();
        AdRequest adRequest6 = new AdRequest.Builder().build();
        AdRequest adRequest7 = new AdRequest.Builder().build();
        AdRequest adRequest8 = new AdRequest.Builder().build();
        AdRequest adRequest9 = new AdRequest.Builder().build();
        AdRequest adRequest10 = new AdRequest.Builder().build();

        InterstitialAdLogic interstitialAdLogic;
            interstitialAdLogic = new InterstitialAdLogic(ProceedToPayout.this);

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(ProceedToPayout.this);
        // need to register the listenier with the code
        mRewardedVideoAd.setRewardedVideoAdListener(ProceedToPayout.this);

        loadRewardedVideoAdd();

        adView1.loadAd(adRequest1);
        adView2.loadAd(adRequest2);
        adView3.loadAd(adRequest3);
        adView4.loadAd(adRequest4);
        adView5.loadAd(adRequest5);
        adView6.loadAd(adRequest6);
        adView7.loadAd(adRequest7);
        adView8.loadAd(adRequest8);
        adView9.loadAd(adRequest9);
        adView10.loadAd(adRequest10);
        adView11.loadAd(new AdRequest.Builder().build());

        getReward = findViewById(R.id.proceedToPayout);
        getReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVideoAdd();
            }
        });


    }

    private void loadRewardedVideoAdd(){
        if(!mRewardedVideoAd.isLoaded()){
            mRewardedVideoAd.loadAd(getString(R.string.reward_ad_id),new AdRequest.Builder().build());
        }
    }

    private void startVideoAdd(){
        if(mRewardedVideoAd.isLoaded()){
            mRewardedVideoAd.show();
        }
    }


    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAdd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        Toast.makeText(ProceedToPayout.this, "Gold Added!", Toast.LENGTH_SHORT).show();
        String firebaseUid = Main.firebaseUidOfUser;
        Integer goldTemp = Main.goldInFirebase + 5;
        if(firebaseUid != null){
            Main.mUserDatabaseRefrence.child(firebaseUid).child("gold").setValue(goldTemp);
        }
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        loadRewardedVideoAdd();
    }



}
