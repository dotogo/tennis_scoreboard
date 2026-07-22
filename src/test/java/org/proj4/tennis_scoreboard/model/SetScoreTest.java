package org.proj4.tennis_scoreboard.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.proj4.tennis_scoreboard.dto.SetScoreDto;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class SetScoreTest {

    private static final int TIE_BREAK_MIN = 6;
    private final SetScore setScore;

    public SetScoreTest() {
        setScore = new SetScore();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getArgumentsForParametersFirstPlayerWonInSixGamesAndMinAdvantageTwoGamesTest")
    @DisplayName("First player won set: 6 games with min advantage 2 games")
    void firstPlayerWonInSixGamesAndMinAdvantageTwoGames(String description, int secondGamesWon) {
        int firstGamesWon = 6;

        for (int i = 0; i < secondGamesWon; i++) {
            winGameSecondPlayer();
        }

        for (int i = 0; i < firstGamesWon; i++) {
            winGameFirstPlayer();
        }

        SetScoreDto setScoreDto = setScore.getSetScoreDto();

        assertAll(
                () -> assertThat(setScoreDto.firstPlayerGames()).isEqualTo(String.valueOf(firstGamesWon)),
                () -> assertThat(setScoreDto.secondPlayerGames()).isEqualTo(String.valueOf(secondGamesWon)),
                () -> assertThat(setScore.getStatus()).isEqualTo(GameStatus.FIRST_PLAYER_WON)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getArgumentsForParametersSecondPlayerWonInSixGamesAndMinAdvantageTwoGamesTest")
    @DisplayName("Second player won set: 6 games with min advantage 2 games")
    void secondPlayerWonInSixGamesAndMinAdvantageTwoGames(String description, int firstGamesWon) {
        int secondGamesWon = 6;

        for (int i = 0; i < firstGamesWon; i++) {
            winGameFirstPlayer();
        }

        for (int i = 0; i < secondGamesWon; i++) {
            winGameSecondPlayer();
        }

        SetScoreDto setScoreDto = setScore.getSetScoreDto();

        assertAll(
                () -> assertThat(setScoreDto.firstPlayerGames()).isEqualTo(String.valueOf(firstGamesWon)),
                () -> assertThat(setScoreDto.secondPlayerGames()).isEqualTo(String.valueOf(secondGamesWon)),
                () -> assertThat(setScore.getStatus()).isEqualTo(GameStatus.SECOND_PLAYER_WON)
        );
    }

    @Test
    @DisplayName("First player won set, score 7:5")
    void firstPlayerWonScoreSevenFive() {
        for (int i = 0; i < 5; i++) {
            winGameFirstPlayer();
            winGameSecondPlayer();
        }

        winGameFirstPlayer();
        winGameFirstPlayer();

        SetScoreDto setScoreDto = setScore.getSetScoreDto();

        assertAll(
                () -> assertThat(setScoreDto.firstPlayerGames()).isEqualTo(String.valueOf(7)),
                () -> assertThat(setScoreDto.secondPlayerGames()).isEqualTo(String.valueOf(5)),
                () -> assertThat(setScore.getStatus()).isEqualTo(GameStatus.FIRST_PLAYER_WON)
        );
    }

    @Test
    @DisplayName("Second player won set, score 5:7")
    void secondPlayerWonScoreFiveSeven() {
        for (int i = 0; i < 5; i++) {
            winGameFirstPlayer();
            winGameSecondPlayer();
        }

        winGameSecondPlayer();
        winGameSecondPlayer();

        SetScoreDto setScoreDto = setScore.getSetScoreDto();

        assertAll(
                () -> assertThat(setScoreDto.firstPlayerGames()).isEqualTo(String.valueOf(5)),
                () -> assertThat(setScoreDto.secondPlayerGames()).isEqualTo(String.valueOf(7)),
                () -> assertThat(setScore.getStatus()).isEqualTo(GameStatus.SECOND_PLAYER_WON)
        );
    }

    @Test
    @DisplayName("The tiebreak is starting when score 6:6")
    void tiebreakIsStaringWhenScoreSixSix() {
        makeScoreGamesSixSix();

        SetScoreDto setScoreDto = setScore.getSetScoreDto();

        assertAll(
                () -> assertThat(setScoreDto.tieBreak()).isTrue(),
                () -> assertThat(setScore.getStatus()).isEqualTo(GameStatus.ONGOING),
                () -> assertThat(setScoreDto.firstPlayerGames()).isEqualTo(String.valueOf(6)),
                () -> assertThat(setScoreDto.secondPlayerGames()).isEqualTo(String.valueOf(6))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getArgumentsForParametersFirstPlayerWonSetInTieBreakTest")
    @DisplayName("The first player won the set in a tiebreak")
    void firstPlayerWonSetInTieBreak(String description, int pointsAfterTieBreakMin) {
        makeScoreGamesSixSix();

        for (int i = 0; i < TIE_BREAK_MIN; i++) {
            setScore.addPointFirstPlayer();
            setScore.addPointSecondPlayer();
        }

        for (int i = 0; i < pointsAfterTieBreakMin; i++) {
            setScore.addPointFirstPlayer();
            setScore.addPointSecondPlayer();
        }

        setScore.addPointFirstPlayer();
        setScore.addPointFirstPlayer();

        SetScoreDto setScoreDto = setScore.getSetScoreDto();

        assertAll(
                () -> assertThat(setScoreDto.tieBreak()).isTrue(),
                () -> assertThat(setScore.getStatus()).isEqualTo(GameStatus.FIRST_PLAYER_WON),
                () -> assertThat(setScoreDto.firstPlayerGames()).isEqualTo(String.valueOf(6)),
                () -> assertThat(setScoreDto.secondPlayerGames()).isEqualTo(String.valueOf(6)),
                () -> assertThat(setScoreDto.gameScoreDto().firstPlayerScore()).isEqualTo(String.valueOf(TIE_BREAK_MIN + pointsAfterTieBreakMin + 2)),
                () -> assertThat(setScoreDto.gameScoreDto().secondPlayerScore()).isEqualTo(String.valueOf(TIE_BREAK_MIN + pointsAfterTieBreakMin))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getArgumentsForParametersSecondPlayerWonSetInTieBreakTest")
    @DisplayName("The second player won the set in a tiebreak")
    void secondPlayerWonSetInTieBreak(String description, int pointsAfterTieBreakMin) {
        makeScoreGamesSixSix();

        for (int i = 0; i < TIE_BREAK_MIN; i++) {
            setScore.addPointFirstPlayer();
            setScore.addPointSecondPlayer();
        }

        for (int i = 0; i < pointsAfterTieBreakMin; i++) {
            setScore.addPointFirstPlayer();
            setScore.addPointSecondPlayer();
        }

        setScore.addPointSecondPlayer();
        setScore.addPointSecondPlayer();

        SetScoreDto setScoreDto = setScore.getSetScoreDto();

        assertAll(
                () -> assertThat(setScoreDto.tieBreak()).isTrue(),
                () -> assertThat(setScore.getStatus()).isEqualTo(GameStatus.SECOND_PLAYER_WON),
                () -> assertThat(setScoreDto.firstPlayerGames()).isEqualTo(String.valueOf(6)),
                () -> assertThat(setScoreDto.secondPlayerGames()).isEqualTo(String.valueOf(6)),
                () -> assertThat(setScoreDto.gameScoreDto().firstPlayerScore()).isEqualTo(String.valueOf(TIE_BREAK_MIN + pointsAfterTieBreakMin)),
                () -> assertThat(setScoreDto.gameScoreDto().secondPlayerScore()).isEqualTo(String.valueOf(TIE_BREAK_MIN + pointsAfterTieBreakMin + 2))
        );
    }

    private void makeScoreGamesSixSix() {
        for (int i = 0; i < 5; i++) {
            winGameFirstPlayer();
            winGameSecondPlayer();
        }

        winGameFirstPlayer();
        winGameSecondPlayer();
    }

    private void winGameFirstPlayer() {
        for (int i = 0; i < 4; i++) {
            setScore.addPointFirstPlayer();
        }
    }

    private void winGameSecondPlayer() {
        for (int i = 0; i < 4; i++) {
            setScore.addPointSecondPlayer();
        }
    }

    static Stream<Arguments> getArgumentsForParametersFirstPlayerWonInSixGamesAndMinAdvantageTwoGamesTest() {
        Stream.Builder<Arguments> builder = Stream.builder();

        for (int secondPlayerGames = 0; secondPlayerGames < 5; secondPlayerGames++) {
            builder.add(Arguments.of(
                    String.format("First player won set, game score is 6:%d", secondPlayerGames), secondPlayerGames));
        }
        return builder.build();
    }

    static Stream<Arguments> getArgumentsForParametersSecondPlayerWonInSixGamesAndMinAdvantageTwoGamesTest() {
        Stream.Builder<Arguments> builder = Stream.builder();

        for (int firstPlayerGames = 0; firstPlayerGames < 5; firstPlayerGames++) {
            builder.add(Arguments.of(
                    String.format("Second player won set, game score is %d:6", firstPlayerGames), firstPlayerGames));
        }
        return builder.build();
    }

    static Stream<Arguments> getArgumentsForParametersFirstPlayerWonSetInTieBreakTest() {
        Stream.Builder<Arguments> builder = Stream.builder();

        for (int pointsAfterTieBreakMin = 0; pointsAfterTieBreakMin < 5; pointsAfterTieBreakMin++) {
            builder.add(Arguments.of(
                    String.format("First player won set, game score is %d:%d", TIE_BREAK_MIN + pointsAfterTieBreakMin + 2, TIE_BREAK_MIN + pointsAfterTieBreakMin), pointsAfterTieBreakMin));
        }
        return builder.build();
    }

    static Stream<Arguments> getArgumentsForParametersSecondPlayerWonSetInTieBreakTest() {
        Stream.Builder<Arguments> builder = Stream.builder();

        for (int pointsAfterTieBreakMin = 0; pointsAfterTieBreakMin < 5; pointsAfterTieBreakMin++) {
            builder.add(Arguments.of(
                    String.format("Second player won set, game score is %d:%d", TIE_BREAK_MIN + pointsAfterTieBreakMin, TIE_BREAK_MIN + pointsAfterTieBreakMin + 2), pointsAfterTieBreakMin));
        }
        return builder.build();
    }
}
