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

        String unexpectedPointWinner = POINT_WINNER_ERROR.formatted(pointWinner, POINT_WINNER_PLAYER1, POINT_WINNER_PLAYER2);

        PlayerScore winnerPlayerScore = switch (pointWinner) {
            case POINT_WINNER_PLAYER1 -> match.getFirstPlayerScore();
            case POINT_WINNER_PLAYER2 -> match.getSecondPlayerScore();
            default -> throw new IllegalStateException(unexpectedPointWinner);
        };

        PlayerScore opponentScore = switch (pointWinner) {
            case POINT_WINNER_PLAYER1 -> match.getSecondPlayerScore();
            case POINT_WINNER_PLAYER2 -> match.getFirstPlayerScore();
            default -> throw new IllegalStateException(unexpectedPointWinner);
        };

        if (isTieBreakWillBeWon(winnerPlayerScore, opponentScore)) {
            incrementSet(winnerPlayerScore, opponentScore);
            return;
        }

        if (isTieBreak(winnerPlayerScore, opponentScore)) {
            incrementTieBreak(winnerPlayerScore, opponentScore);
            return;
        }

        if (isBackToDeuceFromAdvantage(winnerPlayerScore, opponentScore)) {
            backToDeuce(opponentScore);
            return;
        }

        if (isGameWon(winnerPlayerScore, opponentScore)) {
            incrementGame(winnerPlayerScore, opponentScore);
        } else {
            incrementPoint(winnerPlayerScore);
        }

        if (isSetWon(winnerPlayerScore, opponentScore)) {
            incrementSet(winnerPlayerScore, opponentScore);
        }


//        if (isFinishedMatch(winnerPlayerScore)) {
//            // save finished match to database
//            // delete ongoing match from cache
//        }

    }

    public boolean isFinishedMatch(PlayerScore firstPlayerScore, PlayerScore secondPlayerScore) {
        return firstPlayerScore.getSets() == 2 || secondPlayerScore.getSets() == 2;
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

    private void incrementTieBreak(PlayerScore winnerPlayerScore, PlayerScore opponentScore) {
        winnerPlayerScore.setTieBreak(winnerPlayerScore.getTieBreak() + 1);

        winnerPlayerScore.setPoints(Point.LOVE);
        opponentScore.setPoints(Point.LOVE);

    }

    private void incrementGame(PlayerScore winnerPlayerScore, PlayerScore opponentScore) {
        winnerPlayerScore.setGames(winnerPlayerScore.getGames() + 1);

        winnerPlayerScore.setPoints(Point.LOVE);
        opponentScore.setPoints(Point.LOVE);

        winnerPlayerScore.setTieBreak(0);
        opponentScore.setTieBreak(0);
    }

    private void incrementSet(PlayerScore winnerPlayerScore, PlayerScore opponentScore) {
        winnerPlayerScore.setSets(winnerPlayerScore.getSets() + 1);

        winnerPlayerScore.setPoints(Point.LOVE);
        opponentScore.setPoints(Point.LOVE);

        winnerPlayerScore.setGames(0);
        opponentScore.setGames(0);

        winnerPlayerScore.setTieBreak(0);
        opponentScore.setTieBreak(0);
    }

    private boolean isTieBreakWillBeWon(PlayerScore winnerPlayerScore, PlayerScore opponentScore) {
        int winnerTieBreak = winnerPlayerScore.getTieBreak();
        int opponentTieBreak = opponentScore.getTieBreak();


        return (winnerTieBreak >= 6) && ((winnerTieBreak - opponentTieBreak) >= 1);
    }

    private boolean isGameWon(PlayerScore winnerPlayerScore, PlayerScore opponentScore) {
        Point winnerPoints = winnerPlayerScore.getPoints();
        Point opponentPoints = opponentScore.getPoints();

        if (isDeuce(winnerPoints, opponentPoints)) {
            return false;
        }

        if (Point.AD.equals(winnerPoints) && Point.FORTY.equals(opponentPoints)) {
            return true;
        }

        return (Point.FORTY.equals(winnerPoints)) && (opponentPoints.ordinal() <= Point.THIRTY.ordinal());
    }

    private boolean isSetWon(PlayerScore winnerPlayerScore, PlayerScore opponentScore) {
        int winnerGames = winnerPlayerScore.getGames();
        int opponentGames = opponentScore.getGames();

        int winnerTieBreak = winnerPlayerScore.getTieBreak();
        int opponentTieBreak = opponentScore.getTieBreak();


        if ((winnerTieBreak >= 7) && (opponentTieBreak >= 7) && ((winnerTieBreak - opponentTieBreak) == 2)) {
            return true;
        }

        return (winnerGames >= 6) && ((winnerGames - opponentGames) >= 2);
    }

    private boolean isTieBreak(PlayerScore winnerPlayerScore, PlayerScore opponentScore) {
        int winnerGames = winnerPlayerScore.getGames();
        int opponentGames = opponentScore.getGames();

        return winnerGames == 6 && opponentGames == 6;
    }

    private boolean isDeuce(Point winnerPoints, Point opponentPoints) {
        return Point.FORTY.equals(winnerPoints) && Point.FORTY.equals(opponentPoints);
    }

    private boolean isBackToDeuceFromAdvantage(PlayerScore winnerPlayerScore, PlayerScore opponentScore) {
        Point winnerPoints = winnerPlayerScore.getPoints();
        Point opponentPoints = opponentScore.getPoints();

        return Point.FORTY.equals(winnerPoints) && Point.AD.equals(opponentPoints);
    }

    private void backToDeuce(PlayerScore opponentScore) {
        opponentScore.setPoints(Point.FORTY);
    }

}
