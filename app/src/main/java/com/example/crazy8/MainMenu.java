package com.example.crazy8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainMenu extends AppCompatActivity {

    private TextView mUsername,mNumber,userId;
    private Button btnLogout;
    private FirebaseAuth mAuth;
    private FirebaseDatabase userDatabase;
    private FirebaseUser user;
    private DatabaseReference reference;
    private FirebaseAuth.AuthStateListener listener;
    private String firebaseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnLogout = findViewById(R.id.logout);
        mUsername = findViewById(R.id.username);
        mNumber = findViewById(R.id.number);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userDatabase = FirebaseDatabase.getInstance();
        firebaseId = user.getUid();
        reference = userDatabase.getReference("Users");

    }

    public void main3activity(View view) {
        startActivity(new Intent(MainMenu.this,Main3Activity.class));
    }

    public void maacvity(View view) {
        startActivity(new Intent(MainMenu.this,Main4Activity.class));
    }
}
