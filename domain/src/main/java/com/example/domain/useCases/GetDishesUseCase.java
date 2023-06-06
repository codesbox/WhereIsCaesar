package com.example.domain.useCases;

import com.example.domain.listeners.DishesListener;
import com.example.domain.repository.DishesRepository;

public class GetDishesUseCase {

    DishesRepository dishesRepository;
    DishesListener listener;
    String tag;

    public GetDishesUseCase(DishesRepository dishesRepository, DishesListener listener, String tag){
        this.dishesRepository = dishesRepository;
        this.listener = listener;
        this.tag = tag;
    }
    public void execute(){ dishesRepository.GetDishes(tag, listener);}


}
