package com.example.crazy8;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity {

    EditText mname,memail,mmobile,mpassword;
    Button msignup;
    TextView mlogin;
    private long backPressedTime;
    private Toast backTost;
    private FirebaseAuth mAuth;
    private static final String TAG = "Main2Activity";
    private FirebaseDatabase userInfoDatabase;
    private DatabaseReference reference;
    private FirebaseUser currentUser;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mname=findViewById(R.id.NameId);
        memail=findViewById(R.id.EmailId);
        mmobile=findViewById(R.id.MobileId);
        mpassword=findViewById(R.id.PasswordId);
        msignup=findViewById(R.id.SignUpButtonId);
        mlogin=findViewById(R.id.LogInTextId);
        progressBar = findViewById(R.id.progress);

        mAuth = FirebaseAuth.getInstance();
        userInfoDatabase=FirebaseDatabase.getInstance();
        //reference=userInfoDatabase.getReference("Users");
        currentUser=mAuth.getCurrentUser();

        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = memail.getText().toString().trim();
                final String password = mpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    memail.setError("Enter Email Address");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    memail.setError("Enter Valid Email Address");
                    memail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mpassword.setError("Enter Password");
                    return;
                }
                if (password.length() < 6) {
                    mpassword.setError("Use 6 Digit Password");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            UserInformation userInformation = new UserInformation();
                            userInformation.setEmail(email);
                            userInformation.setPassword(password);
                            userInformation.setName(mname.getText().toString());
                            userInformation.setNumber(mmobile.getText().toString());
                            userInformation.setPoints(0);
                            userInformation.setRupees(0);

                            reference.child(currentUser.getUid()).setValue(userInformation).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(Main2Activity.this, "SuccessFully Register", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Main2Activity.this, "Sorry", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backTost.cancel();
            super.onBackPressed();
            finish();
        }else {
            backTost = Toast.makeText(getApplicationContext(),"Press back again to exit",Toast.LENGTH_SHORT);
            backTost.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
    public void LogInText(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}