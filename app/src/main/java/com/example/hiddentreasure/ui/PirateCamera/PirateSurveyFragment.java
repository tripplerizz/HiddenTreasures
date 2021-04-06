package com.example.hiddentreasure.ui.PirateCamera;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.hiddentreasure.R;


public class PirateSurveyFragment extends Fragment {
    private ImageView mImageView;
    private Button mSubmitTreasureBtn;
    private EditText mTreasureDescriptionET;
    private EditText mTreasureNameET;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pirate_survey, container, false);
        Bundle bundle = getArguments();
        Bitmap imageBitmap = (Bitmap) bundle.get("data");
        mImageView = v.findViewById(R.id.photoTaken);
        mSubmitTreasureBtn = v.findViewById(R.id.submit_treasure_btn);
        mTreasureDescriptionET = v.findViewById(R.id.treasure_description_et);
        mTreasureNameET = v.findViewById(R.id.treasure_name_et);
        Glide
                .with(getActivity())
                .load(imageBitmap)
                .fitCenter()
                .into(mImageView);

        mSubmitTreasureBtn.setOnClickListener(view -> {
            String name = mTreasureNameET.getText().toString();
            String description = mTreasureDescriptionET.getText().toString();
        });

        return v;
    }

}
