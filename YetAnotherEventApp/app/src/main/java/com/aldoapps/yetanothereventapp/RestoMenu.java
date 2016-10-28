package com.aldoapps.yetanothereventapp;

/**
 * Created by aldo on 10/29/16.
 */

public class RestoMenu {

    private String key;

    private String menu;

    private String description;

    private float rating;

    private String imageUrl;

    public RestoMenu(String key, String menu, String description, float rating, String imageUrl) {
        this.key = key;
        this.menu = menu;
        this.description = description;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    public RestoMenu() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
