package com.whistle.blooddonor.Events;

import android.location.Location;

/**
 * Created by garvit on 21/5/17.
 */
public class EventLocation {

    private Location mLocation;

    public EventLocation(Location location) {
        this.mLocation = location;
    }

    public Location getLocation() {
        return mLocation;
    }


}
