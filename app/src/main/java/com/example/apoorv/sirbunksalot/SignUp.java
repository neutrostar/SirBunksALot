package com.example.apoorv.sirbunksalot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUp extends Activity implements View.OnClickListener {

    EditText mEmail_Field, mPasswordField;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViewById(R.id.next_button_signupscreen).setOnClickListener(this);

        mEmail_Field = (EditText) findViewById(R.id.signup_email);
        mPasswordField = (EditText) findViewById(R.id.signup_password);
        progressBar = (ProgressBar) findViewById(R.id.signup_progressBar);
        mAuth = FirebaseAuth.getInstance();



    }

    private void registerUser(){
        String email = mEmail_Field.getText().toString().trim();
        String password = mPasswordField.getText().toString().trim();

        if(email.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter a valid email",Toast.LENGTH_SHORT).show();
            mEmail_Field.requestFocus();
            return ;
        }
        if(password.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter a Valid Password", Toast.LENGTH_SHORT).show();
            mPasswordField.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(getApplicationContext(),"Please enter a valid email address",Toast.LENGTH_SHORT).show();
            mEmail_Field.requestFocus();
            return ;
        }
        if(password.length()<6){
            Toast.makeText(getApplicationContext(),"Please enter a password more than 6 characters", Toast.LENGTH_SHORT).show();
            mPasswordField.requestFocus();
            return ;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Sign Up Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    //Toast.makeText(getApplicationContext(),"Sign Up was Unsuccessful", Toast.LENGTH_SHORT).show();
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"The Email is already registered", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.next_button_signupscreen:
                registerUser();
                break;
        }
    }
}
