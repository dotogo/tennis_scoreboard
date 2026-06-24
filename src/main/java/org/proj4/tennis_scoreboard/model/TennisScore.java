package org.proj4.tennis_scoreboard.model;

public interface TennisScore {

    void addPointFirstPlayer();

    void addPointSecondPlayer();

    GameStatus getStatus();
}
