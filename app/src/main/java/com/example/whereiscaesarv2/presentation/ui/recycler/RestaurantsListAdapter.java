package com.example.whereiscaesarv2.presentation.ui.recycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.domain.models.DishModelDomain;
import com.example.domain.models.RestaurantModelDomain;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.RestaurantSmallCardBinding;
import com.example.whereiscaesarv2.databinding.SearchItemBinding;
import com.example.whereiscaesarv2.presentation.util.listeners.RestaurantsListCardClickListener;
import com.example.whereiscaesarv2.presentation.util.listeners.SearchRecyclerClickListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsListAdapter extends RecyclerView.Adapter<RestaurantsListAdapter.ViewHolder> {
    private final Context context;
    RestaurantsListCardClickListener listener;
    private List<RestaurantModelDomain> itemList = new ArrayList<>();
    public RestaurantsListAdapter(android.content.Context context, RestaurantsListCardClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RestaurantsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RestaurantSmallCardBinding binding = RestaurantSmallCardBinding.inflate(inflater);
        return new ViewHolder(binding, context, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantsListAdapter.ViewHolder holder, int position) {
        holder.bind(itemList.get(position));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setItemList(List<RestaurantModelDomain>  restaurantModelDomainList){
        this.itemList = restaurantModelDomainList;
        listener.progressBar(restaurantModelDomainList);
        notifyDataSetChanged();
    }

    public void deleteItem(RestaurantModelDomain restaurantModelDomain){
        int index = itemList.indexOf(restaurantModelDomain);
        if (index != -1){
            itemList.remove(index);
            listener.progressBar(itemList);
            notifyItemRemoved(index);
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final RestaurantSmallCardBinding binding;
        private final Context context;
        private final RestaurantsListCardClickListener listener;
        public ViewHolder(RestaurantSmallCardBinding binding, android.content.Context context, RestaurantsListCardClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
            this.listener = listener;
        }
        public void bind(RestaurantModelDomain restaurantModelDomain){
            binding.name.setText(restaurantModelDomain.restaurantName);
            binding.cardView.setOnClickListener(v -> listener.onCardClick(restaurantModelDomain));
        }
    }
}
