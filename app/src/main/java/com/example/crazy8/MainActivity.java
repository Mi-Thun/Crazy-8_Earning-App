package com.example.crazy8;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backTost;
    private EditText memail1,mpassword1;
    private Button msignin;
    private TextView msignup;
    private FirebaseAuth mAuth;
    private CheckBox rememberMe;
    private SharedPreferences loginPref;
    private SharedPreferences.Editor prefs;
    private boolean check;
    private String email;
    private String password;
    private ProgressBar progressBar;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memail1 = findViewById(R.id.emailId);
        mpassword1 = findViewById(R.id.passwordId);
        msignin = findViewById(R.id.signInId);
        msignup = findViewById(R.id.SignupTextId);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress);
        currentUser=mAuth.getCurrentUser();

        rememberMe = findViewById(R.id.checkbox);
        loginPref = getSharedPreferences("loginPrefs",MODE_PRIVATE);
        prefs = loginPref.edit();
        check= loginPref.getBoolean("savelogin",false);

        if(check==true){
            memail1.setText(loginPref.getString("username",""));
            mpassword1.setText(loginPref.getString("password",""));
            rememberMe.setChecked(true);
        }

        if(currentUser !=null){
            startActivity(new Intent(MainActivity.this,MainMenu.class));
        }

        msignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = memail1.getText().toString().trim();
                String password = mpassword1.getText().toString().trim();

                progressBar.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(email)) {
                    memail1.setError("Enter Email Address");
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    memail1.setError("Enter Valid Email Address");
                    memail1.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mpassword1.setError("Enter Password");
                    return;
                }
                if (password.length() < 6) {
                    mpassword1.setError("Use 6 Digit Password");
                    return;
                }
                setRememberMeMethod();
                loginMethod();
            }
        });
    }

    private void loginMethod() {
        mAuth.signInWithEmailAndPassword(memail1.getText().toString(),mpassword1.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(MainActivity.this,MainMenu.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Try again"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRememberMeMethod() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(memail1.getWindowToken(),0);
        email=memail1.getText().toString();
        password=mpassword1.getText().toString();
        if(rememberMe.isChecked()){
            prefs.putBoolean("savelogin",true);
            prefs.putString("username",email);
            prefs.putString("password",password);
            prefs.commit();
        }
        else {
            prefs.clear();
            prefs.commit();

        }
    }

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backTost.cancel();
            super.onBackPressed();
            return;
        }else {
            backTost = Toast.makeText(getApplicationContext(),"Press back again to exit",Toast.LENGTH_SHORT);
            backTost.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    public void SignUp(View view) {
        startActivity(new Intent(getApplicationContext(), Main2Activity.class));
        finish();
    }
}
