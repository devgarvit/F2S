package com.whistle.blooddonor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.whistle.blooddonor.Persistent.TinyDB;
import com.whistle.blooddonor.R;


/**
 * Created by garvit on 17/5/17.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (new TinyDB(getApplicationContext()).getBoolean("isRegistered"))
                    startActivity(new Intent(SplashActivity.this, MapActivity.class));
                else
                    startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
                finish();
            }
        }, 3000);
    }
}
