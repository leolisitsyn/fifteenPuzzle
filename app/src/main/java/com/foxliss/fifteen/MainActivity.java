package com.foxliss.fifteen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    AppState appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        appState = new AppState(this);
        boolean isFinished = appState.isFinished;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, initializationStatus -> {
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Button start = findViewById(R.id.start);
        Button settings = findViewById(R.id.settings);
        Button rules = findViewById(R.id.rules);
        TextView resume = findViewById(R.id.resume);
        Button best = findViewById(R.id.best_res);
        if (!isFinished) {
            resume.setBackgroundResource(R.color.deep_blue);
            resume.setOnClickListener(this);
        } else {
            resume.setBackgroundResource(R.color.light_blue);
            resume.setTextColor(getResources().getColor(R.color.grey));

            resume.setOnClickListener(null);
        }

        start.setOnClickListener(this);
        settings.setOnClickListener(this);
        rules.setOnClickListener(this);
        best.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Class nextActivity;
        switch (v.getId()) {
            case R.id.start:
                appState.setFinished(true);
                nextActivity = GameActivity.class;
                break;
            case R.id.settings:
                nextActivity = SettingsActivity.class;
                break;
            case R.id.rules:
                nextActivity = RulesActivity.class;
                break;
            case R.id.resume:
                appState.setFinished(false);
                nextActivity = GameActivity.class;
                break;
            case R.id.best_res:
                nextActivity = BestScoresMain.class;
                break;
            default:
                nextActivity = GameActivity.class;
        }
        Intent intent = new Intent(this, nextActivity);
        startActivity(intent);
    }
}