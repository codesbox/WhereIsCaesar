package com.example.whereiscaesarv2.presentation.ui.recycler;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whereiscaesarv2.databinding.MyFeedbackCardBinding;
import com.example.domain.models.MyFeedback;

import java.util.ArrayList;
import java.util.List;

public class MyFeedbackAdapter extends RecyclerView.Adapter<MyFeedbackAdapter.ViewHolder> {
    private android.content.Context context;
    private List<MyFeedback> itemList = new ArrayList<>();
    public MyFeedbackAdapter(android.content.Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyFeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MyFeedbackCardBinding binding = MyFeedbackCardBinding.inflate(inflater);
        return new ViewHolder(binding, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyFeedbackAdapter.ViewHolder holder, int position) {
        holder.bind(itemList.get(position));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setItemList(List<MyFeedback>  myFeedbackList){
        this.itemList = myFeedbackList;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final MyFeedbackCardBinding binding;
        private android.content.Context context;
        public ViewHolder(MyFeedbackCardBinding binding, android.content.Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
        }
        public void bind(MyFeedback myFeedback){
            binding.dishName.setText(myFeedback.dishName);
            binding.estimation.setText(myFeedback.estimation.toString());
            binding.restaurantName.setText(myFeedback.restaurantName);
            binding.feedback.setText(myFeedback.feedback);
        }
    }
}
