package org.proj4.tennis_scoreboard.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OngoingMatch {
    private Player firstPlayer;
    private Player secondPlayer;
    private MatchScore matchScore;
}
