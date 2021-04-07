package com.example.hiddentreasure.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.hiddentreasure.db.TreasureDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

public class HomeViewModel extends AndroidViewModel {
    private final TreasureDatabase mDatabase;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mDatabase = TreasureDatabase.getInstance(application);
    }

    public Query getQuery() {
        return mDatabase.getCollection();
    }
}