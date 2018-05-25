package monetry.com.monetry;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class ReadingInstructions extends Activity {


    Button proceedToReadingTask;
    private AdView mAdViewTop;
    private AdView mAdViewBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reading_instructions);


        mAdViewTop = findViewById(R.id.adViewTop);
        mAdViewBottom = findViewById(R.id.adViewBottom);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewTop.loadAd(adRequest);
        mAdViewBottom.loadAd(adRequest);

        InterstitialAdLogic interstitialAdLogic;
        interstitialAdLogic = new InterstitialAdLogic(ReadingInstructions.this);

        proceedToReadingTask = findViewById(R.id.proceedToReadingTask);
        proceedToReadingTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReadingInstructions.this,Reading.class));
            }
        });


    }
}
