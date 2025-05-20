package org.proj4.tennis_scoreboard.entity;

public enum Point {
    FIFTEEN("15"),
    THIRTY("30"),
    FORTY("40"),
    AD("AD");

    public final String value;

    Point(String value) {
        this.value = value;
    }
}
