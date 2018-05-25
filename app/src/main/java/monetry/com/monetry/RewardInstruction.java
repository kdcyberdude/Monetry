package monetry.com.monetry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class RewardInstruction extends AppCompatActivity {

    Button getCoinsBtn;
    private AdView mAdViewTop;
    private AdView mAdViewBottom;
    private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reward_instruction);



        mAdViewTop = findViewById(R.id.adViewTop);
        mAdViewBottom = findViewById(R.id.adViewBottom);

        AdRequest adRequest = new AdRequest.Builder().build();

        mAdViewTop.loadAd(adRequest);
        mAdViewBottom.loadAd(adRequest);

        InterstitialAdLogic interstitialAdLogic;
            interstitialAdLogic = new InterstitialAdLogic(RewardInstruction.this);

        getCoinsBtn = findViewById(R.id.getCoins);
        getCoinsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code for reward video add
                Intent i = new Intent(RewardInstruction.this,ProceedToPayout.class);
                startActivity(i);
            }
        });

    }






}
