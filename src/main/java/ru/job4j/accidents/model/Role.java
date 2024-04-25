package ru.job4j.accidents.model;

public enum Role {

    ADMIN("Admin"), USER("User");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
