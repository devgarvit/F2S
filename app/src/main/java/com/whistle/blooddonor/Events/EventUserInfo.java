package com.whistle.blooddonor.Events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by garvit on 21/5/17.
 */
public class EventUserInfo {

    @SerializedName("newUser")
    @Expose
    private NewUser newUser;

    public NewUser getNewUser() {
        return newUser;
    }

    public void setNewUser(NewUser newUser) {
        this.newUser = newUser;
    }
}
