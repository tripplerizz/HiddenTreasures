package com.example.hiddentreasure.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
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
import com.google.firebase.firestore.GeoPoint;

public class TreasureInfoFragment extends Fragment {
    private static final String TAG = "TreasureInfoFragment";
    private TreasureItem item;
    private TextView mDescription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            item = bundle.getParcelable(HomeFragment.TREASURE_TAG);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_treasure_info, container, false);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        ImageView itemImage = v.findViewById(R.id.itemImage);
        String ImageUrl = item.getImageUrl();
        Glide
                .with(itemImage.getContext())
                .load(ImageUrl)
                .centerCrop()
                .into(itemImage);
        TextView itemInfo = v.findViewById(R.id.itemInfo);
        mDescription = v.findViewById(R.id.description_tv);
        itemInfo.setText(item.getName());
        mDescription.setText(item.getDescription());
        return v;
    }

    private final OnMapReadyCallback callback = googleMap -> {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        GeoPoint geoPoint = item.getLocation();
        LatLng location = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());

        googleMap.addMarker(new MarkerOptions()
                .position(location)
                .title(item.getName())
        );

        googleMap.setOnMarkerClickListener((Marker marker) -> {
            LatLng position = marker.getPosition();
            Uri uri = Uri.parse("geo:0,0?q=" + position.latitude + "," + position.longitude);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        });

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14f));
    };
}