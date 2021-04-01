package com.example.hiddentreasure.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.Update;

import com.example.hiddentreasure.db.TreasureDAO;
import com.example.hiddentreasure.db.TreasureDatabase;
import com.example.hiddentreasure.models.TreasureItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreasureRepository {
    private static TreasureRepository mInstance;
    private final LiveData<List<TreasureItem>> mTreasureItems;
    private final TreasureDAO mTreasureDAO;

    public TreasureRepository(Application application) {
        TreasureDatabase database = TreasureDatabase.getInstance(application);
        mTreasureDAO = database.mTreasureDAO();
        mTreasureItems = mTreasureDAO.getAllTreasures();
    }

    void insertTreasure(final TreasureItem item) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mTreasureDAO.insertTreasure(item);
            }
        }).start();
    }

    void updateTreasure(final TreasureItem item) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mTreasureDAO.updateTreasure(item);
            }
        }).start();
    }

    void deleteTreasure(final TreasureItem item) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mTreasureDAO.deleteTreasure(item);
            }
        }).start();
    }

    public LiveData<List<TreasureItem>> getTreasureItems() {
        return mTreasureItems;
    }

    public void setTreasureItems() {
        /*
        mTreasureItems = new ArrayList<>(Arrays.asList(
                new TreasureItem("Pull-up bar", "Good for pulling"),
                new TreasureItem("Nintendo Switch", "Play BotW"),
                new TreasureItem("Incense", "For the praying"),
                new TreasureItem("Cat", "Pls take care of it"),
                new TreasureItem("Candle", "For the romance"),
                new TreasureItem("Arm", "Could be useful"),
                new TreasureItem("Leg", "Go break one")
        ));
        */
    }
}
