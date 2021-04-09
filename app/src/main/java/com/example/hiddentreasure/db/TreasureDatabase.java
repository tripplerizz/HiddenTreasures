package com.example.hiddentreasure.db;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;

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
        mCollection = mFirebaseFirestore.collection(TREASURE_COLLECTION);
        mStorageReference = mFirebaseStorage.getReference();
    }

    public void uploadPhoto(String name, String description, Bitmap bitmap, GeoPoint location) {
        StorageReference reference = mStorageReference.child(name + ".jpeg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = reference.putBytes(data);
        uploadTask
                .addOnFailureListener(e -> Log.d(TAG, "Upload failed"))
                .addOnSuccessListener(taskSnapshot -> Log.d(TAG, "Upload success!"));

        uploadTask.continueWithTask(
                task -> reference.getDownloadUrl())
                .addOnCompleteListener(task -> {
                    String url = Objects.requireNonNull(task.getResult()).toString();
                    mCollection.document(name)
                            .set(new TreasureItem(name, description, url, location))
                            .addOnSuccessListener(aVoid -> Log.d(TAG, "onSuccess: Successfully written!"))
                            .addOnFailureListener(e -> Log.d(TAG, "onFailure: Error writing document"));
                });
    }

    public CollectionReference getCollection() {
        return mFirebaseFirestore.collection(TREASURE_COLLECTION);
    }
}
