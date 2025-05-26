package org.proj4.tennis_scoreboard.service;

import org.proj4.tennis_scoreboard.entity.OngoingMatch;

public class FinishedMatchesPersistenceService {
    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();

    public void persistMatch(OngoingMatch ongoingMatch) {
        // persist to database

        System.out.println("Persisting match between: " + ongoingMatch.getFirstPlayer().getName() +
                           " and " + ongoingMatch.getSecondPlayer().getName());
    }

}
