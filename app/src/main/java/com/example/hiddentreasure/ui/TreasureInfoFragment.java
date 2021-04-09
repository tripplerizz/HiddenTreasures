package com.example.hiddentreasure.ui;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hiddentreasure.R;
import com.example.hiddentreasure.db.TreasureItem;

public class TreasureInfoFragment extends Fragment {
    private static final String TAG = "TreasureInfoFragment";
    private TreasureItem item;
    private String itemInfoString = "Name: %s\nDescription: %s\n";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            item = bundle.getParcelable(HomeFragment.TREASURE_TAG);
            Log.d(TAG, "onCreate: " + item.getName());
            Log.d(TAG, "onCreate: " + item.getDescription());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_treasure_info, container, false);
        ImageView itemImage = v.findViewById(R.id.itemImage);
        String ImageUrl = item.getImageUrl();
        Glide
                .with(itemImage.getContext())
                .load(ImageUrl)
                .centerCrop()
                .into(itemImage);
        TextView itemInfo = v.findViewById(R.id.itemInfo);
        itemInfoString = String.format(itemInfoString, item.getName(), item.getDescription());
        itemInfo.setText(itemInfoString);
        return v;
    }
}