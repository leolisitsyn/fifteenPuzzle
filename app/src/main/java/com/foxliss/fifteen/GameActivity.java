package com.foxliss.fifteen;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;


public class GameActivity extends AppCompatActivity {

    int emptyBlocks;
    int sizeId;
    int iterations;
    boolean isFinished;
    String[] fieldNumbers;
    boolean audio;

    LinearLayout linearLayout;
    GridView grid;

    AppState appState;


    @Override
    public void onCreate(Bundle savedInstanceState) {


        appState = new AppState(this);

        sizeId = appState.sizeId;
        int diff = appState.difficulty;

        if (diff == 0) {
            iterations = 50;
            emptyBlocks = 2;
        } else {
            iterations = 200;
            emptyBlocks = 1;
        }

        audio = appState.audio;

        isFinished = appState.isFinished;
        if (isFinished) appState.zeroSteps();
        fieldNumbers = appState.fieldNumbers;

        super.onCreate(savedInstanceState);

        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setBackgroundResource(R.drawable.wood_game);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        Field field = new Field(sizeId, iterations, emptyBlocks);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;

        TextView nSteps = new TextView(this);
        nSteps.setTextColor(getResources().getColor(R.color.black));
        nSteps.setText(String.format(Locale.ENGLISH, "%d steps made", appState.steps));
        nSteps.setTextSize(45);
        nSteps.setPadding(0,50,0, 30);
        nSteps.setGravity(Gravity.CENTER_HORIZONTAL);
        nSteps.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        if (isFinished) {
            field.createField();
            appState.saveField(field.getField());
        } else {
            field.setField(fieldNumbers);
        }
        GridField gridField = new GridField(this, field, screenHeight, screenWidth);
        grid = gridField.getGrid();
        grid.setGravity(Gravity.CENTER);
        grid.setPadding(0,0,0,50);

        isFinished = false;
        appState.setFinished(false);

        TextView back = new TextView(this);
        back.setText(R.string.backButton);

        back.setTextColor(getResources().getColor(R.color.transparent_black));
        back.setBackgroundResource(R.color.transparent_white);
        back.setTextSize(40);
        back.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        back.setGravity(Gravity.CENTER_HORIZONTAL);

        linearLayout.addView(nSteps);
        linearLayout.addView(grid);
        linearLayout.addView(back);
        setContentView(linearLayout);

        back.setOnClickListener(v -> {
            Intent intent = new Intent(GameActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        startActivity(intent);
    }
}