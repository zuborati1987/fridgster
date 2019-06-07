package com.codecool.dto;


import com.codecool.model.Food;

import java.util.List;

public final class ShoppingListDto {

    private final int userId;
    private final List<Food> sList;

    public ShoppingListDto(int userId, List<Food> sList) {
        this.userId = userId;
        this.sList = sList;
    }

    public int getUserId() {
        return userId;
    }

    public List<Food> getsList() {
        return sList;
    }
}
