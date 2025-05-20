package org.proj4.tennis_scoreboard.entity;

import lombok.Data;

@Data
public class MatchScore {
    private int setsFirstPlayer;
    private int gamesFirstPlayer;
    private Point pointsFirstPlayer;

    private int setsSecondPlayer;
    private int gamesSecondPlayer;
    private Point pointsSecondPlayer;

    private int tieBreakFirstPlayer;
    private int tieBreakSecondPlayer;

}
