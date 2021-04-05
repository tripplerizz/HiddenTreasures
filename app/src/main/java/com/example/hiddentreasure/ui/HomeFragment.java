package com.example.hiddentreasure.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hiddentreasure.R;
import com.example.hiddentreasure.adapters.TreasureAdapter;
import com.example.hiddentreasure.db.TreasureItem;
import com.example.hiddentreasure.viewmodels.HomeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    public static final String TREASURE_TAG = "treasure_tag";
    private RecyclerView mTreasureRecyclerView;
    private TreasureAdapter mTreasureAdapter;
    private NavController mNavController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNavController = NavHostFragment.findNavController(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mTreasureRecyclerView = v.findViewById(R.id.treasure_rv);
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.init();
        initializeRecyclerView();
        homeViewModel.getTreasureItems().observe(requireActivity(), new Observer<List<TreasureItem>>() {
            @Override
            public void onChanged(List<TreasureItem> treasureItems) {
                mTreasureAdapter.setTreasureItems(treasureItems);
            }
        });
        return v;
    }

    private void initializeRecyclerView() {
        mTreasureAdapter = new TreasureAdapter();
        mTreasureAdapter.setOnItemClickListener(new TreasureAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TreasureItem item) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(TREASURE_TAG, item);
                mNavController.navigate(R.id.action_nav_home_to_treasureInfoFragment, bundle);
            }
        });
        mTreasureRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 3));
        mTreasureRecyclerView.setAdapter(mTreasureAdapter);
    }

}