package com.example.hiddentreasure.db;

import androidx.room.Entity;

@Entity(tableName = "treasure")
public class TreasureItem {
    private String id;
    private String name;
    private String description;
    private String imageUrl;

    public TreasureItem() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }
}
