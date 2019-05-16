package com.codecool.model;

import java.util.Objects;

public class Measurement extends AbstractModel {

    public final String name;
    public final int userId;

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }

    public Measurement(int id, String name, int userId) {
        super(id);
        this.name = name;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Measurement measurement = (Measurement) o;
        return Objects.equals(name, measurement.name) &&
            userId == measurement.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, userId);
    }
}

