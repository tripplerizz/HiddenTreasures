package com.example.hiddentreasure.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.hiddentreasure.db.TreasureDatabase;
import com.example.hiddentreasure.db.TreasureItem;

import java.util.List;

public class TreasureRepository {
    private static TreasureRepository mInstance;
    private TreasureDatabase mDatabase;
    private LiveData<List<TreasureItem>> mTreasureItems;

    public TreasureRepository(Application application) {
        mDatabase = TreasureDatabase.getInstance(application);
        mTreasureItems = getTreasureItems();
    }

    public LiveData<List<TreasureItem>> getTreasureItems() {
        if (mTreasureItems == null) {
            mTreasureItems = mDatabase.getAllTreasures();
        }
        return mTreasureItems;
    }
}
