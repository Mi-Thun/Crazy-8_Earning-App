package com.example.crazy8;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class Main3Activity extends AppCompatActivity {

    private AdView AdView,AdView1;
    private InterstitialAd mInterstitialAd;
    private Button mButton;
    private TextView textView;
    private TextView textView1,textView2,edittext3,BlanceCount;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mButton = findViewById(R.id.button);
        textView = findViewById(R.id.edittext);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        edittext3 = findViewById(R.id.edittext3);
        BlanceCount = findViewById(R.id.BlanceCount);
        AdView = findViewById(R.id.adView);
        AdView1 = findViewById(R.id.adView1);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView.loadAd(adRequest);
        AdView1.loadAd(adRequest);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = Integer.parseInt(textView1.getText().toString());
                if(c==0){
                    Toast.makeText(Main3Activity.this, "অ্যাপ বন্ধ করুন এবং আবার ফিরে আসুন", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mInterstitialAd.isLoaded()) {
                    textView.setVisibility(View.GONE);
                    mButton.setVisibility(View.VISIBLE);
                    edittext3.setVisibility(View.VISIBLE);
                    mInterstitialAd.show();
                }
                else {
                    textView.setVisibility(View.VISIBLE);
                    mButton.setVisibility(View.GONE);
                    edittext3.setVisibility(View.GONE);
                }
            }
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                textView.setVisibility(View.GONE);
                mButton.setVisibility(View.VISIBLE);
                edittext3.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                textView.setVisibility(View.VISIBLE);
                mButton.setVisibility(View.GONE);
                edittext3.setVisibility(View.GONE);
            }

            @Override
            public void onAdOpened() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        vibrator.vibrate(1000);
                        Toast.makeText(Main3Activity.this, "Cut This Ads", Toast.LENGTH_SHORT).show();
                    }
                },5000);
                Toast.makeText(Main3Activity.this, "Wait 5 Second", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdClicked() {
                Toast.makeText(Main3Activity.this, "Wait 10 Second", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdLeftApplication() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        vibrator.vibrate(1000);
                        Toast.makeText(Main3Activity.this, "Close Browse And Go Again App", Toast.LENGTH_SHORT).show();
                    }
                },5000);
                int c = Integer.parseInt(textView1.getText().toString());
                int d = Integer.parseInt(textView2.getText().toString());
                int e = Integer.parseInt(BlanceCount.getText().toString());
                if(c!=1){
                    int wrong = d + 1;
                    int blance = (e - 3);
                    textView2.setText(""+wrong);
                    BlanceCount.setText(""+blance);
                }
                if(c==1){
                    int blance = e + 5;
                    BlanceCount.setText(""+blance);
                }
            }
            @Override
            public void onAdClosed() {
                int c = Integer.parseInt(textView1.getText().toString());
                int e = Integer.parseInt(BlanceCount.getText().toString());
                if(c!=0) {
                    int sum = c - 1;
                    int blance = e + 2;
                    textView1.setText("" + sum);
                    BlanceCount.setText(""+blance);
                }
                textView.setVisibility(View.VISIBLE);
                mButton.setVisibility(View.GONE);
                edittext3.setVisibility(View.GONE);
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

    }
    public void payment(View view) {
        startActivity(new Intent(Main3Activity.this,PaymentActivity.class));
    }
}
