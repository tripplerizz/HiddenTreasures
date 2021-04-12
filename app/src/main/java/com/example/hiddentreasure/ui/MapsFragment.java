package com.example.hiddentreasure.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.hiddentreasure.R;
import com.example.hiddentreasure.db.TreasureDatabase;
import com.example.hiddentreasure.db.TreasureItem;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;

public class MapsFragment extends Fragment {
    private static final String TAG = "MapsFragment";
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mCurrentLocation;
    private TreasureDatabase mDatabase;
    private Button mGetMoreInfoBtn;

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            CollectionReference reference = mDatabase.getCollection();

            // Enable the map to mark where you are with blue marker
            googleMap.setMyLocationEnabled(true);

            // get current location of user
            Task<Location> task = mFusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(location -> {
                if (location != null) {
                    mCurrentLocation = location;
                    LatLng curLocation = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

                    addMarker(reference, googleMap);

                    // move camera to current location
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLocation, 14f));
                }
            });

            // googleMap.setOnMarkerClickListener(marker -> false);
        }
    };

    private void addMarker(CollectionReference reference, GoogleMap googleMap) {
        reference.addSnapshotListener((value, error) -> {
            // Iterate through each item in the database and add the item info to map
            for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                TreasureItem item = documentSnapshot.toObject(TreasureItem.class);
                GeoPoint itemLocation = item.getLocation();
                LatLng itemLatLng = new LatLng(itemLocation.getLatitude(), itemLocation.getLongitude());

                if (getActivity() == null)  {
                    continue;
                }

                addMarkerToMapWithPicture(googleMap, itemLatLng, item);
            }
        });
    }

    private void addMarkerToMapWithPicture(GoogleMap googleMap, LatLng location, TreasureItem item) {
        Glide.with(MapsFragment.this)
                .asBitmap()
                .load(item.getImageUrl())
                .override(125, 125)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Log.d(TAG, "onResourceReady: image is ready");
                        googleMap.addMarker(new MarkerOptions()
                                .position(location)
                                .title(item.getName())
                                .icon(BitmapDescriptorFactory.fromBitmap(resource))
                        );
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = TreasureDatabase.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_maps, container, false);
        mGetMoreInfoBtn = v.findViewById(R.id.get_more_info_btn);
        mGetMoreInfoBtn.setOnClickListener(v1 -> {

        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}