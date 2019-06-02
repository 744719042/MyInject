package com.example.injection;

import java.util.Objects;

public class Key {
    private Class<?> clazz;
    private Name name;

    public Key(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Key(Class<?> clazz, Name name) {
        this.clazz = clazz;
        this.name = name;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        if (clazz == null) {
            return false;
        }

        if (clazz != key.clazz) {
            return false;
        }

        if (name != key.name) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz, name);
    }
}
