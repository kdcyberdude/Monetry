package monetry.com.monetry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.NumberFormat;


public class RequestMoney extends AppCompatActivity {

    TextView userName;
    TextView goldCoins;
    TextView silverCoins;
    TextView goldCoinsConversion;
    TextView silverCoinsConversion;
    TextView goldRequested;
    TextView silverRequested;
    TextView goldToRupees;
    TextView silverToRupees;

    LinearLayout dollarClicked;

    private AdView mAdViewTop;
    private AdView mAdViewBottom;

    Button redeemBtn;
    UsersData userData;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_money);

        InterstitialAdLogic interstitialAdLogic;
            interstitialAdLogic = new InterstitialAdLogic(RequestMoney.this);

        userName = findViewById(R.id.userName);
        goldCoins = findViewById(R.id.goldCoins);
        silverCoins = findViewById(R.id.silverCoins);
        goldCoinsConversion = findViewById(R.id.goldCoinsConversion);
        silverCoinsConversion = findViewById(R.id.silverCoinsConversion);
        goldRequested = findViewById(R.id.goldRequested);
        silverRequested = findViewById(R.id.silverRequested);
        redeemBtn = findViewById(R.id.redeemBtn);

        goldToRupees = findViewById(R.id.goldToRupees);
        silverToRupees = findViewById(R.id.silverToRupees);
        dollarClicked = findViewById(R.id.dollarClicked);

        mAdViewTop = findViewById(R.id.adViewTop);
        mAdViewBottom = findViewById(R.id.adViewBottom);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewTop.loadAd(adRequest);
        mAdViewBottom.loadAd(adRequest);


        dollarClicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double goldTemp = Double.parseDouble(goldRequested.getText().toString().trim());
                Double silverTemp = Double.parseDouble(silverRequested.getText().toString().trim());
                goldToRupees.setText(NumberFormat.getCurrencyInstance().format(goldTemp));
                silverToRupees.setText(NumberFormat.getCurrencyInstance().format(silverTemp));
            }
        });


        userData = (UsersData) getIntent().getSerializableExtra("UsersData.class");

        if(userData == null){
            Toast.makeText(this,"Wait to Load!",Toast.LENGTH_LONG).show();
            userName.setText("");
            goldCoins.setText("");
            silverCoins.setText("");
            goldCoinsConversion.setText(NumberFormat.getCurrencyInstance().format(0));
            silverCoinsConversion.setText(NumberFormat.getCurrencyInstance().format(0));

        }
        else {
            userName.setText(userData.getName());
            goldCoins.setText(Integer.toString(userData.getgold() * 10 ) + "");
            silverCoins.setText(Integer.toString(userData.getSilver()) + "");
            goldCoinsConversion.setText(NumberFormat.getCurrencyInstance().format(goldToMoney(userData.getgold())));
            silverCoinsConversion.setText(NumberFormat.getCurrencyInstance().format(silverToMoney(userData.getSilver())));



            redeemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(goldRequested.getText().toString().trim().isEmpty() || silverRequested.getText().toString().trim().isEmpty()) {
                        Toast.makeText(RequestMoney.this,"Enter Coins",Toast.LENGTH_SHORT).show();
                    }else{
                        Integer goldRequestedByUser;
                        Integer silverRequestedByUser;
                        goldRequestedByUser = Integer.parseInt(goldRequested.getText().toString().trim());
                        silverRequestedByUser = Integer.parseInt(silverRequested.getText().toString().trim());

                        openPayTmNumberActivity(goldRequestedByUser,silverRequestedByUser);
                    }




                }
            });
        }
    }

    private void openPayTmNumberActivity(Integer goldRequestedByUser, Integer silverRequestedByUser) {
        if(goldRequestedByUser <= userData.getgold() && goldRequestedByUser > 0 &&
                silverRequestedByUser <= userData.getSilver() && silverRequestedByUser > 0) {

            // converting coins to money
            Double moneyInRupees = convertCoinsToMoney(goldRequestedByUser,silverRequestedByUser);

            Intent intent = new Intent(RequestMoney.this, PayTmNumber.class);
            Bundle extras = new Bundle();
            extras.putString("name", userData.getName());
            extras.putString("email", userData.getEmail());
            extras.putInt("goldRequested", goldRequestedByUser);
            extras.putInt("silverRequested", silverRequestedByUser);
            extras.putDouble("money",moneyInRupees);
            intent.putExtras(extras);
            startActivity(intent);
        }
        else {
            Toast.makeText(RequestMoney.this,"Insufficent Coins!",Toast.LENGTH_SHORT).show();
        }
    }


    private Double goldToMoney(Integer noOfGoldCoins){
        noOfGoldCoins = noOfGoldCoins * 10;
        return (0.025 * noOfGoldCoins);
    }
    private Double silverToMoney(Integer noOfSilverCoins){
        return (0.005 * noOfSilverCoins);
    }
    private Double convertCoinsToMoney(Integer goldRequestedByUser, Integer silverRequestedByUser) {
        return (goldToMoney(goldRequestedByUser)+(silverToMoney(silverRequestedByUser)));
    }
}
