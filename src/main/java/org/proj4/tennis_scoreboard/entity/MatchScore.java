package org.proj4.tennis_scoreboard.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MatchScore {
    private int setsFirstPlayer;
    private int gamesFirstPlayer;
    private Point pointsFirstPlayer = Point.ZERO;

    private int setsSecondPlayer;
    private int gamesSecondPlayer;
    private Point pointsSecondPlayer = Point.ZERO;

    private int tieBreakFirstPlayer;
    private int tieBreakSecondPlayer;

}
