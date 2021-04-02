package com.example.hiddentreasure.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hiddentreasure.models.TreasureItem;
import com.example.hiddentreasure.repositories.TreasureRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private LiveData<List<TreasureItem>> mTreasureItems;
    private final TreasureRepository mTreasureRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mTreasureRepository = new TreasureRepository(application);
    }

    public LiveData<List<TreasureItem>> getTreasureItems() {
        return mTreasureItems;
    }

    public void init() {
        if (mTreasureItems != null) {
            return;
        }
        mTreasureItems = mTreasureRepository.getTreasureItems();
    }
}