package com.example.hiddentreasure.db;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static com.example.hiddentreasure.other.Constants.*;

public class TreasureDatabase {
    private static final String TAG = "TreasureDatabase";
    private static TreasureDatabase instance;
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollection;
    private MutableLiveData<List<TreasureItem>> mItems = new MutableLiveData<>();
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;


    public static synchronized TreasureDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new TreasureDatabase(context);
        }
        return instance;
    }

    private TreasureDatabase(Context context) {
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReference();
    }

    public void uploadPhoto(String fileName, Bitmap bitmap) {
        StorageReference reference = mStorageReference.child(fileName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = reference.putBytes(data);
        uploadTask.addOnFailureListener(e -> {
            Log.d(TAG, "uploadPhoto: upload failed");
        }).addOnSuccessListener(taskSnapshot -> {
            Log.d(TAG, "Upload success!");
        });
    }

    public LiveData<List<TreasureItem>> getAllTreasures() {
        mCollection = mFirebaseFirestore.collection(TREASURE_COLLECTION);
        mCollection.get().addOnCompleteListener(task -> mItems.setValue(task.getResult().toObjects(TreasureItem.class)));
        return mItems;
    }
}
