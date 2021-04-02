package com.example.hiddentreasure.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "treasure")
public class TreasureItem {
    @PrimaryKey(autoGenerate = true)
    private int mId;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    private byte[] mImage;

    public TreasureItem(String name, String description, byte[] image) {
        mName = name;
        mDescription = description;
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getId() {
        return mId;
    }

    public byte[] getImage() {
        return mImage;
    }

    public void setId(int id) {
        mId = id;
    }
}
