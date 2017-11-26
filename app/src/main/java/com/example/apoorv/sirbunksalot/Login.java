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

public class Login extends Activity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    EditText mEmail_Field;
    EditText mPasswordField;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mEmail_Field =(EditText) findViewById(R.id.loginscreen_username);
        mPasswordField=(EditText) findViewById(R.id.loginsscreen_password);
        progressBar=(ProgressBar) findViewById(R.id.loginscreen_progressBar);
        findViewById(R.id.Signup_Button_MainScreen).setOnClickListener(this);
        findViewById(R.id.loginbutton_login_screen).setOnClickListener(this);

    }

    private void userLogin(){
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

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    finish();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.Signup_Button_MainScreen:
                startActivity(new Intent(this, SignUp.class));
                break;
            case R.id.loginbutton_login_screen:
                userLogin();
                break;
        }

    }
}
