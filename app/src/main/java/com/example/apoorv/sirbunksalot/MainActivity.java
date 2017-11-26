package com.example.apoorv.sirbunksalot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;

public class MainActivity extends Activity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.mainactivity_logoutbutton).setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.mainactivity_progressBar);


    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            Intent intent = new Intent(this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private void Logout(){
        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        progressBar.setVisibility(View.GONE);
        startActivity(intent);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.mainactivity_logoutbutton:
                Logout();
                break;
        }

    }

}
