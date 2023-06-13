package com.example.whereiscaesarv2.presentation.ui.recycler;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.domain.models.FeedbackModel;
import com.example.whereiscaesarv2.databinding.DishFeedbackLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class DishFeedbackAdapter extends RecyclerView.Adapter<DishFeedbackAdapter.ViewHolder> {
    private android.content.Context context;
    private List<FeedbackModel> itemList = new ArrayList<>();
    public DishFeedbackAdapter(android.content.Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DishFeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DishFeedbackLayoutBinding binding = DishFeedbackLayoutBinding.inflate(inflater);
        return new ViewHolder(binding, context);
    }

    @Override
    public void onBindViewHolder(@NonNull DishFeedbackAdapter.ViewHolder holder, int position) {
        holder.bind(itemList.get(position));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setItemList(List<FeedbackModel>  feedbackList){
        this.itemList = feedbackList;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final DishFeedbackLayoutBinding binding;
        private android.content.Context context;
        public ViewHolder(DishFeedbackLayoutBinding binding, android.content.Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
        }
        public void bind(FeedbackModel feedback){
            binding.name.setText(feedback.userName);
            binding.estimation.setText(feedback.estimation.toString());
            binding.level.setText(String.format("Критик %s уровня", feedback.level.toString()));
            binding.feedback.setText(feedback.feedback);

        }
    }
}
