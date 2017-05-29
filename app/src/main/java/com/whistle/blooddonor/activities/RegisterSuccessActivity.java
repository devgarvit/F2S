package com.whistle.blooddonor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whistle.blooddonor.Persistent.TinyDB;
import com.whistle.blooddonor.R;

public class RegisterSuccessActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtWelcome;
    Button btnProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);

        btnProceed = (Button) findViewById(R.id.btn_proceed);
        txtWelcome = (TextView) findViewById(R.id.txt_welcome);
        txtWelcome.setText("Welcome " + new TinyDB(getApplicationContext()).getString("name"));
        btnProceed.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_proceed:
                new TinyDB(getApplicationContext()).putBoolean("isRegistered", true);
                startActivity(new Intent(RegisterSuccessActivity.this, MapActivity.class));
                finish();
                break;
        }
    }
}
