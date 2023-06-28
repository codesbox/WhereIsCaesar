package com.example.whereiscaesarv2.presentation.ui.recycler;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.domain.models.FeedbackModel;
import com.example.domain.models.NewFeedbackModel;
import com.example.domain.models.NewRestaurantModel;
import com.example.whereiscaesarv2.databinding.NewFeedbackCardBinding;
import com.example.whereiscaesarv2.databinding.NewRestaurantCardBinding;
import com.example.whereiscaesarv2.presentation.util.listeners.NewFeedbacksListener;
import com.example.whereiscaesarv2.presentation.util.listeners.NewRestaurantsListener;

import java.util.ArrayList;
import java.util.List;

public class NewFeedbackAdapter extends RecyclerView.Adapter<NewFeedbackAdapter.ViewHolder> {
    private android.content.Context context;
    NewFeedbacksListener listener;
    private List<NewFeedbackModel> itemList = new ArrayList<>();
    public NewFeedbackAdapter(android.content.Context context, NewFeedbacksListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewFeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NewFeedbackCardBinding binding = NewFeedbackCardBinding.inflate(inflater);
        return new ViewHolder(binding, context, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewFeedbackAdapter.ViewHolder holder, int position) {
        holder.bind(itemList.get(position));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setItemList(List<NewFeedbackModel>  newRestaurantModel){
        this.itemList = newRestaurantModel;
        notifyDataSetChanged();
    }

    public void deleteItem(NewFeedbackModel newRestaurantModel){
        int index = itemList.indexOf(newRestaurantModel);
        if (index != -1){
            itemList.remove(index);
            notifyItemRemoved(index);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private final NewFeedbackCardBinding binding;
        private android.content.Context context;
        private NewFeedbacksListener listener;
        public ViewHolder(NewFeedbackCardBinding binding, android.content.Context context, NewFeedbacksListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
            this.listener = listener;
        }
        public void bind(NewFeedbackModel newRestaurantModel){
            binding.restaurantName.setText(newRestaurantModel.restaurantName);
            binding.firstName.setText(newRestaurantModel.firstName);
            binding.feedback.setText(newRestaurantModel.feedbackText);
            binding.estimation.setText(newRestaurantModel.estimation.toString());
            binding.lastName.setText(newRestaurantModel.lastName);
            binding.dishName.setText(newRestaurantModel.dishName);
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
