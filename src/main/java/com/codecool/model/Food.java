package com.codecool.model;

import java.util.Objects;
import java.time.LocalDate;


public class Food extends AbstractModel {

    public final String name;
    public final String category;
    public final double amount;
    public final String measurement;
    public final String storage;
    public final LocalDate expiry;
    public final int userId;

    public Food(int id, String name, String category, double amount, String measurement, String storage, LocalDate expiry, int userId) {
        super(id);
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.measurement = measurement;
        this.storage = storage;
        this.expiry = expiry;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getMeasurement() {
        return measurement;
    }

    public String getStorage() {
        return storage;
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
            category.equals(food.category) &&
            measurement.equals(food.measurement) &&
            userId == food.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, measurement, userId);
    }
}
