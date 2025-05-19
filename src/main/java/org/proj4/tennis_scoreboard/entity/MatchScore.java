package org.proj4.tennis_scoreboard.entity;

import lombok.Data;

@Data
public class MatchScore {
    private Match match;
    private int sets;
    private int games;
    private int points;

}
