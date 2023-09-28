package com.dlarodziny.wolontariusze.model;

public enum Attitude {
    ZIMNY("Zimny"),
    CHLODNY("Chłodny"),
    CIEPLY("Ciepły"),
    GORACY("Gorący");

    private final String attitude;

    Attitude(String attitude) {
        this.attitude = attitude;
    }

    public String getAttitude() {
        return this.attitude;
    }
}
