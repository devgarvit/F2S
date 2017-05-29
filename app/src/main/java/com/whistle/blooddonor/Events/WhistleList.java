package com.whistle.blooddonor.Events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by garvit on 22/5/17.
 */
public class WhistleList {


    @SerializedName("dis")
    @Expose
    private Double dis;

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("createdAt")
    @Expose
    private Long createdAt;
    @SerializedName("lastLogin")
    @Expose
    private String lastLogin;
    @SerializedName("Whistles")
    @Expose
    private Whistles whistles = null;
    @SerializedName("dislikes")
    @Expose
    private List<Object> dislikes = null;
    @SerializedName("likes")
    @Expose
    private List<Object> likes = null;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("reachability")
    @Expose
    private EventReachability reachability;
    @SerializedName("usertype")
    @Expose
    private String usertype;
    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("verified")
    @Expose
    private Boolean verified;
    @SerializedName("visible")
    @Expose
    private Boolean visible;
    @SerializedName("teaser")
    @Expose
    private Boolean teaser;
    @SerializedName("createdAtISO")
    @Expose
    private String createdAtISO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Whistles getWhistles() {
        return whistles;
    }

    public void setWhistles(Whistles whistles) {
        this.whistles = whistles;
    }

    public List<Object> getDislikes() {
        return dislikes;
    }

    public void setDislikes(List<Object> dislikes) {
        this.dislikes = dislikes;
    }

    public List<Object> getLikes() {
        return likes;
    }

    public void setLikes(List<Object> likes) {
        this.likes = likes;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public EventReachability getReachability() {
        return reachability;
    }

    public void setReachability(EventReachability reachability) {
        this.reachability = reachability;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getTeaser() {
        return teaser;
    }

    public void setTeaser(Boolean teaser) {
        this.teaser = teaser;
    }

    public String getCreatedAtISO() {
        return createdAtISO;
    }

    public void setCreatedAtISO(String createdAtISO) {
        this.createdAtISO = createdAtISO;
    }

    public Double getDis() {
        return dis;
    }

    public void setDis(Double dis) {
        this.dis = dis;
    }

}
