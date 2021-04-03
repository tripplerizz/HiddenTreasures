package com.example.hiddentreasure.adapters;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hiddentreasure.R;
import com.example.hiddentreasure.models.TreasureItem;

import java.util.ArrayList;
import java.util.List;

public class TreasureAdapter extends RecyclerView.Adapter<TreasureAdapter.TreasureListHolder> {
    private static final String TAG = "TreasureAdapter";
    private List<TreasureItem> mTreasureItems = new ArrayList<>();
    private OnItemClickListener mListener;

    @NonNull
    @Override
    public TreasureListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TreasureListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_home, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TreasureListHolder holder, int position) {
        byte[] imgData = mTreasureItems.get(position).getImage();
        holder.bind(imgData);
    }

    @Override
    public int getItemCount() {
        return mTreasureItems.size();
    }

    public void setTreasureItems(List<TreasureItem> items) {
        mTreasureItems = items;
        notifyDataSetChanged();
    }


    class TreasureListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = "TreasureListHolder";
        private ImageView mItemImage;

        public TreasureListHolder(@NonNull View itemView) {
            super(itemView);
            mItemImage = itemView.findViewById(R.id.item_iv);
            itemView.setOnClickListener(this);
        }

        public void bind(byte[] imgData) {
            Glide
                    .with(mItemImage.getContext())
                    .load(BitmapFactory.decodeByteArray(imgData, 0, imgData.length))
                    .centerCrop()
                    .into(mItemImage);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (mListener != null && position != RecyclerView.NO_POSITION) {
                mListener.onItemClick(mTreasureItems.get(position));
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TreasureItem item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        Log.d(TAG, "setOnItemClickListener: added listener");
        mListener = listener;
    }
}


