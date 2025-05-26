package org.proj4.tennis_scoreboard.service;

import org.proj4.tennis_scoreboard.entity.OngoingMatch;

public class MatchScoreCalculationService {

    public void updateMatchScore(OngoingMatch match, String pointScorer) {
        // update score

        System.out.println("Match score calculation. Add point: " + pointScorer);

    }

    public boolean isFinishedMatch(OngoingMatch match) {
        return false;
    }
}
