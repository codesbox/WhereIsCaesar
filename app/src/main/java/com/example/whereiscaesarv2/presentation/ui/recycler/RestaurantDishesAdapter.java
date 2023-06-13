package com.example.whereiscaesarv2.presentation.ui.recycler;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.domain.models.MapDishCard;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.RestaurantDishCardLayoutBinding;
import com.example.whereiscaesarv2.presentation.util.listeners.RestaurantDishCardClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDishesAdapter extends RecyclerView.Adapter<RestaurantDishesAdapter.ViewHolder> {
    private android.content.Context context;
    RestaurantDishCardClickListener listener;
    private List<MapDishCard> itemList = new ArrayList<>();
    public RestaurantDishesAdapter(android.content.Context context, RestaurantDishCardClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RestaurantDishesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RestaurantDishCardLayoutBinding binding = RestaurantDishCardLayoutBinding.inflate(inflater);
        return new ViewHolder(binding, context, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantDishesAdapter.ViewHolder holder, int position) {
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


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final RestaurantDishCardLayoutBinding binding;
        private android.content.Context context;
        private RestaurantDishCardClickListener listener;
        public ViewHolder(RestaurantDishCardLayoutBinding binding, android.content.Context context, RestaurantDishCardClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
            this.listener = listener;
        }
        public void bind(MapDishCard mapDishCard){
            binding.name.setText(mapDishCard.dishName);

            if (mapDishCard.sum == 0.0){
                binding.estimation.setText("0.0");
            }
            else{
                double result = (double) mapDishCard.sum / mapDishCard.counter;
                DecimalFormat decimalFormat = new DecimalFormat("#0.0");
                String formattedResult = decimalFormat.format(result);
                binding.estimation.setText(formattedResult);

            }
            binding.counter.setText(String.format("Отзывов: %s", mapDishCard.counter.toString()));
            Glide.with(context)
                    .load(mapDishCard.imageUrl)
                    .error(R.drawable.image_not_supported)
                    .into(binding.imageView);
            binding.cardView.setOnClickListener(v -> {listener.onCardClick(mapDishCard);});
        }
    }
}
