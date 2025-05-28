package org.proj4.tennis_scoreboard.service;

import org.proj4.tennis_scoreboard.entity.OngoingMatch;
import org.proj4.tennis_scoreboard.entity.PlayerScore;
import org.proj4.tennis_scoreboard.entity.Point;

public class MatchScoreCalculationService {
    private static final String POINT_WINNER_PLAYER1 = "player1";
    private static final String POINT_WINNER_PLAYER2 = "player2";

    private static final String POINT_WINNER_ERROR = "Unexpected value: %s. Expected parameter value: %s or %s";


    public void updateMatchScore(OngoingMatch match, String pointWinner) {
        // update score
        System.out.println("Match score calculation. Add point: " + pointWinner);

        PlayerScore winnerPlayerScore = switch (pointWinner) {
            case POINT_WINNER_PLAYER1 -> match.getFirstPlayerScore();
            case POINT_WINNER_PLAYER2 -> match.getSecondPlayerScore();
            default -> throw new IllegalStateException(POINT_WINNER_ERROR.formatted(pointWinner, POINT_WINNER_PLAYER1, POINT_WINNER_PLAYER2));
        };

        PlayerScore opponentScore = switch (pointWinner) {
            case POINT_WINNER_PLAYER1 -> match.getSecondPlayerScore();
            case POINT_WINNER_PLAYER2 -> match.getFirstPlayerScore();
            default -> throw new IllegalStateException(POINT_WINNER_ERROR.formatted(pointWinner, POINT_WINNER_PLAYER1, POINT_WINNER_PLAYER2));
        };
    }

    public boolean isFinishedMatch(OngoingMatch match) {
        return false;
    }

    private void incrementPoint(PlayerScore winnerPlayerScore) {
        Point currentPoint = winnerPlayerScore.getPoints();
        Point newPoint =  switch (currentPoint) {
            case LOVE -> Point.FIFTEEN;
            case FIFTEEN -> Point.THIRTY;
            case THIRTY -> Point.FORTY;
            case FORTY, AD -> Point.AD;
        };

        winnerPlayerScore.setPoints(newPoint);
    }
}
