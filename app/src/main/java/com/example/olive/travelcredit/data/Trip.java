package com.example.olive.travelcredit.data;

/**
 * Created by olive on 5/11/18.
 */





public class Trip {


    private String placeName;

    private String uid;

    public Trip() {
    }

    public Trip(String placeName, String uid) {
        this.uid = uid;
        this.placeName = placeName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
