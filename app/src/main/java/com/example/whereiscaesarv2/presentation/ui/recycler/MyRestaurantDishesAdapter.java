package com.example.whereiscaesarv2.presentation.ui.recycler;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.domain.models.DishModelDomain;
import com.example.domain.models.MapDishCard;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.MyRestDishCardLayoutBinding;
import com.example.whereiscaesarv2.databinding.RestaurantDishCardLayoutBinding;
import com.example.whereiscaesarv2.presentation.util.listeners.RestaurantDishCardClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MyRestaurantDishesAdapter extends RecyclerView.Adapter<MyRestaurantDishesAdapter.ViewHolder> {
    private android.content.Context context;
    RestaurantDishCardClickListener listener;
    private List<MapDishCard> itemList = new ArrayList<>();
    public MyRestaurantDishesAdapter(android.content.Context context, RestaurantDishCardClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyRestaurantDishesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MyRestDishCardLayoutBinding binding = MyRestDishCardLayoutBinding.inflate(inflater);
        return new ViewHolder(binding, context, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRestaurantDishesAdapter.ViewHolder holder, int position) {
        holder.bind(itemList.get(position));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setItemList(List<MapDishCard>  mapDishCardList){
        this.itemList = mapDishCardList;
        notifyDataSetChanged();
    }

    public void deleteItem(MapDishCard dishModelDomain){
        int index = itemList.indexOf(dishModelDomain);
        if (index != -1){
            itemList.remove(index);
            notifyItemRemoved(index);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private final MyRestDishCardLayoutBinding binding;
        private android.content.Context context;
        private RestaurantDishCardClickListener listener;
        public ViewHolder(MyRestDishCardLayoutBinding binding, android.content.Context context, RestaurantDishCardClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
            this.listener = listener;
        }
        public void bind(MapDishCard mapDishCard){
            binding.name.setText(mapDishCard.dishName);
            Glide.with(context)
                    .load(mapDishCard.imageUrl)
                    .error(R.drawable.image_not_supported)
                    .into(binding.imageView);

            binding.deleteBut.setOnClickListener(v -> {
                deleteItem(mapDishCard);

                listener.onCardClick(mapDishCard);});
        }
    }
}

