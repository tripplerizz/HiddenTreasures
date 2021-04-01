package com.example.hiddentreasure.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.hiddentreasure.models.TreasureItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreasureRepository {
    private static TreasureRepository mInstance;
    private ArrayList<TreasureItem> mTreasureItems = new ArrayList<>();


    /**
     * Singleton pattern
     */
    public static TreasureRepository getInstance() {
        if (mInstance == null) {
            mInstance = new TreasureRepository();
        }
        return mInstance;
    }


    public MutableLiveData<List<TreasureItem>> getTreasureItems() {
        setTreasureItems();
        return new MutableLiveData<List<TreasureItem>>(mTreasureItems);
    }

    public void setTreasureItems() {
        mTreasureItems = new ArrayList<>(Arrays.asList(
                new TreasureItem("Pull-up bar", "Good for pulling"),
                new TreasureItem("Nintendo Switch", "Play BotW"),
                new TreasureItem("Incense", "For the praying"),
                new TreasureItem("Cat", "Pls take care of it"),
                new TreasureItem("Candle", "For the romance"),
                new TreasureItem("Arm", "Could be useful"),
                new TreasureItem("Leg", "Go break one")
        ));
    }
}
