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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.hiddentreasure.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

public class MapsFragment extends Fragment {
    private static final String TAG = "MapsFragment";
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mCurrentLocation;


    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            String url = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/Midtown_atlanta_%28cropped%29.jpg/850px-Midtown_atlanta_%28cropped%29.jpg";
            googleMap.setMyLocationEnabled(true);
            Task<Location> task = mFusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(location -> {
                if (location != null) {
                    mCurrentLocation = location;
                    LatLng curLocation = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                    Glide.with(MapsFragment.this)
                            .asBitmap()
                            .load(url)
                            .override(20, 32)
                            .into(new CustomTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    Log.d(TAG, "onResourceReady: image is ready");
                                    googleMap.addMarker(new MarkerOptions()
                                            .position(curLocation)
                                            .title("Marker in Atlanta")
                                            .icon(BitmapDescriptorFactory.fromBitmap(resource))
                                    );
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                }
                            });
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLocation, 16f));
                }
            });

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
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