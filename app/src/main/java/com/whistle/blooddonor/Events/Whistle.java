package com.whistle.blooddonor.Events;

/**
 * Created by garvit on 21/5/17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Whistle {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("provider")
    @Expose
    private Boolean provider;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("createdAt")
    @Expose
    private Long createdAt;
    @SerializedName("lastUpdatedAt")
    @Expose
    private String lastUpdatedAt;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("public")
    @Expose
    private Boolean _public;
    @SerializedName("createdAtISO")
    @Expose
    private String createdAtISO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(String lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getPublic() {
        return _public;
    }

    public void setPublic(Boolean _public) {
        this._public = _public;
    }

    public String getCreatedAtISO() {
        return createdAtISO;
    }

    public void setCreatedAtISO(String createdAtISO) {
        this.createdAtISO = createdAtISO;
    }

}