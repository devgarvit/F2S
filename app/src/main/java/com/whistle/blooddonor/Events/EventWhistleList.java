package com.whistle.blooddonor.Events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by garvit on 21/5/17.
 */
public class EventWhistleList {
    @SerializedName("matchingWhistles")
    @Expose
    private List<WhistleList> matchingWhistles = null;
    @SerializedName("criteria")
    @Expose
    private EventCriteria criteria;

    public List<WhistleList> getMatchingWhistles() {
        return matchingWhistles;
    }

    public void setMatchingWhistles(List<WhistleList> matchingWhistles) {
        this.matchingWhistles = matchingWhistles;
    }

    public EventCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(EventCriteria criteria) {
        this.criteria = criteria;
    }
}
