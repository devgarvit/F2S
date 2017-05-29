package com.whistle.blooddonor.Events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by garvit on 21/5/17.
 */
public class EventCriteria {
    @SerializedName("radius")
    @Expose
    private Double radius;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("location")
    @Expose
    private List<Double> location = null;

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }
}
