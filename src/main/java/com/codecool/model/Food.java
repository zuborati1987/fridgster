package com.codecool.model;

import java.util.Objects;
import java.time.LocalDate;


public class Food extends AbstractModel {

    public final String name;
    public final int categoryId;
    public final double amount;
    public final int measurementId;
    public final int storageId;
    public final LocalDate expiry;
    public final int userId;

    public Food(int id, String name, int categoryId, double amount, int measurementId, int storageId, LocalDate expiry, int userId) {
        super(id);
        this.name = name;
        this.categoryId = categoryId;
        this.amount = amount;
        this.measurementId = measurementId;
        this.storageId = storageId;
        this.expiry = expiry;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public double getAmount() {
        return amount;
    }

    public int getMeasurementId() {
        return measurementId;
    }

    public int getStorageId() {
        return storageId;
    }

    public LocalDate getExpiry() {
        return expiry;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Food food = (Food) o;
        return Objects.equals(name, food.name) &&
            categoryId == food.categoryId &&
            measurementId == food.measurementId &&
            userId == food.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, measurementId, userId);
    }
}
