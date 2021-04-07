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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.hiddentreasure.R;
import com.example.hiddentreasure.db.TreasureDatabase;


public class PirateSurveyFragment extends Fragment {
    private ImageView mImageView;
    private Button mSubmitTreasureBtn;
    private EditText mTreasureDescriptionET;
    private EditText mTreasureNameET;
    private Bitmap mImageBitmap;
    private TreasureDatabase mDatabase;
    private NavController mNavController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = TreasureDatabase.getInstance(getActivity());
        mNavController = NavHostFragment.findNavController(this);
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
            String name = mTreasureNameET.getText().toString();
            String description = mTreasureDescriptionET.getText().toString();
            mDatabase.uploadPhoto(name, description, mImageBitmap);
            mNavController.navigate(R.id.action_nav_survey_to_nav_home);
        });

        return v;
    }

}
