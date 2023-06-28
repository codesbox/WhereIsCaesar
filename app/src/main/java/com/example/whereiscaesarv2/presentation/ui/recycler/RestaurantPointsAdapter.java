package com.example.whereiscaesarv2.presentation.ui.recycler;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whereiscaesarv2.databinding.RestaurantPointCardBinding;
import com.example.whereiscaesarv2.presentation.util.listeners.PointCardClickListener;
import com.example.domain.models.RestaurantPointModel;

import java.util.ArrayList;
import java.util.List;

public class RestaurantPointsAdapter extends RecyclerView.Adapter<RestaurantPointsAdapter.ViewHolder> {
    private android.content.Context context;
    PointCardClickListener listener;
    private List<RestaurantPointModel> itemList = new ArrayList<>();
    public RestaurantPointsAdapter(android.content.Context context, PointCardClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RestaurantPointsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RestaurantPointCardBinding binding = RestaurantPointCardBinding.inflate(inflater);
        return new ViewHolder(binding, context, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantPointsAdapter.ViewHolder holder, int position) {
        holder.bind(itemList.get(position));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setItemList(List<RestaurantPointModel>  restaurantPointModels){
        this.itemList = restaurantPointModels;
        notifyDataSetChanged();
    }

    public void deleteItem(RestaurantPointModel restaurantPointModel){
        int index = itemList.indexOf(restaurantPointModel);
        if (index != -1){
            itemList.remove(index);
            notifyItemRemoved(index);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private final RestaurantPointCardBinding binding;
        private android.content.Context context;
        private PointCardClickListener listener;
        public ViewHolder(RestaurantPointCardBinding binding, android.content.Context context, PointCardClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
            this.listener = listener;
        }
        public void bind(RestaurantPointModel restaurantPointModel){
            binding.address.setText(restaurantPointModel.address);

            binding.deleteBut2.setOnClickListener(v -> {
                deleteItem(restaurantPointModel);
                listener.onCardClick(restaurantPointModel);});
        }
    }
}