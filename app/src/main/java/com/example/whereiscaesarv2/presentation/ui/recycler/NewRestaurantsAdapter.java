package com.example.whereiscaesarv2.presentation.ui.recycler;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.domain.models.NewRestaurantModel;
import com.example.domain.models.RestaurantPointModel;
import com.example.whereiscaesarv2.databinding.NewRestaurantCardBinding;
import com.example.whereiscaesarv2.databinding.RestaurantPointCardBinding;
import com.example.whereiscaesarv2.presentation.util.listeners.NewRestaurantsListener;
import com.example.whereiscaesarv2.presentation.util.listeners.PointCardClickListener;

import java.util.ArrayList;
import java.util.List;

public class NewRestaurantsAdapter extends RecyclerView.Adapter<NewRestaurantsAdapter.ViewHolder> {
    private android.content.Context context;
    NewRestaurantsListener listener;
    private List<NewRestaurantModel> itemList = new ArrayList<>();
    public NewRestaurantsAdapter(android.content.Context context, NewRestaurantsListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewRestaurantsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NewRestaurantCardBinding binding = NewRestaurantCardBinding.inflate(inflater);
        return new ViewHolder(binding, context, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewRestaurantsAdapter.ViewHolder holder, int position) {
        holder.bind(itemList.get(position));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setItemList(List<NewRestaurantModel>  newRestaurantModel){
        this.itemList = newRestaurantModel;
        notifyDataSetChanged();
    }

    public void deleteItem(NewRestaurantModel newRestaurantModel){
        int index = itemList.indexOf(newRestaurantModel);
        if (index != -1){
            itemList.remove(index);
            notifyItemRemoved(index);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private final NewRestaurantCardBinding binding;
        private android.content.Context context;
        private NewRestaurantsListener listener;
        public ViewHolder(NewRestaurantCardBinding binding, android.content.Context context, NewRestaurantsListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
            this.listener = listener;
        }
        public void bind(NewRestaurantModel newRestaurantModel){
            binding.restaurantName.setText(newRestaurantModel.restaurantName);
            binding.name.setText(newRestaurantModel.name);
            binding.lastName.setText(newRestaurantModel.lastName);
            binding.addBut.setOnClickListener(v -> {
                deleteItem(newRestaurantModel);
                listener.approve(newRestaurantModel);
            });
            binding.deleteBut.setOnClickListener(v -> {
                deleteItem(newRestaurantModel);
                listener.reject(newRestaurantModel);
            });

        }
    }
}
