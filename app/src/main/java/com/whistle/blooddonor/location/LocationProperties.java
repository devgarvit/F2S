package com.whistle.blooddonor.location;

import android.support.annotation.FloatRange;

/**
 * Created by garvit on 21/5/17.
 */
public class LocationProperties {
    public static final LocationProperties DEFAULT = new LocationProperties();

    public static final long DEFAULT_MIN_TIME = 15 * 60 * 1000;

    public static final float DEFAULT_MIN_METERS = 100;


    private long mRegularUpdateTime = 0;

    private float mRegularUpdateDistance = 0;



    public void setRegularUpdateTime(@FloatRange(from = 1) long regularUpdateTime) {
        if (regularUpdateTime > 0) {
            mRegularUpdateTime = regularUpdateTime;
        }
    }

    public long getRegularUpdateTime() {
        return mRegularUpdateTime <= 0 ? DEFAULT_MIN_TIME : mRegularUpdateTime;
    }

    public void setMetersBetweenUpdates(@FloatRange(from = 0) float mRegularUpdateDistance) {
        if (mRegularUpdateDistance > 0) {
            this.mRegularUpdateDistance = mRegularUpdateDistance;
        }
    }

    public float getMetersBetweenUpdates() {
        return mRegularUpdateDistance <= 0 ? DEFAULT_MIN_METERS : mRegularUpdateDistance;
    }
}
