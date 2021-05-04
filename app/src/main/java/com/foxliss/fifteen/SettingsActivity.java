package com.foxliss.fifteen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener {

    final String SIZE = "Field size";
    final String DIFFICULT = "Difficult";

    Spinner difficulty;
    Spinner sizeSpinner;
    CheckBox chkbox;

    int fieldSizeId;
    int difficultyLevel;

    AppState appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        appState = new AppState(this);

        final int prevSize = appState.sizeId;
        final int prevDifficulty = appState.difficulty;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Button back = findViewById(R.id.backToMainMenu);

        chkbox = findViewById(R.id.volOnOff);

        chkbox.setOnCheckedChangeListener(this);

        sizeSpinner =  findViewById(R.id.spinner);
        String[] data = {"2X2", "3X3", "4X4", "5X5", "6X6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(adapter);


        difficulty = findViewById(R.id.difficultySpinner);
        String[] level = {"EASY", "HARD"};
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, level);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficulty.setAdapter(difficultyAdapter);

        loadData();

        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextSize(30);
                ((TextView) parent.getChildAt(0)).setGravity(Gravity.START);

                fieldSizeId = sizeSpinner.getSelectedItemPosition();

                if (fieldSizeId != prevSize) appState.setFinished(true);
                appState.saveInt(SIZE, fieldSizeId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        difficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextSize(30);
                ((TextView) parent.getChildAt(0)).setGravity(Gravity.START);

                difficultyLevel = difficulty.getSelectedItemPosition();

                if (difficultyLevel != prevDifficulty) appState.setFinished(true);
                appState.saveInt(DIFFICULT, difficultyLevel);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        back.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        appState.setAudio(isChecked);
    }


    void loadData() {

        difficultyLevel = appState.difficulty;
        difficulty.setSelection(difficultyLevel);

        int currentPosition = appState.sizeId;
        sizeSpinner.setSelection(currentPosition);

        boolean audio = appState.audio;
        chkbox.setChecked(audio);
    }
}
