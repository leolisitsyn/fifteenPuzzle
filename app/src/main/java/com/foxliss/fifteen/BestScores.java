package com.foxliss.fifteen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;


public class BestScores extends AppCompatActivity {

    AppState appState;
    ListView listView2;
    ArrayAdapter<String> adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_best_scores);


        appState = new AppState(this);
        int prevBestScore = appState.loadBestScore();
        int nSteps = appState.steps;


        TextView result = findViewById(R.id.result);

        if (nSteps < prevBestScore) {
            result.setText(String.format(Locale.ENGLISH, "You won in %d steps. It's a new best score!",
                    nSteps));
        }
        else {
            result.setText(String.format(Locale.ENGLISH, "You won in %d steps! Well done",
                    nSteps));
        }

        appState.saveBest(nSteps);

        ListView listView1 = findViewById(R.id.list1);
        final String[] oneToFive = {"1.", "2.", "3.", "4.", "5."};
        ArrayAdapter<String> adapter1 = new CustomArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, oneToFive);
        listView1.setAdapter(adapter1);

        listView2 = findViewById(R.id.list2);
        final String[] bestString = appState.selectedScores();

        adapter2 = new CustomArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, bestString);

        listView2.setAdapter(adapter2);


        Button back = findViewById(R.id.button1);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(BestScores.this, MainActivity.class);
            startActivity(intent);
        });

        Button clearScores = findViewById(R.id.clear_results);
        clearScores.setOnClickListener(v -> {
            appState.clearScores(appState.sizeId, appState.difficulty);
            listView2.setAdapter(new CustomArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, appState.selectedScores()));
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(BestScores.this, MainActivity.class);
        startActivity(intent);
    }
}