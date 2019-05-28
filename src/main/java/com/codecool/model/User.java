package com.codecool.model;

import java.util.Objects;

public final class User extends AbstractModel {

    private final String email;
    private final String password;
    private final boolean admin;

    public User(int id, String email, String password, boolean admin) {
        super(id);
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public boolean isAdmin() {
        return admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
            Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, password);
    }
}
