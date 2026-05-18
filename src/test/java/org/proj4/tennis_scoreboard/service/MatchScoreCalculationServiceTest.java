package org.proj4.tennis_scoreboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.proj4.tennis_scoreboard.entity.OngoingMatch;
import org.proj4.tennis_scoreboard.entity.Player;
import org.proj4.tennis_scoreboard.entity.PlayerScore;

import java.util.Optional;

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
        void FinishedMatchIfFirstPlayerHasSetsEquals2() {
            firstPlayerScore.setSets(2);

            boolean result = matchScoreCalculationService.isFinishedMatch(firstPlayerScore, secondPlayerScore);

            assertTrue(result);
        }

        @Test
        @DisplayName("Match finished. Second player won 2 sets.")
        void FinishedMatchIfSecondPlayerHasSetsEquals2() {
            secondPlayerScore.setSets(2);

            boolean result = matchScoreCalculationService.isFinishedMatch(firstPlayerScore, secondPlayerScore);

            assertTrue(result);
        }

        @Test
        @DisplayName("Unfinished match. Neither player won 2 sets.")
        void UnfinishedMatchIfNeitherPlayerHas2Sets() {
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

        @BeforeEach
        void prepareNested() {
            firstPlayer = new Player();
            secondPlayer = new Player();
        }

        @Test
        @DisplayName("First Player is Winner")
        void FirstPlayerIsWinner() {
            firstPlayerScore.setSets(2);

            OngoingMatch ongoingMatch = new OngoingMatch(firstPlayer, secondPlayer, firstPlayerScore, secondPlayerScore);

            Optional<Player> winner = matchScoreCalculationService.getWinner(ongoingMatch);

            assertThat(winner).hasValue(firstPlayer);
        }

        @Test
        @DisplayName("Second Player is Winner")
        void SecondPlayerIsWinner() {
            secondPlayerScore.setSets(2);

            OngoingMatch ongoingMatch = new OngoingMatch(firstPlayer, secondPlayer, firstPlayerScore, secondPlayerScore);

            Optional<Player> winner = matchScoreCalculationService.getWinner(ongoingMatch);

            assertThat(winner).hasValue(secondPlayer);
        }

        @Test
        @DisplayName("No Winner found")
        void NoWinnerFound() {
            OngoingMatch ongoingMatch = new OngoingMatch(firstPlayer, secondPlayer, firstPlayerScore, secondPlayerScore);

            Optional<Player> winner = matchScoreCalculationService.getWinner(ongoingMatch);

            assertFalse(winner.isPresent());
        }
    }

    @Nested
    @DisplayName("Testing the match score update")
    class UpdateMatchScoreTest {

        @Test
        void OnePointWillBeAddedToPointWinner() {

        }

    }
}
