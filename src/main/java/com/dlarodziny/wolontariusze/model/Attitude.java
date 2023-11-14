package com.dlarodziny.wolontariusze.model;

public enum Attitude {
    ZIMNY("Zimny", 0),
    CHLODNY("Chłodny", 1),
    CIEPLY("Ciepły", 2),
    GORACY("Gorący", 3),
    AKTYWNY("Aktywny", 4),
    UTRACONY("Utracony", 5),
    NIEPODANO("nie podano", 6)
    ;

    private final String attitude;
    private final int number;

    Attitude(String attitude, int number) {
        this.attitude = attitude;
        this.number = number;
    }

    public String getAttitude() {
        return this.attitude;
    }
    public int getNumber() {
        return this.number;
    }

    public static Attitude findByValue(String value) {
        Attitude result = null;
        for (Attitude attitude : values()) {
            if (value.toLowerCase().contains(attitude.getAttitude().toLowerCase())) {
                result = attitude;
                break;
            }
        }
        return result;
    }
}
