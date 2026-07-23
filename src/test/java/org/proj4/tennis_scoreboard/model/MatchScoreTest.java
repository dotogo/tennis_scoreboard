package org.proj4.tennis_scoreboard.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.proj4.tennis_scoreboard.dto.MatchScoreDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MatchScoreTest {

    private final MatchScore matchScore;

    public MatchScoreTest() {
        matchScore = new MatchScore();
    }

    @Test
    @DisplayName("First player won match, score 2:0")
    void firstPlayerWonMatchScoreTwoZero() {
        winSetFirstPlayer();
        winSetFirstPlayer();

        MatchScoreDto matchScoreDto = matchScore.getMatchScoreDto();

        assertAll(
                () -> assertThat(matchScore.getStatus()).isEqualTo(GameStatus.FIRST_PLAYER_WON),
                () -> assertThat(matchScoreDto.getFirstPlayerSets()).isEqualTo(String.valueOf(2)),
                () -> assertThat(matchScoreDto.getSecondPlayerSets()).isEqualTo(String.valueOf(0))
        );
    }

    @Test
    @DisplayName("Second player won match, score 0:2")
    void secondPlayerWonMatchScoreZeroTwo() {
        winSetSecondPlayer();
        winSetSecondPlayer();

        MatchScoreDto matchScoreDto = matchScore.getMatchScoreDto();

        assertAll(
                () -> assertThat(matchScore.getStatus()).isEqualTo(GameStatus.SECOND_PLAYER_WON),
                () -> assertThat(matchScoreDto.getFirstPlayerSets()).isEqualTo(String.valueOf(0)),
                () -> assertThat(matchScoreDto.getSecondPlayerSets()).isEqualTo(String.valueOf(2))
        );
    }

    @Test
    @DisplayName("First player won match, score 2:1")
    void firstPlayerWonMatchScoreTwoOne() {
        winSetFirstPlayer();
        winSetSecondPlayer();
        winSetFirstPlayer();

        MatchScoreDto matchScoreDto = matchScore.getMatchScoreDto();

        assertAll(
                () -> assertThat(matchScore.getStatus()).isEqualTo(GameStatus.FIRST_PLAYER_WON),
                () -> assertThat(matchScoreDto.getFirstPlayerSets()).isEqualTo(String.valueOf(2)),
                () -> assertThat(matchScoreDto.getSecondPlayerSets()).isEqualTo(String.valueOf(1))
        );
    }

    @Test
    @DisplayName("Second player won match, score 1:2")
    void secondPlayerWonMatchScoreOneTwo() {
        winSetSecondPlayer();
        winSetFirstPlayer();
        winSetSecondPlayer();

        MatchScoreDto matchScoreDto = matchScore.getMatchScoreDto();

        assertAll(
                () -> assertThat(matchScore.getStatus()).isEqualTo(GameStatus.SECOND_PLAYER_WON),
                () -> assertThat(matchScoreDto.getFirstPlayerSets()).isEqualTo(String.valueOf(1)),
                () -> assertThat(matchScoreDto.getSecondPlayerSets()).isEqualTo(String.valueOf(2))
        );
    }

    @Test
    @DisplayName("Match is ongoing, score 1:1")
    void matchIsOngoingScoreOneOne() {
        winSetFirstPlayer();
        winSetSecondPlayer();

        MatchScoreDto matchScoreDto = matchScore.getMatchScoreDto();

        assertAll(
                () -> assertThat(matchScore.getStatus()).isEqualTo(GameStatus.ONGOING),
                () -> assertThat(matchScoreDto.getFirstPlayerSets()).isEqualTo(String.valueOf(1)),
                () -> assertThat(matchScoreDto.getSecondPlayerSets()).isEqualTo(String.valueOf(1))
        );
    }

    @Test
    @DisplayName("Match is ongoing, score 0:0")
    void matchIsOngoingScoreZeroZero() {
        MatchScoreDto matchScoreDto = matchScore.getMatchScoreDto();

        assertAll(
                () -> assertThat(matchScore.getStatus()).isEqualTo(GameStatus.ONGOING),
                () -> assertThat(matchScoreDto.getFirstPlayerSets()).isEqualTo(String.valueOf(0)),
                () -> assertThat(matchScoreDto.getSecondPlayerSets()).isEqualTo(String.valueOf(0))
        );
    }

    private void winSetFirstPlayer() {
        for (int i = 0; i < 6; i++) {
            winGameFirstPlayer();
        }
    }

    private void winSetSecondPlayer() {
        for (int i = 0; i < 6; i++) {
            winGameSecondPlayer();
        }
    }

    private void winGameFirstPlayer() {
        for (int i = 0; i < 4; i++) {
            matchScore.addPointFirstPlayer();
        }
    }

    private void winGameSecondPlayer() {
        for (int i = 0; i < 4; i++) {
            matchScore.addPointSecondPlayer();
        }
    }
}
