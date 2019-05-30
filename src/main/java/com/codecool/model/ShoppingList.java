package com.codecool.model;

import java.time.LocalDate;
import java.util.Objects;


public class ShoppingList extends AbstractModel{

    public final String name;
    public final double amount;
    public final String measurement;

    public ShoppingList(int id, String name, double amount, String measurement) {
        super(id);
        this.name = name;
        this.amount = amount;
        this.measurement = measurement;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getMeasurement() {
        return measurement;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ShoppingList shoppingList = (ShoppingList) o;
        return Objects.equals(name, shoppingList.name) &&
            measurement.equals(shoppingList.measurement) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, measurement);
    }
}
