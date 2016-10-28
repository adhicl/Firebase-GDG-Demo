package com.aldoapps.yetanothereventapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aldo on 10/29/16.
 */

public class RestoMenu implements Parcelable{

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

    protected RestoMenu(Parcel in) {
        key = in.readString();
        menu = in.readString();
        description = in.readString();
        rating = in.readFloat();
        imageUrl = in.readString();
    }

    public static final Creator<RestoMenu> CREATOR = new Creator<RestoMenu>() {
        @Override
        public RestoMenu createFromParcel(Parcel in) {
            return new RestoMenu(in);
        }

        @Override
        public RestoMenu[] newArray(int size) {
            return new RestoMenu[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(key);
        parcel.writeString(menu);
        parcel.writeString(description);
        parcel.writeFloat(rating);
        parcel.writeString(imageUrl);
    }
}
