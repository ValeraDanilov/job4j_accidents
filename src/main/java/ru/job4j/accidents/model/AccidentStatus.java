package ru.job4j.accidents.model;

public enum AccidentStatus {

    DONE("Done"), REJECTED("Rejected"), ACCEPTED("Accepted");

    private String value;

    AccidentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
