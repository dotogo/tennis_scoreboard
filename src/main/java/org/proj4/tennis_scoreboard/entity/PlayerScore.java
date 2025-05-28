package org.proj4.tennis_scoreboard.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlayerScore {
    private int sets;
    private int games;
    private Point points = Point.LOVE;
    private int tieBreak;
}
