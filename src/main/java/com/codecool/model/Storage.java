package com.codecool.model;

import java.util.Objects;

public class Storage extends AbstractModel {

    public final String name;
    public final int userId;

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }

    public Storage(int id, String name, int userId) {
        super(id);
        this.name = name;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Storage storage = (Storage) o;
        return Objects.equals(name, storage.name) &&
            userId == storage.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, userId);
    }
}

