package com.example.whereiscaesarv2.presentation.ui.recycler;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.domain.models.MapDishCard;
import com.example.domain.models.MyRestaurantsModel;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.MyRestaurantCardRecyclerBinding;
import com.example.whereiscaesarv2.presentation.util.listeners.MyRestaurantCardClickListener;
import com.example.whereiscaesarv2.presentation.util.listeners.RestaurantDishCardClickListener;

import java.util.ArrayList;
import java.util.List;

public class MyRestaurantsAdapter extends RecyclerView.Adapter<MyRestaurantsAdapter.ViewHolder> {
    private android.content.Context context;
    MyRestaurantCardClickListener listener;
    private List<MyRestaurantsModel> restaurants = new ArrayList<>();
    public MyRestaurantsAdapter(android.content.Context context, MyRestaurantCardClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyRestaurantsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MyRestaurantCardRecyclerBinding binding = MyRestaurantCardRecyclerBinding.inflate(inflater);
        return new ViewHolder(binding, context, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRestaurantsAdapter.ViewHolder holder, int position) {
        holder.bind(restaurants.get(position));

    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setRestaurants(List<MyRestaurantsModel>  mapDishCardList){
        this.restaurants = mapDishCardList;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final MyRestaurantCardRecyclerBinding binding;
        private android.content.Context context;
        private MyRestaurantCardClickListener listener;
        public ViewHolder(MyRestaurantCardRecyclerBinding binding, android.content.Context context, MyRestaurantCardClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
            this.listener = listener;
        }


        public void bind(MyRestaurantsModel restaurant){
            binding.cardView.setOnClickListener(v -> {listener.onCardClick(restaurant);});
            binding.name.setText(restaurant.restaurantName);
            if (restaurant.status.equals("m")) {
                Log.d("VVVVVVVVVVV", restaurant.status);
                binding.status.setText("На проверке");
                binding.status.setTextColor(Color.parseColor("#FFA11B"));
            }
            if (restaurant.status.equals("s")){
                binding.status.setVisibility(View.GONE);
            }
            if (restaurant.status.equals("f")){
                binding.status.setText("Не прошел проверку");
                binding.status.setTextColor(Color.parseColor("#AA0000"));
            }
        }
    }
}

