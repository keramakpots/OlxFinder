package com.codecool.olxpathfinder.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Offer {

    @JsonProperty("url")
    private String url;
    @JsonProperty("street")
    private String street;
    @JsonProperty("description")
    private String description;
    @JsonProperty("title")
    private String title;
    @JsonProperty("publishTime")
    private String publishTime;
    @JsonProperty("price")
    private Long price;

    public Offer() {
    }

    public Offer(String url, String street, String description, String title, String publishTime,
        Long price) {
        this.url = url;
        this.street = street;
        this.description = description;
        this.title = title;
        this.publishTime = publishTime;
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
