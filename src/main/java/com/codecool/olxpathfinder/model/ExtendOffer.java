package com.codecool.olxpathfinder.model;

public class ExtendOffer extends Offer {

    private long distance;
    private long duration;


    public ExtendOffer(String url, String street, String description, String title,
        String publishTime, Long price, long distance, long duration) {
        super(url, street, description, title, publishTime, price);
        this.distance = distance;
        this.duration = duration;
    }

    public long getDistance() {
        return distance;
    }

    public long getDuration() {
        return duration;
    }
}
