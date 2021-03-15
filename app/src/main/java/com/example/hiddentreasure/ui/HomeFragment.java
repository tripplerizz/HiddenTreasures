package com.example.hiddentreasure.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hiddentreasure.R;
import com.example.hiddentreasure.adapters.TreasureAdapter;
import com.example.hiddentreasure.models.TreasureItem;
import com.example.hiddentreasure.viewmodels.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private HomeViewModel mHomeViewModel;
    private RecyclerView mTreasureRecyclerView;
    private TreasureAdapter mTreasureAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mTreasureRecyclerView = v.findViewById(R.id.treasure_rv);
        mHomeViewModel = new HomeViewModel();
        mHomeViewModel.init();
        mHomeViewModel.getTreasureItems().observe(requireActivity(), new Observer<List<TreasureItem>>() {
            @Override
            public void onChanged(List<TreasureItem> treasureItems) {
                mTreasureAdapter.notifyDataSetChanged();
            }
        });
        initializeRecyclerView();
        return v;
    }

    private void initializeRecyclerView() {
        mTreasureAdapter = new TreasureAdapter((ArrayList<TreasureItem>) mHomeViewModel.getTreasureItems().getValue());
        mTreasureRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(),3));
        mTreasureRecyclerView.setAdapter(mTreasureAdapter);
    }
}