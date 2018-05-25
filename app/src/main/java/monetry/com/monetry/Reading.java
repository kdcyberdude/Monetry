package monetry.com.monetry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

public class Reading extends AppCompatActivity {

    private TextView blogTitle;
    private TextView blogData;
    private Button changeArticle;
    private AdView mAdViewTop;
    private AdView mAdViewBottom;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reading);

        mAdViewTop = findViewById(R.id.adViewTop);
        mAdViewBottom = findViewById(R.id.adViewBottom);

        mAdViewTop.loadAd(new AdRequest.Builder().build());
        mAdViewBottom.loadAd(new AdRequest.Builder().build());

        blogTitle = findViewById(R.id.blogTitle);
        blogData = findViewById(R.id.blogData);
        changeArticle = findViewById(R.id.changeArticle);

        InterstitialAdLogic interstitialAdLogic;
            interstitialAdLogic = new InterstitialAdLogic(Reading.this);

        changeArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdViewTop.loadAd(new AdRequest.Builder().build());
                mAdViewBottom.loadAd(new AdRequest.Builder().build());
                InterstitialAdLogic interstitialAdLogic;
                    interstitialAdLogic = new InterstitialAdLogic(Reading.this);
                Random random = new Random();
                int index = random.nextInt(Main.firebaseBlogs.size());
                if(Main.firebaseBlogs.size() > 0) {
                    blogTitle.setText(Main.firebaseBlogs.get(index).getTitle());
                    blogData.setText(Main.firebaseBlogs.get(index).getArticle());
                }
            }
        });


        Random random = new Random();
        int index = random.nextInt(Main.firebaseBlogs.size());
            if(Main.firebaseBlogs.size() > 0) {
                blogTitle.setText(Main.firebaseBlogs.get(index).getTitle());
                blogData.setText(Main.firebaseBlogs.get(index).getArticle());
            }

    }


}
