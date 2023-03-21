package com.example.crazy8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Main1Activity extends AppCompatActivity {
    MainMenu mainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void MainMenu(View view) {
        startActivity(new Intent(getApplicationContext(), MainMenu.class));
        finish();
    }
}
