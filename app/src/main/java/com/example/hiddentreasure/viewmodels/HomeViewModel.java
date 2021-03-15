package com.example.hiddentreasure.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hiddentreasure.models.TreasureItem;
import com.example.hiddentreasure.repositories.TreasureRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<TreasureItem>> mTreasureItems;
    private TreasureRepository mTreasureRepository;

    public LiveData<List<TreasureItem>> getTreasureItems() {
        return mTreasureItems;
    }

    public void init() {
        if (mTreasureItems != null) {
            return;
        }
        mTreasureRepository = TreasureRepository.getInstance();
        mTreasureItems = mTreasureRepository.getTreasureItems();
    }
}