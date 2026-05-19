package org.proj4.tennis_scoreboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.proj4.tennis_scoreboard.entity.OngoingMatch;
import org.proj4.tennis_scoreboard.entity.Player;
import org.proj4.tennis_scoreboard.entity.PlayerScore;
import org.proj4.tennis_scoreboard.entity.Point;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class MatchScoreCalculationServiceTest {

    private MatchScoreCalculationService matchScoreCalculationService;
    private PlayerScore firstPlayerScore;
    private PlayerScore secondPlayerScore;

    @BeforeEach
    void prepareParent() {
        matchScoreCalculationService = new MatchScoreCalculationService();
        firstPlayerScore = new PlayerScore();
        secondPlayerScore = new PlayerScore();
    }

    @Nested
    @DisplayName("Is the match finished?")
    class FinishedMatchTest {

        @Test
        @DisplayName("Match finished. First player won 2 sets.")
        void finishedMatchIfFirstPlayerHasSetsEquals2() {
            firstPlayerScore.setSets(2);

            boolean result = matchScoreCalculationService.isFinishedMatch(firstPlayerScore, secondPlayerScore);

            assertTrue(result);
        }

        @Test
        @DisplayName("Match finished. Second player won 2 sets.")
        void finishedMatchIfSecondPlayerHasSetsEquals2() {
            secondPlayerScore.setSets(2);

            boolean result = matchScoreCalculationService.isFinishedMatch(firstPlayerScore, secondPlayerScore);

            assertTrue(result);
        }

        @Test
        @DisplayName("Unfinished match. Neither player won 2 sets.")
        void unfinishedMatchIfNeitherPlayerHas2Sets() {
            secondPlayerScore.setSets(1);

            boolean result = matchScoreCalculationService.isFinishedMatch(firstPlayerScore, secondPlayerScore);

            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("Get a winner?")
    class GetWinnerTest {
        private Player firstPlayer;
        private Player secondPlayer;
        private OngoingMatch ongoingMatch;

        @BeforeEach
        void prepareNested() {
            firstPlayer = new Player();
            secondPlayer = new Player();
            ongoingMatch = new OngoingMatch(firstPlayer, secondPlayer, firstPlayerScore, secondPlayerScore);
        }

        @Test
        @DisplayName("First Player is Winner")
        void firstPlayerIsWinner() {
            firstPlayerScore.setSets(2);

            Optional<Player> winner = matchScoreCalculationService.getWinner(ongoingMatch);

            assertThat(winner).hasValue(firstPlayer);
        }

        @Test
        @DisplayName("Second Player is Winner")
        void secondPlayerIsWinner() {
            secondPlayerScore.setSets(2);

            Optional<Player> winner = matchScoreCalculationService.getWinner(ongoingMatch);

            assertThat(winner).hasValue(secondPlayer);
        }

        @Test
        @DisplayName("No Winner found")
        void noWinnerFound() {
            Optional<Player> winner = matchScoreCalculationService.getWinner(ongoingMatch);

            assertFalse(winner.isPresent());
        }
    }

    @Nested
    @DisplayName("Testing the match score update")
    class UpdateMatchScoreTest {
        private OngoingMatch ongoingMatch;

        @BeforeEach
        void prepareNested() {
            ongoingMatch = new OngoingMatch(new Player(), new Player(), firstPlayerScore, secondPlayerScore);
        }

        @Test
        @DisplayName("One point to first player")
        void onePointWillBeAddedToFirstPlayer() {
            String pointWinner = "player1";

            matchScoreCalculationService.updateMatchScore(ongoingMatch, pointWinner);

            assertThat(firstPlayerScore.getPoints()).isEqualTo(Point.FIFTEEN);
        }

        @Test
        @DisplayName("One point to second player")
        void onePointWillBeAddedToSecondPlayer() {
            String pointWinner = "player2";

            matchScoreCalculationService.updateMatchScore(ongoingMatch, pointWinner);

            assertThat(secondPlayerScore.getPoints()).isEqualTo(Point.FIFTEEN);
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("getArgumentsForParametersPointWinnerWonGameTest")
        @DisplayName("Point winner won game")
        void pointWinnerWonGame(String description, Point firstPlayerPoint, Point secondPlayerPoint) {
            String pointWinner = "player1";

            firstPlayerScore.setPoints(firstPlayerPoint);
            secondPlayerScore.setPoints(secondPlayerPoint);

            matchScoreCalculationService.updateMatchScore(ongoingMatch, pointWinner);

            assertThat(firstPlayerScore.getGames()).isEqualTo(1);
        }

        @Test
        @DisplayName("Point winner gets AD if deuce")
        void PointWinnerGetAdvantageIfDeuce() {
            String pointWinner = "player1";

            firstPlayerScore.setPoints(Point.FORTY);
            secondPlayerScore.setPoints(Point.FORTY);

            matchScoreCalculationService.updateMatchScore(ongoingMatch, pointWinner);

            assertAll(
                    () -> assertThat(firstPlayerScore.getPoints()).isEqualTo(Point.AD),
                    () -> assertThat(firstPlayerScore.getGames()).isEqualTo(0),
                    () -> assertThat(secondPlayerScore.getGames()).isEqualTo(0)
            );
        }

        @Test
        @DisplayName("Back to deuce from AD. Point winner - opponent: 40 - AD")
        void fromAdvantageBackToDeuceIfPointWinnerHasFortyAndOpponentHasAD() {
            String pointWinner = "player1";

            firstPlayerScore.setPoints(Point.FORTY);
            secondPlayerScore.setPoints(Point.AD);

            matchScoreCalculationService.updateMatchScore(ongoingMatch, pointWinner);

            assertAll(
                    () -> assertThat(firstPlayerScore.getPoints()).isEqualTo(Point.FORTY),
                    () -> assertThat(secondPlayerScore.getPoints()).isEqualTo(Point.FORTY)
            );
        }

        static Stream<Arguments> getArgumentsForParametersPointWinnerWonGameTest() {
            return Stream.of(
                    Arguments.of("Point winner - opponent: AD - FORTY", Point.AD, Point.FORTY),
                    Arguments.of("Point winner - opponent: FORTY - THIRTY", Point.FORTY, Point.THIRTY),
                    Arguments.of("Point winner - opponent: FORTY - FIFTEEN", Point.FORTY, Point.FIFTEEN),
                    Arguments.of("Point winner - opponent: FORTY - LOVE", Point.FORTY, Point.LOVE)
            );
        }

    }
}
