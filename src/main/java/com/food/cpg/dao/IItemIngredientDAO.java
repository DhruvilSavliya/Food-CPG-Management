package com.food.cpg.dao;


import com.food.cpg.models.Item;
import com.food.cpg.models.ItemIngredient;

import java.util.List;

public interface IItemIngredientDAO {
    void saveItemIngredient(List<ItemIngredient> itemIngredients, Integer itemId);

}
