package org.proj4.tennis_scoreboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MatchDto {
    private int id;
    private String firstPlayerName;
    private String secondPlayerName;
    private String winnerName;
}
