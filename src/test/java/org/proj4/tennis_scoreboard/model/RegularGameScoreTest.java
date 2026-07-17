package org.proj4.tennis_scoreboard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.proj4.tennis_scoreboard.dto.GameScoreDto;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class RegularGameScoreTest {

    private RegularGameScore regularGameScore;

    @BeforeEach
    void setUp() {
        regularGameScore = new RegularGameScore();
    }

    @Test
    @DisplayName("Increment from 0 to 15 for the first player")
    void incrementPointFromLoveToFifteenToFirstPlayer() {
        regularGameScore.addPointFirstPlayer();
        GameScoreDto gameScoreDto = regularGameScore.getGameScoreDto();

        assertAll(
                () -> assertThat(gameScoreDto.firstPlayerScore()).isEqualTo("15"),
                () -> assertThat(regularGameScore.getStatus()).isEqualTo(GameStatus.ONGOING)
        );
    }

    @Test
    @DisplayName("Increment from 0 to 15 for the second player")
    void incrementPointFromLoveToFifteenToSecondPlayer() {
        regularGameScore.addPointSecondPlayer();
        GameScoreDto gameScoreDto = regularGameScore.getGameScoreDto();

        assertAll(
                () -> assertThat(gameScoreDto.secondPlayerScore()).isEqualTo("15"),
                () -> assertThat(regularGameScore.getStatus()).isEqualTo(GameStatus.ONGOING)
        );
    }

    @Test
    @DisplayName("Increment from 0 to 30 for the first player")
    void incrementPointFromLoveToThirtyToFirstPlayer() {
        regularGameScore.addPointFirstPlayer();
        regularGameScore.addPointFirstPlayer();
        GameScoreDto gameScoreDto = regularGameScore.getGameScoreDto();

        assertAll(
                () -> assertThat(gameScoreDto.firstPlayerScore()).isEqualTo("30"),
                () -> assertThat(regularGameScore.getStatus()).isEqualTo(GameStatus.ONGOING)
        );
    }

    @Test
    @DisplayName("Increment from 0 to 30 for the second player")
    void incrementPointFromLoveToThirtyToSecondPlayer() {
        regularGameScore.addPointSecondPlayer();
        regularGameScore.addPointSecondPlayer();
        GameScoreDto gameScoreDto = regularGameScore.getGameScoreDto();

        assertAll(
                () -> assertThat(gameScoreDto.secondPlayerScore()).isEqualTo("30"),
                () -> assertThat(regularGameScore.getStatus()).isEqualTo(GameStatus.ONGOING)
        );
    }

    @Test
    @DisplayName("Increment from 0 to 40 for the first player")
    void incrementPointFromLoveToFortyToFirstPlayer() {
        regularGameScore.addPointFirstPlayer();
        regularGameScore.addPointFirstPlayer();
        regularGameScore.addPointFirstPlayer();
        GameScoreDto gameScoreDto = regularGameScore.getGameScoreDto();

        assertAll(
                () -> assertThat(gameScoreDto.firstPlayerScore()).isEqualTo("40"),
                () -> assertThat(regularGameScore.getStatus()).isEqualTo(GameStatus.ONGOING)
        );
    }

    @Test
    @DisplayName("Increment from 0 to 40 for the second player")
    void incrementPointFromLoveToFortyToSecondPlayer() {
        regularGameScore.addPointSecondPlayer();
        regularGameScore.addPointSecondPlayer();
        regularGameScore.addPointSecondPlayer();
        GameScoreDto gameScoreDto = regularGameScore.getGameScoreDto();

        assertAll(
                () -> assertThat(gameScoreDto.secondPlayerScore()).isEqualTo("40"),
                () -> assertThat(regularGameScore.getStatus()).isEqualTo(GameStatus.ONGOING)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getArgumentsForParametersFirstPlayerWonWithDifferentScoresTest")
    @DisplayName("First player won with different score")
    void firstPlayerWonWithDifferentScores(String description, int numberAddPointFirstPlayer, int numberAddPointSecondPlayer, String secondPlayerPoints) {

        for (int i = 0; i < numberAddPointSecondPlayer; i++) {
            regularGameScore.addPointSecondPlayer();
        }

        for (int i = 0; i < numberAddPointFirstPlayer; i++) {
            regularGameScore.addPointFirstPlayer();
        }

        GameScoreDto gameScoreDto = regularGameScore.getGameScoreDto();

        assertAll(
                () -> assertThat(gameScoreDto.firstPlayerScore()).isEqualTo("40"),
                () -> assertThat(gameScoreDto.secondPlayerScore()).isEqualTo(secondPlayerPoints),
                () -> assertThat(regularGameScore.getStatus()).isEqualTo(GameStatus.FIRST_PLAYER_WON)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getArgumentsForParametersSecondPlayerWonWithDifferentScoresTest")
    @DisplayName("Second player won with different score")
    void secondPlayerWonWithDifferentScores(String description, int numberAddPointFirstPlayer, int numberAddPointSecondPlayer, String firstPlayerPoints) {

        for (int i = 0; i < numberAddPointFirstPlayer; i++) {
            regularGameScore.addPointFirstPlayer();
        }

        for (int i = 0; i < numberAddPointSecondPlayer; i++) {
            regularGameScore.addPointSecondPlayer();
        }

        GameScoreDto gameScoreDto = regularGameScore.getGameScoreDto();

        assertAll(
                () -> assertThat(gameScoreDto.secondPlayerScore()).isEqualTo("40"),
                () -> assertThat(gameScoreDto.firstPlayerScore()).isEqualTo(firstPlayerPoints),
                () -> assertThat(regularGameScore.getStatus()).isEqualTo(GameStatus.SECOND_PLAYER_WON)
        );
    }

    @Test
    @DisplayName("First player from deuce to advantage (40:40 → AD:40)")
    void firstPlayerFromDeuceToAdvantage() {
        makeDeuce();

        regularGameScore.addPointFirstPlayer();

        GameScoreDto gameScoreDto = regularGameScore.getGameScoreDto();
        assertAll(
                () -> assertThat(gameScoreDto.firstPlayerScore()).isEqualTo("AD"),
                () -> assertThat(gameScoreDto.secondPlayerScore()).isEqualTo("40"),
                () -> assertThat(regularGameScore.getStatus()).isEqualTo(GameStatus.ONGOING)
        );
    }

    @Test
    @DisplayName("Second player from deuce to advantage (40:40 → 40:AD)")
    void secondPlayerFromDeuceToAdvantage() {
        makeDeuce();

        regularGameScore.addPointSecondPlayer();

        GameScoreDto gameScoreDto = regularGameScore.getGameScoreDto();
        assertAll(
                () -> assertThat(gameScoreDto.secondPlayerScore()).isEqualTo("AD"),
                () -> assertThat(gameScoreDto.firstPlayerScore()).isEqualTo("40"),
                () -> assertThat(regularGameScore.getStatus()).isEqualTo(GameStatus.ONGOING)
        );
    }

    @Test
    @DisplayName("First player back to deuce from advantage (AD:40 → 40:40)")
    void firstPlayerFromAdvantageToDeuce() {
        makeDeuce();

        regularGameScore.addPointFirstPlayer();
        regularGameScore.addPointSecondPlayer();

        GameScoreDto gameScoreDto = regularGameScore.getGameScoreDto();
        assertAll(
                () -> assertThat(gameScoreDto.firstPlayerScore()).isEqualTo("40"),
                () -> assertThat(gameScoreDto.secondPlayerScore()).isEqualTo("40")
        );
    }

    @Test
    @DisplayName("Second player back to deuce from advantage (40:AD → 40:40)")
    void secondPlayerFromAdvantageToDeuce() {
        makeDeuce();

        regularGameScore.addPointSecondPlayer();
        regularGameScore.addPointFirstPlayer();

        GameScoreDto gameScoreDto = regularGameScore.getGameScoreDto();
        assertAll(
                () -> assertThat(gameScoreDto.secondPlayerScore()).isEqualTo("40"),
                () -> assertThat(gameScoreDto.firstPlayerScore()).isEqualTo("40")
        );
    }

    @Test
    @DisplayName("First player won from advantage AD:40")
    void firstPlayerWonWhenHasAdvantage() {
        makeDeuce();

        regularGameScore.addPointFirstPlayer();
        regularGameScore.addPointFirstPlayer();

        assertThat(regularGameScore.getStatus()).isEqualTo(GameStatus.FIRST_PLAYER_WON);
    }

    @Test
    @DisplayName("Second player won from advantage 40:AD")
    void secondPlayerWonWhenHasAdvantage() {
        makeDeuce();

        regularGameScore.addPointSecondPlayer();
        regularGameScore.addPointSecondPlayer();

        assertThat(regularGameScore.getStatus()).isEqualTo(GameStatus.SECOND_PLAYER_WON);
    }

    private void makeDeuce() {
        regularGameScore.addPointFirstPlayer();
        regularGameScore.addPointFirstPlayer();
        regularGameScore.addPointFirstPlayer();

        regularGameScore.addPointSecondPlayer();
        regularGameScore.addPointSecondPlayer();
        regularGameScore.addPointSecondPlayer();
    }

    static Stream<Arguments> getArgumentsForParametersFirstPlayerWonWithDifferentScoresTest() {
        return Stream.of(
                Arguments.of("First player won if add point when score is 40:0", 4, 0, "0"),
                Arguments.of("First player won if add point when score is 40:15", 4, 1, "15"),
                Arguments.of("First player won if add point when score is 40:30", 4, 2, "30")
        );
    }

    static Stream<Arguments> getArgumentsForParametersSecondPlayerWonWithDifferentScoresTest() {
        return Stream.of(
                Arguments.of("Second player won if add point when score is 0:40", 0, 4, "0"),
                Arguments.of("Second player won if add point when score is 40:15", 1, 4, "15"),
                Arguments.of("Second player won if add point when score is 40:30", 2, 4, "30")
        );
    }
}
