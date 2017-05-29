package com.whistle.blooddonor.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by garvit on 20/5/17.
 */

public class User {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("location")
    @Expose
    private List<Double> location = null;
    @SerializedName("reachability")
    @Expose
    private Reachability reachability;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("subCategory")
    @Expose
    private String subCategory;
    @SerializedName("provider")
    @Expose
    private Boolean provider;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("whistleImages")
    @Expose
    private List<String> whistleImages = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public Reachability getReachability() {
        return reachability;
    }

    public void setReachability(Reachability reachability) {
        this.reachability = reachability;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getProvider() {
        return provider;
    }

    public void setProvider(Boolean provider) {
        this.provider = provider;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getWhistleImages() {
        return whistleImages;
    }

    public void setWhistleImages(List<String> whistleImages) {
        this.whistleImages = whistleImages;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

}