package com.example.hiddentreasure.db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;

import com.google.firebase.firestore.GeoPoint;

@Entity(tableName = "treasure")
public class TreasureItem implements Parcelable {
    private String name;
    private String description;
    private String imageUrl;
    private GeoPoint location;
    private int piratesVisited;

    public TreasureItem() {
    }

    public TreasureItem(String name, String description, String imageUrl, GeoPoint location) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.location = location;
        this.piratesVisited = 1;
    }

    protected TreasureItem(Parcel in) {
        name = in.readString();
        description = in.readString();
        imageUrl = in.readString();
    }
    public int getPiratesVisited(){
        return this.piratesVisited;
    }
    public void setPiratesVisited(int val){
        this.piratesVisited = val;
    }


    public static final Creator<TreasureItem> CREATOR = new Creator<TreasureItem>() {
        @Override
        public TreasureItem createFromParcel(Parcel in) {
            return new TreasureItem(in);
        }

        @Override
        public TreasureItem[] newArray(int size) {
            return new TreasureItem[size];
        }
    };

    public GeoPoint getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(imageUrl);
    }
}
