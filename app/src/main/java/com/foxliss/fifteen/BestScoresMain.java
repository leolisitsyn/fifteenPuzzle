package com.foxliss.fifteen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


public class BestScoresMain extends AppCompatActivity {

    AppState appState;

    Spinner difficultySpinner;
    Spinner sizeSpinner;

    int sizeId;
    int difficultyId;

    ListView listView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_scores_main);

        appState = new AppState(this);


        Button back = findViewById(R.id.back_from_best_main);


        back.setOnClickListener(v -> {
            Intent intent = new Intent(BestScoresMain.this, MainActivity.class);
            startActivity(intent);
        });

        sizeSpinner =  findViewById(R.id.sizeIdSpinner);
        String[] sizeString = {"2X2", "3X3", "4X4", "5X5", "6X6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, sizeString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(adapter);

        difficultySpinner = findViewById(R.id.difficultySpinner);
        String[] level = {"EASY", "HARD"};
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                level);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficultyAdapter);


        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextSize(25);
                ((TextView) parent.getChildAt(0)).setGravity(Gravity.START);

                sizeId = sizeSpinner.getSelectedItemPosition();

                final String[] bestString = appState.selectedScores(sizeId, difficultyId);
                ArrayAdapter<String> adapter2 = new CustomArrayAdapter<>(BestScoresMain.this,
                        android.R.layout.simple_list_item_1, bestString);

                listView2.setAdapter(adapter2);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        difficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextSize(25);
                        ((TextView) parent.getChildAt(0)).setGravity(Gravity.START);

                        difficultyId = difficultySpinner.getSelectedItemPosition();

                        final String[] bestString = appState.selectedScores(sizeId, difficultyId);
                        ArrayAdapter<String> adapter2 = new CustomArrayAdapter<>(BestScoresMain.this,
                                android.R.layout.simple_list_item_1, bestString);
            listView2.setAdapter(adapter2);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ListView listView1 = findViewById(R.id.list1);
        final String[] oneToFive = {"1.", "2.", "3.", "4.", "5."};
        ArrayAdapter<String> adapter1 = new CustomArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, oneToFive);

        listView1.setAdapter(adapter1);
        listView2 = findViewById(R.id.list2);

        Button clearScores = findViewById(R.id.reset);

        clearScores.setOnClickListener(v -> {
                    appState.clearScores(sizeId, difficultyId);
                    listView2.setAdapter(new CustomArrayAdapter<>(this,
                            android.R.layout.simple_list_item_1, appState.selectedScores()));
                });
    }
}