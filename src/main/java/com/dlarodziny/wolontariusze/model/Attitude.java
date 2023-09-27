package com.dlarodziny.wolontariusze.model;

public enum Attitude {
    ZIMNY("Zimny"),
    CHLODNY("Chłodny"),
    CIEPLY("Ciepły"),
    GORACY("Gorący");

    private final String name;

    Attitude(String name) {
        this.name = name;
    }
}
