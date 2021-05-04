package com.foxliss.fifteen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RulesActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rules);

        Button back = findViewById(R.id.backToMainMenu);
        back.setOnClickListener(this);

    }
    @Override
    public void onClick (View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

