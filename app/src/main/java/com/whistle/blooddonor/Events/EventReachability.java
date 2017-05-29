package com.whistle.blooddonor.Events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by garvit on 21/5/17.
 */
public class EventReachability {
    @SerializedName("call")
    @Expose
    private Boolean call;
    @SerializedName("SMS")
    @Expose
    private Boolean sMS;

    public Boolean getCall() {
        return call;
    }

    public void setCall(Boolean call) {
        this.call = call;
    }

    public Boolean getSMS() {
        return sMS;
    }

    public void setSMS(Boolean sMS) {
        this.sMS = sMS;
    }

}
