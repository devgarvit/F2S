package com.whistle.blooddonor.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by garvit on 21/5/17.
 */

public abstract class FusedProvider implements  GoogleApiClient.ConnectionCallbacks, com.google.android.gms.location.LocationListener {

    private static final String TAG = "RYLocationProvider";
    LocationRequest mLocationRequest;
    private LocationManager mLocationManager;
    private boolean mIsListening = false;
    private Context mContext;
    private LocationProperties mLocationProperties;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIsConnected;

    /**
     * Constructor initializes  with default properties
     *
     * @param context
     */
    public FusedProvider(@NonNull Context context) {
        this(context, LocationProperties.DEFAULT);
    }

    /**
     * Constructor initializes  based on properties given by Client
     *
     * @param context
     * @param LocationProperties
     */
    public FusedProvider(@NonNull Context context, @NonNull LocationProperties LocationProperties) {
        this.mContext = context;
        this.mLocationProperties = LocationProperties;
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        if (!mGoogleApiClient.isConnected() || !mGoogleApiClient.isConnecting())
            mGoogleApiClient.connect();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000 * 10);
        mLocationRequest.setFastestInterval(1000 * 10);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    /**
     * Start Listen for location updates
     */
    @RequiresPermission(anyOf = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION})
    public final void startListen() throws InterruptedException {
        if (mLocationRequest == null) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000 * 10);
            mLocationRequest.setFastestInterval(1000 * 10);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        }
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
            if (!mGoogleApiClient.isConnected() || !mGoogleApiClient.isConnecting())
                mGoogleApiClient.connect();
        }
        if (!this.mIsListening) {
            if (mGoogleApiClient.isConnected()) {
                Log.i(TAG, "listening for location updates");
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                try {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mIsListening = true;
            } else {
                Log.i(TAG, "api client not connected");
                mIsListening = false;
                startListen();
            }

        } else {
            Log.i(TAG, "Relax, Already listening for location updates");
        }
    }

    /**
     * This method can be used for stopping Location updates
     */
    public final void stopListen() {
        if (this.mIsListening) {
            Log.i(TAG, "Stopped listening for location updates");
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //Check self permission required for newer Android versions
                return;
            }
            try {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.mIsListening = false;
        } else {
            Log.i(TAG, "we were not listening for location updates anyway");
        }
    }

    /**
     * Check if we are currently listening location updates (can be useful in client library)
     *
     * @return
     */
    public final boolean isListening() {
        return mIsListening;
    }

    /**
     * Called when we find location
     *
     * @param location
     */
    @Override
    public final void onLocationChanged(@NonNull Location location) {
        onLocationFound(location);
    }

    /**
     * Called when the RYLocationProvider finds a location
     *
     * @param location the found location
     */
    public abstract void onLocationFound(@NonNull Location location);

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "api client connected");
        try {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startListen();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.i(TAG, "Failed to connect to location services ");
            mIsListening = false;

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

}