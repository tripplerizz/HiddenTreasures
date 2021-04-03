package com.example.hiddentreasure.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.hiddentreasure.db.TreasureItem;
import com.example.hiddentreasure.repositories.TreasureRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private LiveData<List<TreasureItem>> mTreasureItems = new LiveData<List<TreasureItem>>() {
    };
    private final TreasureRepository mTreasureRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mTreasureRepository = new TreasureRepository(application);
    }

    public LiveData<List<TreasureItem>> getTreasureItems() {
        return mTreasureItems;
    }

    public void init() {
        mTreasureItems = mTreasureRepository.getTreasureItems();
    }
}