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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;

import java.util.List;
import java.util.Stack;

public class PirateSwipeFragment extends Fragment {
    private Stack<TreasureItem> items = new Stack<TreasureItem>();
    private TreasureDatabase db;
    private  CollectionReference collectionReference;
    private  ImageView imageView;
    private boolean acceptQuery;
    private boolean acceptTreasure = true;
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
                if (acceptTreasure){
                    items.push(item);
                }
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
        // accepting new content from data base
        acceptTreasure = true;
        findTreasureItem(collectionReference, v);
        // Skipping items which were unvisited by Pirate
        ImageButton skipButton = v.findViewById(R.id.skipButton);
        skipButton.setOnClickListener(view -> {
            items.pop();
            readyTreasureCardView(v);
        });
        //removing floating action button
        FloatingActionButton fab = getActivity().findViewById(R.id.add_treasure_fab);
        if (fab != null){
            fab.setVisibility(View.INVISIBLE);
        }

        ImageButton acceptButton = v.findViewById(R.id.acceptButton);
        acceptButton.setOnClickListener( view -> {
            String itemName = items.peek().getName();
            acceptTreasure = false;
            acceptQuery = true;
            //Query request =  db.getCollection().whereEqualTo("name", itemName).endAt("name", itemName);
            db.getCollection().whereEqualTo("name", itemName)
                .addSnapshotListener( (value, error) ->
                {
                    List<DocumentSnapshot> treasureItems = value.getDocuments();
                    if (treasureItems.size() > 0){
                        // updating
                        TreasureItem temp = treasureItems.get(0).toObject(TreasureItem.class);
                        temp.setPiratesVisited(temp.getPiratesVisited() + 1);
                        // inserting to data base w same object. but incremented
                        if (acceptQuery == true){
                            db.incrementPiratesVisited(itemName, temp);
                            if (items.size() != 0){
                                items.pop();
                            }
                            readyTreasureCardView(v);
                        }
                        acceptQuery = false;
                    }

                }
            );

        });
        return v;
    }
    public void readyTreasureCardView(View v){
        imageView = v.findViewById(R.id.ItemSwipePhoto);
        if (items.size() != 0){
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
        }else{
            imageView.setImageDrawable(null);
        }

    }

}
