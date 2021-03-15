package com.example.hiddentreasure.models;

import java.util.UUID;

public class TreasureItem {
    private UUID mId;
    private String mName;
    private String mDescription;

    public TreasureItem(String name, String description) {
        mName = name;
        mDescription = description;
        mId = UUID.randomUUID();
    }


    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }
}
