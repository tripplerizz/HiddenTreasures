package com.example.hiddentreasure.ui.PirateCamera;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.hiddentreasure.R;
import com.example.hiddentreasure.db.TreasureDatabase;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.GeoPoint;


public class PirateSurveyFragment extends Fragment {
    private ImageView mImageView;
    private Button mSubmitTreasureBtn;
    private EditText mTreasureDescriptionET;
    private EditText mTreasureNameET;
    private Bitmap mImageBitmap;
    private TreasureDatabase mDatabase;
    private NavController mNavController;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = TreasureDatabase.getInstance(getActivity());
        mNavController = NavHostFragment.findNavController(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pirate_survey, container, false);
        Bundle bundle = getArguments();
        mImageBitmap = (Bitmap) bundle.get("data");
        mImageView = v.findViewById(R.id.photoTaken);
        mSubmitTreasureBtn = v.findViewById(R.id.submit_treasure_btn);
        mTreasureDescriptionET = v.findViewById(R.id.treasure_description_et);
        mTreasureNameET = v.findViewById(R.id.treasure_name_et);
        Glide
                .with(requireActivity())
                .load(mImageBitmap)
                .fitCenter()
                .into(mImageView);

        mSubmitTreasureBtn.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            String name = mTreasureNameET.getText().toString();
            String description = mTreasureDescriptionET.getText().toString();

            mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                Location location = task.getResult();
                GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                mDatabase.uploadPhoto(name, description, mImageBitmap, geoPoint);
            });

            mNavController.navigate(R.id.action_nav_survey_to_nav_home);
        });

        return v;
    }

}
