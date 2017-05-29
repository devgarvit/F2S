package com.whistle.blooddonor.Entities;

/**
 * Created by garvit on 20/5/17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reachability {

    @SerializedName("call")
    @Expose
    private Boolean call;
    @SerializedName("SMS")
    @Expose
    private Boolean sMS;
    @SerializedName("email")
    @Expose
    private Boolean email;

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

    public Boolean getEmail() {
        return email;
    }

    public void setEmail(Boolean email) {
        this.email = email;
    }

}
