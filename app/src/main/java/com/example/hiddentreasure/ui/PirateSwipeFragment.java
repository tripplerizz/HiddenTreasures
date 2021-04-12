package com.example.hiddentreasure.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.hiddentreasure.R;
import com.example.hiddentreasure.db.TreasureDatabase;
import com.example.hiddentreasure.db.TreasureItem;
import com.example.hiddentreasure.ui.HomeFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;
import java.util.List;
import java.util.Stack;

public class PirateSwipeFragment extends Fragment {
    private Stack<TreasureItem> items = new Stack<TreasureItem>();
    private TreasureDatabase db;
    private  CollectionReference collectionReference;
    private  ImageView imageView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // populating list with treasure items
        db = TreasureDatabase.getInstance(getContext());
        collectionReference = db.getCollection();
    }
    private void findTreasureItem(CollectionReference reference, View v) {
        reference.addSnapshotListener((value, error) -> {
            // Iterate through each item in the database and add the item info to map
            for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                TreasureItem item = documentSnapshot.toObject(TreasureItem.class);
                items.push(item);
                if (getActivity() == null)  {
                    continue;
                }
            }
            readyTreasureCardView(v);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pirate_treasure_tinder, container, false);
        findTreasureItem(collectionReference, v);
        // Skipping items which were unvisited by Pirate
        ImageButton skipButton = v.findViewById(R.id.skipButton);
        skipButton.setOnClickListener(view -> {
            items.pop();
            readyTreasureCardView(v);
        });
        ImageButton acceptButton = v.findViewById(R.id.acceptButton);
        return v;
    }
    public void readyTreasureCardView(View v){
        imageView = v.findViewById(R.id.ItemSwipePhoto);
        String ImageUrl = items.peek().getImageUrl();
        Glide
                .with(imageView.getContext())
                .load(ImageUrl)
                .centerCrop()
                .into(new ImageViewTarget<Drawable>(imageView) {
                    @Override
                    protected void setResource(@Nullable Drawable resource) {
                        imageView.setImageDrawable(resource);
                    }
                });

    }

}
