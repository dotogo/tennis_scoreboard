package org.proj4.tennis_scoreboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.proj4.tennis_scoreboard.entity.OngoingMatch;
import org.proj4.tennis_scoreboard.entity.Player;
import org.proj4.tennis_scoreboard.entity.PlayerScore;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class MatchScoreCalculationServiceTest {

    private MatchScoreCalculationService matchScoreCalculationService;

    @BeforeEach
    void prepare() {
        matchScoreCalculationService = new MatchScoreCalculationService();
    }

    @Test
    void FinishedMatchIfFirstPlayerHasSetsEquals2() {
        PlayerScore firstPlayerScore = new PlayerScore();
        firstPlayerScore.setSets(2);

        PlayerScore secondPlayerScore = new PlayerScore();

        boolean result = matchScoreCalculationService.isFinishedMatch(firstPlayerScore, secondPlayerScore);

        assertTrue(result);
    }

    @Test
    void FinishedMatchIfSecondPlayerHasSetsEquals2() {
        PlayerScore firstPlayerScore = new PlayerScore();

        PlayerScore secondPlayerScore = new PlayerScore();
        secondPlayerScore.setSets(2);

        boolean result = matchScoreCalculationService.isFinishedMatch(firstPlayerScore, secondPlayerScore);

        assertTrue(result);
    }

    @Test
    void UnfinishedMatchIfNeitherPlayerHas2Sets() {
        PlayerScore firstPlayerScore = new PlayerScore();

        PlayerScore secondPlayerScore = new PlayerScore();
        secondPlayerScore.setSets(1);

        boolean result = matchScoreCalculationService.isFinishedMatch(firstPlayerScore, secondPlayerScore);

        assertFalse(result);
    }

    @Test
    void FirstPlayerIsWinner() {
        Player firstPlayer = new Player();
        Player secondPlayer = new Player();
        PlayerScore firstPlayerScore = new PlayerScore();
        firstPlayerScore.setSets(2);
        PlayerScore secondPlayerScore = new PlayerScore();

        OngoingMatch ongoingMatch = new OngoingMatch(firstPlayer, secondPlayer, firstPlayerScore, secondPlayerScore);

        Optional<Player> winner = matchScoreCalculationService.getWinner(ongoingMatch);

        assertThat(winner).hasValue(firstPlayer);
    }

    @Test
    void SecondPlayerIsWinner() {
        Player firstPlayer = new Player();
        Player secondPlayer = new Player();
        PlayerScore firstPlayerScore = new PlayerScore();
        PlayerScore secondPlayerScore = new PlayerScore();
        secondPlayerScore.setSets(2);

        OngoingMatch ongoingMatch = new OngoingMatch(firstPlayer, secondPlayer, firstPlayerScore, secondPlayerScore);

        Optional<Player> winner = matchScoreCalculationService.getWinner(ongoingMatch);

        assertThat(winner).hasValue(secondPlayer);
    }

    @Test
    void NoWinnerFound() {
        Player firstPlayer = new Player();
        Player secondPlayer = new Player();
        PlayerScore firstPlayerScore = new PlayerScore();
        PlayerScore secondPlayerScore = new PlayerScore();

        OngoingMatch ongoingMatch = new OngoingMatch(firstPlayer, secondPlayer, firstPlayerScore, secondPlayerScore);

        Optional<Player> winner = matchScoreCalculationService.getWinner(ongoingMatch);

        assertFalse(winner.isPresent());
    }
}
