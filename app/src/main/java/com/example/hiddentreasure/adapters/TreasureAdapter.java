package com.example.hiddentreasure.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hiddentreasure.R;
import com.example.hiddentreasure.models.TreasureItem;

import java.util.ArrayList;

public class TreasureAdapter extends RecyclerView.Adapter<TreasureListHolder> {
    ArrayList<TreasureItem> mTreasureItems;

    public TreasureAdapter(ArrayList<TreasureItem> items) {
        mTreasureItems = items;
    }

    @NonNull
    @Override
    public TreasureListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TreasureListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_home, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TreasureListHolder holder, int position) {
        String name = mTreasureItems.get(position).getName();
        String description = mTreasureItems.get(position).getDescription();
        holder.bind(name, description);
    }

    @Override
    public int getItemCount() {
        return mTreasureItems.size();
    }
}

class TreasureListHolder extends RecyclerView.ViewHolder {
    private TextView mName;
    private TextView mDescription;

    public TreasureListHolder(@NonNull View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.name_tv);
        mDescription = itemView.findViewById(R.id.description_tv);
    }

    public void bind(String name, String description) {
        mName.setText(name);
        mDescription.setText(description);
    }
}
