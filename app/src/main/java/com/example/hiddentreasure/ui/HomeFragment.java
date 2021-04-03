package com.example.hiddentreasure.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hiddentreasure.R;
import com.example.hiddentreasure.adapters.TreasureAdapter;
import com.example.hiddentreasure.models.TreasureItem;
import com.example.hiddentreasure.viewmodels.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private HomeViewModel mHomeViewModel;
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
        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mHomeViewModel.init();
        initializeRecyclerView();
        mHomeViewModel.getTreasureItems().observe(requireActivity(), new Observer<List<TreasureItem>>() {
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
                mNavController.navigate(R.id.action_nav_home_to_treasureInfoFragment);
            }
        });
        mTreasureRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 3));
        mTreasureRecyclerView.setAdapter(mTreasureAdapter);
    }
}