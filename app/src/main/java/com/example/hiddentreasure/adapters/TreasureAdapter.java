package com.example.hiddentreasure.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hiddentreasure.R;
import com.example.hiddentreasure.db.TreasureItem;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class TreasureAdapter extends FirestoreRecyclerAdapter<TreasureItem, TreasureAdapter.TreasureListHolder> {
    private static final String TAG = "TreasureAdapter";
    private OnItemClickListener mListener;

    public TreasureAdapter(@NonNull FirestoreRecyclerOptions<TreasureItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TreasureListHolder holder, int position, @NonNull TreasureItem model) {
        holder.bind(model.getImageUrl());
    }

    @NonNull
    @Override
    public TreasureListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_home, parent, false);
        return new TreasureListHolder(v);
    }


    class TreasureListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = "TreasureListHolder";
        private ImageView mItemImage;

        public TreasureListHolder(@NonNull View itemView) {
            super(itemView);
            mItemImage = itemView.findViewById(R.id.item_iv);
            itemView.setOnClickListener(this);
        }

        public void bind(String url) {
            Glide
                    .with(mItemImage.getContext())
                    .load(url)
                    .centerCrop()
                    .into(mItemImage);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (mListener != null && position != RecyclerView.NO_POSITION) {
                mListener.onItemClick(getItem(position));
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TreasureItem item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}


