package com.example.hiddentreasure;
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

import static android.app.Activity.RESULT_OK;


public class PirateSurveyFragment extends Fragment {
    private ImageView mImageView;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public PirateSurveyFragment(){
        super(R.layout.pirate_survay);
    }

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
