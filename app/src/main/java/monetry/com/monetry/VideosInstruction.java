package monetry.com.monetry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class VideosInstruction extends AppCompatActivity {

    private Button proceedToVideotask;

    private AdView mAdViewTop;
    private AdView mAdViewBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videos_instruction);

        proceedToVideotask = findViewById(R.id.proceedToReadingTask);

        mAdViewTop = findViewById(R.id.adViewTop);
        mAdViewBottom = findViewById(R.id.adViewBottom);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewTop.loadAd(adRequest);
        mAdViewBottom.loadAd(adRequest);

        InterstitialAdLogic interstitialAdLogic;
            interstitialAdLogic = new InterstitialAdLogic(VideosInstruction.this);
    }

    public void openMainActivityClass(View view)
    {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}
