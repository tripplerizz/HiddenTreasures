package com.example.hiddentreasure.ui.PirateCamera;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.hiddentreasure.R;
import com.example.hiddentreasure.db.TreasureItem;
import com.example.hiddentreasure.ui.HomeFragment;

public class PirateSwipeFragment extends Fragment {
    private TreasureItem item;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        return v;


    }

}
