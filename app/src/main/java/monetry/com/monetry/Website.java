package monetry.com.monetry;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;


public class Website extends AppCompatActivity{
    private WebView myWebView;
    Button changeWebsiteBtn;
    private AdView mAdViewBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.website);
        mAdViewBottom = findViewById(R.id.adViewBottom);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewBottom.loadAd(adRequest);
        InterstitialAdLogic interstitialAdLogic;
            interstitialAdLogic = new InterstitialAdLogic(Website.this);

        changeWebsiteBtn = findViewById(R.id.changeWebsite);
        changeWebsiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InterstitialAdLogic interstitialAdLogic;
                    interstitialAdLogic = new InterstitialAdLogic(Website.this);
                myWebView.loadUrl(selectRandomURL());
            }
        });

        myWebView = (WebView)findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl(selectRandomURL());
        myWebView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed() {
        if(myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nextUrl) {

            myWebView.loadUrl(selectRandomURL());

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private String selectRandomURL(){
        Random random = new Random();
        int index = random.nextInt(Main.websiteURLS.size());
        if(Main.websiteURLS.size() > 0) {
            return Main.websiteURLS.get(index);
        }
        return "http://www.google.com";             // default weired website
    }

}