package com.whistle.blooddonor.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whistle.blooddonor.Events.EventLocation;
import com.whistle.blooddonor.R;
import com.whistle.blooddonor.location.LocationRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by garvit on 17/5/17.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtLookingDonor;
    Button btnRegister;
    Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtLookingDonor = (TextView) findViewById(R.id.txt_looking_donor);
        btnRegister = (Button) findViewById(R.id.btn_register);
        txtLookingDonor.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        checkAndRequestLocation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register: {

                Intent intent = new Intent(RegisterActivity.this, SelectGroupActivity.class);
                startActivity(intent);
                finish();
            }
            break;

            case R.id.txt_looking_donor: {
                Intent intent = new Intent(RegisterActivity.this, MapActivity.class);
                intent.putExtra("lat",mLocation.getLatitude());
                intent.putExtra("lng",mLocation.getLongitude());
                startActivity(intent);
                finish();
            }
            break;
        }
    }

    private void checkAndRequestLocation() {
        int finePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (finePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);

        }

    }

    private void checkAndRequestSMS()   {
        int smsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        int phonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (smsPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (phonePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 200);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //TODO location granted
                } else {
                }

                checkAndRequestSMS();
            }
            break;
            case 200:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkAndRequestLocation();
                } else {
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocationRequest.getInstance().startFusedLocation(getApplicationContext());
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocationRequest.getInstance().stopLocationRequest(getApplicationContext());
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(EventLocation event) {
        mLocation = event.getLocation();
    }
}
