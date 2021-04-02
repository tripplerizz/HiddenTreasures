package com.example.hiddentreasure.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hiddentreasure.R;
import com.example.hiddentreasure.models.TreasureItem;

import java.util.ArrayList;
import java.util.List;

public class TreasureAdapter extends RecyclerView.Adapter<TreasureListHolder> {
    private List<TreasureItem> mTreasureItems = new ArrayList<>();

    @NonNull
    @Override
    public TreasureListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TreasureListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_home, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TreasureListHolder holder, int position) {
        String name = mTreasureItems.get(position).getName();
        String description = mTreasureItems.get(position).getDescription();
        byte[] imgData = mTreasureItems.get(position).getImage();
        holder.bind(name, description, imgData);
    }

    @Override
    public int getItemCount() {
        return mTreasureItems.size();
    }

    public void setTreasureItems(List<TreasureItem> items) {
        mTreasureItems = items;
        notifyDataSetChanged();
    }
}

class TreasureListHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "TreasureListHolder";
    private TextView mName;
    private TextView mDescription;
    private ImageView mItemImage;

    public TreasureListHolder(@NonNull View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.name_tv);
        mDescription = itemView.findViewById(R.id.description_tv);
        mItemImage = itemView.findViewById(R.id.item_iv);
    }

    public void bind(String name, String description, byte[] imgData) {
        mName.setText(name);
        mDescription.setText(description);
        Glide
                .with(mItemImage.getContext())
                .load(BitmapFactory.decodeByteArray(imgData, 0, imgData.length))
                .centerCrop()
                .override(512)
                .into(mItemImage);
    }
}
