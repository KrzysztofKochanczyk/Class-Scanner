package com.example.barcodescanner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private EditText emailET, passwordET;
    private Button loginBtn;
    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailET = (EditText)findViewById(R.id.userNameT1);
        passwordET = (EditText)findViewById(R.id.passwordT1);
        loginBtn = (Button)findViewById(R.id.b1);
        fAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for easy access to get inside app
                startActivity(new Intent(LoginActivity.this, TrimesterActivity.class));

//                //setting up login
//                String email = emailET.getText().toString().trim();
//                String password = passwordET.getText().toString().trim();
//
//                //checking to see if email is empty
//                if (TextUtils.isEmpty(email))
//                {
//                    emailET.setError("Email is Required.");
//                    return;
//                }
//                //checking to see if password is empty
//                if (TextUtils.isEmpty(password))
//                {
//                    passwordET.setError("Password is Required.");
//                    return;
//                }
//                //checking password length
//                if (password.length() < 6)
//                {
//                    passwordET.setError("Password Must be >= 6 Characters");
//                    return;
//                }
//
//                //authenticate the user
//                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful())
//                        {
//                            startActivity(new Intent(getApplicationContext(),TrimesterActivity.class));
//                        }
//                        else
//                        {
//                            Toast.makeText(LoginActivity.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
            }
        });

    }



}
