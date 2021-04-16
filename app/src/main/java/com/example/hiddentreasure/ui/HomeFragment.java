package com.example.hiddentreasure.ui;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import com.example.hiddentreasure.db.TreasureDatabase;
import com.example.hiddentreasure.db.TreasureItem;
import com.example.hiddentreasure.other.Constants;
import com.example.hiddentreasure.viewmodels.HomeViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

import java.util.List;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    public static final String TREASURE_TAG = "treasure_tag";
    private RecyclerView mTreasureRecyclerView;
    private TreasureAdapter mTreasureAdapter;
    private NavController mNavController;
    private HomeViewModel mHomeViewModel;

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
        // making Floating action invisable when un-needed
        FloatingActionButton fab = getActivity().findViewById(R.id.add_treasure_fab);
        if (fab != null){
            fab.setVisibility(View.VISIBLE);
        }
        initializeRecyclerView();
        return v;
    }

    private void initializeRecyclerView() {
        FirestoreRecyclerOptions<TreasureItem> options = new FirestoreRecyclerOptions.Builder<TreasureItem>()
                .setQuery(mHomeViewModel.getQuery(), TreasureItem.class)
                .build();

        mTreasureAdapter = new TreasureAdapter(options);
        mTreasureRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 3));
        mTreasureRecyclerView.setAdapter(mTreasureAdapter);

        mTreasureAdapter.setOnItemClickListener(item -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(TREASURE_TAG, item);
            mNavController.navigate(R.id.action_nav_home_to_treasureInfoFragment, bundle);
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mTreasureAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mTreasureAdapter.stopListening();
    }
}