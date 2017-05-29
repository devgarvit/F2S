package com.whistle.blooddonor.location;

import android.content.Context;
import android.location.Location;

import com.whistle.blooddonor.Events.EventLocation;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Saldi on 28/4/16.
 * for Railyatri
 * you may contact me at : sourabh.saldi@railyatri.in
 */
public class LocationRequest {
    private Context mContext;
    private static LocationRequest LocationInstance;
    private LocationProperties mLocationProperties;
    private FusedProvider ryFusedProvider;
    private boolean isListening = false;

    /**
     * Private constructor
     */
    private LocationRequest() {
    }

    /**
     * A static method to get instance.
     */
    public static LocationRequest getInstance() {
        if (LocationInstance == null) {
            LocationInstance = new LocationRequest();
        }
        return LocationInstance;
    }


    /**
     * This method must be used to stop location requests
     *
     * @param context
     */
    public void stopLocationRequest(Context context) {
        this.mContext = context;
        if (ryFusedProvider != null)
            ryFusedProvider.stopListen();
    }

    /**
     * This method must be used to start Fused locations
     *
     * @param context
     */
    public void startFusedLocation(final Context context) {
        if (ryFusedProvider != null)
            ryFusedProvider.stopListen();
        ryFusedProvider = new FusedProvider(context, mLocationProperties) {

            @Override
            public void onLocationFound(Location location) {
                EventBus.getDefault().post(new EventLocation(location));
            }
        };
        isListening = true;
    }

    public boolean isFusedListening() {
        return ryFusedProvider.isListening();
    }

    public boolean isListening() {
        return isListening;
    }


}
