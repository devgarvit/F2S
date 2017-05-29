package com.whistle.blooddonor.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by garvit on 22/5/17.
 */
public class WhistleUnAuthEntity {
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("radius")
    @Expose
    private Integer radius;
    @SerializedName("location")
    @Expose
    private List<Double> location = null;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("keyword")
    @Expose
    private String keyword;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
