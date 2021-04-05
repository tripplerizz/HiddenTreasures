package com.example.hiddentreasure.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hiddentreasure.R;
import com.example.hiddentreasure.db.TreasureItem;

public class TreasureInfoFragment extends Fragment {
    private static final String TAG = "TreasureInfoFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        TreasureItem item;
        if (bundle != null) {
            item = bundle.getParcelable(HomeFragment.TREASURE_TAG);
            Log.d(TAG, "onCreate: " + item.getName());
            Log.d(TAG, "onCreate: " + item.getDescription());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO add UI to this fragment
        // This is where the user clicks on a picture from Home and you need to display stuff
        // Let me know what features you want to add to this area such as location, requesting the
        // item, etc. You'll want to display the name, description, and picture of the item. All of
        // info you need is in the TreasureItem object. An example on how to retrieve the data
        // is in onCreate()
        return inflater.inflate(R.layout.fragment_treasure_info, container, false);
    }
}