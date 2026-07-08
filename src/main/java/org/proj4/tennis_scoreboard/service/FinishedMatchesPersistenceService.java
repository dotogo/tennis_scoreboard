package org.proj4.tennis_scoreboard.service;

import org.proj4.tennis_scoreboard.dao.MatchDao;
import org.proj4.tennis_scoreboard.dao.PlayerDao;
import org.proj4.tennis_scoreboard.dao.impl.MatchDaoImpl;
import org.proj4.tennis_scoreboard.dao.impl.PlayerDaoImpl;
import org.proj4.tennis_scoreboard.entity.Match;
import org.proj4.tennis_scoreboard.model.OngoingMatch;
import org.proj4.tennis_scoreboard.entity.Player;
import org.proj4.tennis_scoreboard.model.OngoingPlayer;

import java.util.Objects;
import java.util.Optional;

public class FinishedMatchesPersistenceService {
    private static final String MATCH_CANNOT_BE_NULL = "Match cannot be null";
    private static final String MATCH_MUST_BE_FINISHED = "Match must be finished";
    private static final String FINISHED_MATCH_MUST_HAVE_WINNER = "Finished Match must have a winner";
    private static final String FINISHED_MATCH_MUST_HAVE_TWO_PLAYERS = "Finished Match must have two players";
    private final MatchDao matchDao;
    private final PlayerDao playerDao;

    public FinishedMatchesPersistenceService() {
        this(new MatchDaoImpl(), new PlayerDaoImpl());
    }

    public FinishedMatchesPersistenceService(MatchDao matchDao, PlayerDao playerDao) {
        this.matchDao = matchDao;
        this.playerDao = playerDao;
    }

    public void persistMatch(OngoingMatch ongoingMatch) {
        Objects.requireNonNull(ongoingMatch, MATCH_CANNOT_BE_NULL);

        if (!ongoingMatch.isFinished()) {
            throw new IllegalStateException(MATCH_MUST_BE_FINISHED);
        }
        
        OngoingPlayer ongoingMatchFirstPlayer = ongoingMatch.getFirstPlayer();
        Optional<Player> optionalFirstPlayer = playerDao.findByName(ongoingMatchFirstPlayer.name());
        Player firstPlayer = optionalFirstPlayer.orElseThrow(() -> new IllegalStateException(FINISHED_MATCH_MUST_HAVE_TWO_PLAYERS));

        OngoingPlayer ongoingMatchSecondPlayer = ongoingMatch.getSecondPlayer();
        Optional<Player> optionalSecondPlayer = playerDao.findByName(ongoingMatchSecondPlayer.name());
        Player secondPlayer = optionalSecondPlayer.orElseThrow(() -> new IllegalStateException(FINISHED_MATCH_MUST_HAVE_TWO_PLAYERS));

        OngoingPlayer ongoingWinner = ongoingMatch.getWinner().orElseThrow(() -> new IllegalStateException(FINISHED_MATCH_MUST_HAVE_WINNER));

        Player winner;
        
        if (firstPlayer.getName().equals(ongoingWinner.name())) {
            winner = firstPlayer;
        } else if (secondPlayer.getName().equals(ongoingWinner.name())) {
            winner = secondPlayer;
        } else {
            throw new IllegalStateException(FINISHED_MATCH_MUST_HAVE_WINNER);
        }
        
        Match match = new Match(firstPlayer, secondPlayer, winner);

        matchDao.save(match);
    }
}
