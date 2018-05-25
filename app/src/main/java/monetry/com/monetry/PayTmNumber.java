package monetry.com.monetry;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.NumberFormat;

public class PayTmNumber extends AppCompatActivity {

    TextView userName;
    Button registerBtn;
    EditText paytmNumber;

    private AdView mAdViewTop;
    private AdView mAdViewBottom;

    Dialog confirmationDilogBox;

    String payTmNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_tm_number);

        userName = findViewById(R.id.userName);
        registerBtn = findViewById(R.id.registerBtn);
        paytmNumber = findViewById(R.id.paytmNumber);


        InterstitialAdLogic interstitialAdLogic;
            interstitialAdLogic = new InterstitialAdLogic(PayTmNumber.this);

        mAdViewTop = findViewById(R.id.adViewTop);
        mAdViewBottom = findViewById(R.id.adViewBottom);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewTop.loadAd(adRequest);
        mAdViewBottom.loadAd(adRequest);



        Bundle extras = getIntent().getExtras();
        final String nameOfUser = extras.getString("name");
        final String emailOfUser = extras.getString("email");
        final Integer goldRequestedByUser = extras.getInt("goldRequested");
        final Integer silverRequestedByUser = extras.getInt("silverRequested");
        final Double totalMoneyOfCoins = extras.getDouble("money");

        userName.setText(nameOfUser);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payTmNumber = paytmNumber.getText().toString().trim();
                confirmationDilogBox = new Dialog(PayTmNumber.this);
                Log.i("PayTmNumber", "show Confirmation popUP to confirm");
                TextView close;
                Button confirmed;
                confirmationDilogBox.setContentView(R.layout.confirmation_pop_up);
                confirmationDilogBox.show();

                close = confirmationDilogBox.findViewById(R.id.closepopUp);
                confirmed = confirmationDilogBox.findViewById(R.id.btnConfirm);

                TextView nameOfUserTV;
                TextView numberTV;
                TextView goldCoinsTV;
                TextView silverCoinsTV;
                TextView rupeesTV;

                nameOfUserTV = confirmationDilogBox.findViewById(R.id.name);
                numberTV = confirmationDilogBox.findViewById(R.id.number);
                goldCoinsTV = confirmationDilogBox.findViewById(R.id.goldCoins);
                silverCoinsTV = confirmationDilogBox.findViewById(R.id.silverCoins);
                rupeesTV = confirmationDilogBox.findViewById(R.id.rupees);

                nameOfUserTV.setText(nameOfUser);
                numberTV.setText(payTmNumber);
                goldCoinsTV.setText(goldRequestedByUser + "");
                silverCoinsTV.setText(silverRequestedByUser + "");
                rupeesTV.setText(NumberFormat.getCurrencyInstance().format(totalMoneyOfCoins));


                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmationDilogBox.dismiss();
                    }
                });

                confirmed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // send entry on firebase
                        payTmNumber = paytmNumber.getText().toString().trim();
                        if(payTmNumber.isEmpty()){
                            Toast.makeText(PayTmNumber.this,"Your Number is invalid!",Toast.LENGTH_LONG).show();
                        }
                        UsersRequest usersRequest;
                        usersRequest = new UsersRequest(nameOfUser, emailOfUser, payTmNumber, goldRequestedByUser, silverRequestedByUser, true, totalMoneyOfCoins);

                        if (goldRequestedByUser <= Main.goldInFirebase && silverRequestedByUser <= Main.silverInFirebase) {
                            Main.mUserRequestDatabaseReference.push().setValue(usersRequest);  //set value with a push id
                            Main.goldInFirebase -= goldRequestedByUser;
                            Main.silverInFirebase -= silverRequestedByUser;
                            Main.mUserDatabaseRefrence.child(Main.firebaseUidOfUser).child("gold").setValue(Main.goldInFirebase);
                            Main.mUserDatabaseRefrence.child(Main.firebaseUidOfUser).child("silver").setValue(Main.silverInFirebase);
                            Toast.makeText(PayTmNumber.this, "Congratulations! Your money will be transfered within 24 hours", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(PayTmNumber.this, Main.class));
                        } else {
                            Toast.makeText(PayTmNumber.this, "INVALID ACTIVITY", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                confirmationDilogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                confirmationDilogBox.show();
            }
        });
    }

}