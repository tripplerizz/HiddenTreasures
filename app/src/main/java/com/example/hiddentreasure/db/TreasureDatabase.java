package com.example.hiddentreasure.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static com.example.hiddentreasure.other.Constants.*;

public class TreasureDatabase {
    private static final String TAG = "TreasureDatabase";
    private static TreasureDatabase instance;
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollection;
    private MutableLiveData<List<TreasureItem>> mItems = new MutableLiveData<>();


    public static synchronized TreasureDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new TreasureDatabase(context);
        }
        return instance;
    }

    private TreasureDatabase(Context context) {
        mFirebaseFirestore = FirebaseFirestore.getInstance();
    }

    public LiveData<List<TreasureItem>> getAllTreasures() {
        mCollection = mFirebaseFirestore.collection(TREASURE_COLLECTION);
        mCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
               mItems.setValue(task.getResult().toObjects(TreasureItem.class));
            }
        });
        return mItems;
    }
}
