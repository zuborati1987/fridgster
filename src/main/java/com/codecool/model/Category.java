package com.codecool.model;

import java.util.Objects;

public class Category extends AbstractModel {

    public final String name;
    public final int userId;

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }

    public Category(int id, String name, int userId) {
        super(id);
        this.name = name;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name) &&
            userId == category.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, userId);
    }
}

