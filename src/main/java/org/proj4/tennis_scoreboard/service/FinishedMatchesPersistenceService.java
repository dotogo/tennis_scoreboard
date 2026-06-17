package org.proj4.tennis_scoreboard.service;

import org.proj4.tennis_scoreboard.dao.MatchDao;
import org.proj4.tennis_scoreboard.dao.impl.MatchDaoImpl;
import org.proj4.tennis_scoreboard.entity.Match;
import org.proj4.tennis_scoreboard.entity.OngoingMatch;
import org.proj4.tennis_scoreboard.entity.Player;
import org.proj4.tennis_scoreboard.entity.PlayerScore;

import java.util.Objects;

public class FinishedMatchesPersistenceService {
    private static final String MATCH_CANNOT_BE_NULL = "Match cannot be null";
    private static final String MATCH_MUST_BE_FINISHED = "Match must be finished";
    private static final String FINISHED_MATCH_MUST_HAVE_WINNER = "Finished Match must have a winner";
    private final MatchDao matchDao;
    private final MatchScoreCalculationService matchScoreCalculationService;

    public FinishedMatchesPersistenceService() {
        this(new MatchDaoImpl(), new MatchScoreCalculationService());
    }

    public FinishedMatchesPersistenceService(MatchDao matchDao, MatchScoreCalculationService matchScoreCalculationService) {
        this.matchDao = matchDao;
        this.matchScoreCalculationService = matchScoreCalculationService;
    }

    public void persistMatch(OngoingMatch ongoingMatch) {
        Objects.requireNonNull(ongoingMatch, MATCH_CANNOT_BE_NULL);

        PlayerScore firstPlayerScore = ongoingMatch.getFirstPlayerScore();
        PlayerScore secondPlayerScore = ongoingMatch.getSecondPlayerScore();

        if (!matchScoreCalculationService.isFinishedMatch(firstPlayerScore, secondPlayerScore)) {
            throw new IllegalStateException(MATCH_MUST_BE_FINISHED);
        }

        Player firstPlayer = ongoingMatch.getFirstPlayer();
        Player secondPlayer = ongoingMatch.getSecondPlayer();

        Player winner = matchScoreCalculationService.getWinner(ongoingMatch)
                .orElseThrow(() -> new IllegalStateException(FINISHED_MATCH_MUST_HAVE_WINNER));

        Match match = new Match(firstPlayer, secondPlayer, winner);

        matchDao.save(match);
    }
}
