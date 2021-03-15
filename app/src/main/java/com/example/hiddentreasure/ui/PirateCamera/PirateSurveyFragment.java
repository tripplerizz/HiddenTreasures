package com.example.hiddentreasure.ui.PirateCamera;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.hiddentreasure.R;

import static android.app.Activity.RESULT_OK;


public class PirateSurveyFragment extends Fragment {
    private ImageView mImageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pirate_survay, container, false);
        Bundle bundle = getArguments();
        Bitmap imageBitmap = (Bitmap) bundle.get("data");
        mImageView = v.findViewById(R.id.photoTaken);
        mImageView.setImageBitmap(imageBitmap);

        return v;
    }

}
