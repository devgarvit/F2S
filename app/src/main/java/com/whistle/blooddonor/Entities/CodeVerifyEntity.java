package com.whistle.blooddonor.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by garvit on 20/5/17.
 */
public class CodeVerifyEntity {
    @SerializedName("verify")
    @Expose
    private Integer verify;

    public Integer getVerify() {
        return verify;
    }

    public void setVerify(Integer verify) {
        this.verify = verify;
    }
}
