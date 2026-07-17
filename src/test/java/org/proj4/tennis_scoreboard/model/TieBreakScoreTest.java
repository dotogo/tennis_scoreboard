package org.proj4.tennis_scoreboard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.proj4.tennis_scoreboard.dto.GameScoreDto;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TieBreakScoreTest {
    private static final int MIN_TIEBREAK_POINTS = 6;

    private TieBreakScore tiebreakScore;

    @BeforeEach
    public void setUp() {
        tiebreakScore = new TieBreakScore();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getArgumentsForParametersTieBreakOngoingWithDifferentScoresTest")
    @DisplayName("Tie Break is ongoing with different scores before 6:6")
    void tieBreakOngoingWithDifferentScores(String description, int numberAddPointFirstPlayer, int numberAddPointSecondPlayer) {

        for (int i = 0; i < numberAddPointSecondPlayer; i++) {
            tiebreakScore.addPointSecondPlayer();
        }

        for (int i = 0; i < numberAddPointFirstPlayer; i++) {
            tiebreakScore.addPointFirstPlayer();
        }

        GameScoreDto gameScoreDto = tiebreakScore.getGameScoreDto();

        assertAll(
                () -> assertThat(gameScoreDto.firstPlayerScore()).isEqualTo(String.valueOf(numberAddPointFirstPlayer)),
                () -> assertThat(gameScoreDto.secondPlayerScore()).isEqualTo(String.valueOf(numberAddPointSecondPlayer)),
                () -> assertThat(tiebreakScore.getStatus()).isEqualTo(GameStatus.ONGOING)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getArgumentsForParametersOngoingWithDifferenceInOnePointAfterScoreSixSixTest")
    @DisplayName("Tie Break is ongoing with difference in 1 point after score 6:6")
    void ongoingWithDifferenceInOnePointAfterScoreSixSix(String description, int firstPoints, int secondPoints) {

        for (int i = 0; i < MIN_TIEBREAK_POINTS; i++) {
            tiebreakScore.addPointFirstPlayer();
            tiebreakScore.addPointSecondPlayer();
        }

        if (firstPoints < secondPoints) {
            for (int i = 0; i < firstPoints; i++) {
                tiebreakScore.addPointFirstPlayer();
                tiebreakScore.addPointSecondPlayer();
            }
            tiebreakScore.addPointSecondPlayer();

        } else if (secondPoints < firstPoints) {

            for (int i = 0; i < secondPoints; i++) {
                tiebreakScore.addPointSecondPlayer();
                tiebreakScore.addPointFirstPlayer();
            }
            tiebreakScore.addPointFirstPlayer();

        } else {

            for (int i = 0; i < secondPoints; i++) {
                tiebreakScore.addPointSecondPlayer();
                tiebreakScore.addPointFirstPlayer();
            }
        }

        GameScoreDto gameScoreDto = tiebreakScore.getGameScoreDto();

        String firstPlayerPoints = String.valueOf(MIN_TIEBREAK_POINTS + firstPoints);
        String secondPlayerPoints = String.valueOf(MIN_TIEBREAK_POINTS + secondPoints);

        assertAll(
                () -> assertThat(gameScoreDto.firstPlayerScore()).isEqualTo(firstPlayerPoints),
                () -> assertThat(gameScoreDto.secondPlayerScore()).isEqualTo(secondPlayerPoints),
                () -> assertThat(tiebreakScore.getStatus()).isEqualTo(GameStatus.ONGOING)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getArgumentsForParametersFirstPlayerHasSevenPointsAndWonTest")
    @DisplayName("First player has 7 points in Tie Break and won")
    void firstPlayerHasSevenPointsAndWon(String description, int firstPoints, int secondPoints) {

        for (int i = 0; i < secondPoints; i++) {
            tiebreakScore.addPointSecondPlayer();
        }

        for (int i = 0; i < firstPoints; i++) {
            tiebreakScore.addPointFirstPlayer();
        }

        GameScoreDto gameScoreDto = tiebreakScore.getGameScoreDto();

        assertAll(
                () -> assertThat(gameScoreDto.firstPlayerScore()).isEqualTo(String.valueOf(firstPoints)),
                () -> assertThat(gameScoreDto.secondPlayerScore()).isEqualTo(String.valueOf(secondPoints)),
                () -> assertThat(tiebreakScore.getStatus()).isEqualTo(GameStatus.FIRST_PLAYER_WON)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getArgumentsForParametersSecondPlayerHasSevenPointsAndWonTest")
    @DisplayName("Second player has 7 points in Tie Break and won")
    void secondPlayerHasSevenPointsAndWon(String description, int firstPoints, int secondPoints) {

        for (int i = 0; i < firstPoints; i++) {
            tiebreakScore.addPointFirstPlayer();
        }

        for (int i = 0; i < secondPoints; i++) {
            tiebreakScore.addPointSecondPlayer();
        }

        GameScoreDto gameScoreDto = tiebreakScore.getGameScoreDto();

        assertAll(
                () -> assertThat(gameScoreDto.firstPlayerScore()).isEqualTo(String.valueOf(firstPoints)),
                () -> assertThat(gameScoreDto.secondPlayerScore()).isEqualTo(String.valueOf(secondPoints)),
                () -> assertThat(tiebreakScore.getStatus()).isEqualTo(GameStatus.SECOND_PLAYER_WON)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getArgumentsForParametersFirstPlayerHasTwoPointsDifferenceAfterSevenPointsAndWonTest")
    @DisplayName("First player has 2 point difference after 7 points in Tie Break and won")
    void firstPlayerHasTwoPointsDifferenceAfterSevenPointsAndWon(String description, int firstPointsAdd, int secondPointsAdd) {

        for (int i = 0; i < MIN_TIEBREAK_POINTS + secondPointsAdd; i++) {
            tiebreakScore.addPointFirstPlayer();
            tiebreakScore.addPointSecondPlayer();
        }

        tiebreakScore.addPointFirstPlayer();
        tiebreakScore.addPointFirstPlayer();

        GameScoreDto gameScoreDto = tiebreakScore.getGameScoreDto();

        assertAll(
                () -> assertThat(gameScoreDto.firstPlayerScore()).isEqualTo(String.valueOf(MIN_TIEBREAK_POINTS + firstPointsAdd)),
                () -> assertThat(gameScoreDto.secondPlayerScore()).isEqualTo(String.valueOf(MIN_TIEBREAK_POINTS + secondPointsAdd)),
                () -> assertThat(tiebreakScore.getStatus()).isEqualTo(GameStatus.FIRST_PLAYER_WON)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getArgumentsForParametersSecondPlayerHasTwoPointsDifferenceAfterSevenPointsAndWonTest")
    @DisplayName("Second player has 2 point difference after 7 points in Tie Break and won")
    void secondPlayerHasTwoPointsDifferenceAfterSevenPointsAndWon(String description, int firstPointsAdd, int secondPointsAdd) {

        for (int i = 0; i < MIN_TIEBREAK_POINTS + firstPointsAdd; i++) {
            tiebreakScore.addPointFirstPlayer();
            tiebreakScore.addPointSecondPlayer();
        }

        tiebreakScore.addPointSecondPlayer();
        tiebreakScore.addPointSecondPlayer();

        GameScoreDto gameScoreDto = tiebreakScore.getGameScoreDto();

        assertAll(
                () -> assertThat(gameScoreDto.firstPlayerScore()).isEqualTo(String.valueOf(MIN_TIEBREAK_POINTS + firstPointsAdd)),
                () -> assertThat(gameScoreDto.secondPlayerScore()).isEqualTo(String.valueOf(MIN_TIEBREAK_POINTS + secondPointsAdd)),
                () -> assertThat(tiebreakScore.getStatus()).isEqualTo(GameStatus.SECOND_PLAYER_WON)
        );
    }

    static Stream<Arguments> getArgumentsForParametersTieBreakOngoingWithDifferentScoresTest() {
        Stream.Builder<Arguments> builder = Stream.builder();

        for (int firstPlayerPoints = 0; firstPlayerPoints <= MIN_TIEBREAK_POINTS; firstPlayerPoints++) {
            for (int secondPlayerPoints = 0; secondPlayerPoints <= MIN_TIEBREAK_POINTS; secondPlayerPoints++) {
                builder.add(Arguments.of(
                        String.format("Tie Break is ongoing if score is %d:%d", firstPlayerPoints, secondPlayerPoints),
                        firstPlayerPoints,
                        secondPlayerPoints
                ));
            }
        }
        return builder.build();
    }

    static Stream<Arguments> getArgumentsForParametersOngoingWithDifferenceInOnePointAfterScoreSixSixTest() {
        Stream.Builder<Arguments> builder = Stream.builder();
        int pointAfterMinTieBreakPoints = 10;

        for (int i = 0; i < pointAfterMinTieBreakPoints; i++) {
            int[][] pointOffsets = {
                    {i + 1, i},
                    {i, i + 1},
                    {i, i}
            };

            for (int[] offset : pointOffsets) {
                int firstAddPoints = offset[0];
                int secondAddPoints = offset[1];

                builder.add(Arguments.of(
                        String.format("Tie Break is ongoing if score is %d:%d", firstAddPoints + MIN_TIEBREAK_POINTS, secondAddPoints + MIN_TIEBREAK_POINTS),
                        firstAddPoints, secondAddPoints
                ));
            }
        }
        return builder.build();
    }

    static Stream<Arguments> getArgumentsForParametersFirstPlayerHasSevenPointsAndWonTest() {
        return Stream.of(
                Arguments.of("First player has won, score is 7:0", 7, 0),
                Arguments.of("First player has won, score is 7:1", 7, 1),
                Arguments.of("First player has won, score is 7:2", 7, 2),
                Arguments.of("First player has won, score is 7:3", 7, 3),
                Arguments.of("First player has won, score is 7:4", 7, 4),
                Arguments.of("First player has won, score is 7:5", 7, 5)
        );
    }

    static Stream<Arguments> getArgumentsForParametersSecondPlayerHasSevenPointsAndWonTest() {
        return Stream.of(
                Arguments.of("Second player has won, score is 0:7", 0, 7),
                Arguments.of("Second player has won, score is 1:7", 1, 7),
                Arguments.of("Second player has won, score is 2:7", 2, 7),
                Arguments.of("Second player has won, score is 3:7", 3, 7),
                Arguments.of("Second player has won, score is 4:7", 4, 7),
                Arguments.of("Second player has won, score is 5:7", 5, 7)
        );
    }

    static Stream<Arguments> getArgumentsForParametersFirstPlayerHasTwoPointsDifferenceAfterSevenPointsAndWonTest() {
        return Stream.of(
                Arguments.of("First player has won, score is 8:6", 2, 0),
                Arguments.of("First player has won, score is 9:7", 3, 1),
                Arguments.of("First player has won, score is 10:8", 4, 2),
                Arguments.of("First player has won, score is 11:9", 5, 3),
                Arguments.of("First player has won, score is 12:10", 6, 4),
                Arguments.of("First player has won, score is 13:11", 7, 5)
        );
    }

    static Stream<Arguments> getArgumentsForParametersSecondPlayerHasTwoPointsDifferenceAfterSevenPointsAndWonTest() {
        return Stream.of(
                Arguments.of("Second player has won, score is 6:8", 0, 2),
                Arguments.of("Second player has won, score is 7:9", 1, 3),
                Arguments.of("Second player has won, score is 8:10", 2, 4),
                Arguments.of("Second player has won, score is 9:11", 3, 5),
                Arguments.of("Second player has won, score is 10:12", 4, 6),
                Arguments.of("Second player has won, score is 11:13", 5, 7)
        );
    }
}
