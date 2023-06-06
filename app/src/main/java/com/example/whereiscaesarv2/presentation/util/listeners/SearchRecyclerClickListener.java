package com.example.whereiscaesarv2.presentation.util.listeners;

import com.example.domain.models.DishModelDomain;

import java.util.List;

public interface SearchRecyclerClickListener {

    void onCardClick(DishModelDomain dishModelDomain);
    void progressBar(List<DishModelDomain> dishModelDomainList);
}
