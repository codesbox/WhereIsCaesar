package com.example.whereiscaesarv2.presentation.ui.recycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.domain.models.DishModelDomain;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.SearchItemBinding;
import com.example.whereiscaesarv2.databinding.SearchResultItemBinding;
import com.example.whereiscaesarv2.presentation.util.listeners.SearchRecyclerClickListener;

import java.util.ArrayList;
import java.util.List;

public class RequestSearchAdapter extends RecyclerView.Adapter<RequestSearchAdapter.ViewHolder> {
    private final Context context;
    SearchRecyclerClickListener listener;
    private List<DishModelDomain> itemList = new ArrayList<>();
    public RequestSearchAdapter(android.content.Context context, SearchRecyclerClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RequestSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SearchResultItemBinding binding = SearchResultItemBinding.inflate(inflater);
        return new ViewHolder(binding, context, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestSearchAdapter.ViewHolder holder, int position) {
        holder.bind(itemList.get(position));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setItemList(List<DishModelDomain>  dishModelDomainList){
        this.itemList = dishModelDomainList;
        listener.progressBar(dishModelDomainList);
        notifyDataSetChanged();
    }

    public void deleteItem(DishModelDomain dishModelDomain){
        int index = itemList.indexOf(dishModelDomain);
        if (index != -1){
            itemList.remove(index);
            listener.progressBar(itemList);
            notifyItemRemoved(index);
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final SearchResultItemBinding binding;
        private final Context context;
        private final SearchRecyclerClickListener listener;
        public ViewHolder(SearchResultItemBinding binding, android.content.Context context, SearchRecyclerClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
            this.listener = listener;
        }
        public void bind(DishModelDomain categoryOrDishModelDomain){
            binding.name.setText(categoryOrDishModelDomain.title);
            Glide.with(context)
                    .load(categoryOrDishModelDomain.imageUrl)
                    .error(R.drawable.image_not_supported)
                    .into(binding.imageView);
            binding.deleteBut.setOnClickListener(v -> listener.onCardClick(categoryOrDishModelDomain));
        }
    }
}

