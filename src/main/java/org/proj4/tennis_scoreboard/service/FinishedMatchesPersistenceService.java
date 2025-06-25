package org.proj4.tennis_scoreboard.service;

import org.proj4.tennis_scoreboard.dao.MatchDao;
import org.proj4.tennis_scoreboard.entity.Match;
import org.proj4.tennis_scoreboard.entity.OngoingMatch;
import org.proj4.tennis_scoreboard.entity.Player;
import org.proj4.tennis_scoreboard.entity.PlayerScore;

import java.util.Objects;

public class FinishedMatchesPersistenceService {
    private final MatchDao matchDao = new MatchDao();
    private final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();

    public void persistMatch(OngoingMatch ongoingMatch) {
        Objects.requireNonNull(ongoingMatch, "Match cannot be null");

        PlayerScore firstPlayerScore = ongoingMatch.getFirstPlayerScore();
        PlayerScore secondPlayerScore = ongoingMatch.getSecondPlayerScore();

        if (!matchScoreCalculationService.isFinishedMatch(firstPlayerScore, secondPlayerScore)) {
            throw new IllegalStateException("Match must be finished");
        }

        Match match = new Match();
        match.setFirstPlayer(ongoingMatch.getFirstPlayer());
        match.setSecondPlayer(ongoingMatch.getSecondPlayer());

        Player winner = matchScoreCalculationService.getWinner(ongoingMatch)
                .orElseThrow(() -> new IllegalStateException("Finished match must have a winner"));

        match.setWinner(winner);

        matchDao.save(match);

        System.out.println("Persisting match between: " + match.getFirstPlayer().getName() +
                           " and " + match.getSecondPlayer().getName());
    }

}
