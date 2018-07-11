package com.prabhudeepsingh.userecosystem.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.prabhudeepsingh.userecosystem.R;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser !=null)
            handler.sendEmptyMessageDelayed(201, 1000);
        else
            handler.sendEmptyMessageDelayed(101, 1000);
    }

    Handler handler = new Handler(){
        Intent intent;

        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 101){
                intent = new Intent(SplashActivity.this, SignInActivity.class);
            }else {
                intent = new Intent(SplashActivity.this, HomeActivity.class);
            }
            startActivity(intent);
            finish();
        }
    };
}