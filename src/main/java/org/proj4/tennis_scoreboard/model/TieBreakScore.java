package org.proj4.tennis_scoreboard.model;

public class TieBreakScore implements TennisScore {
    private int firstPlayerPoint;
    private int secondPlayerPoint;
    private boolean tieBreakFinished;

    @Override
    public void addPointFirstPlayer() {
        if (!tieBreakFinished) {
            firstPlayerPoint = addPoint(firstPlayerPoint, secondPlayerPoint);
        }
    }

    @Override
    public void addPointSecondPlayer() {
        if (!tieBreakFinished) {
            secondPlayerPoint = addPoint(secondPlayerPoint, firstPlayerPoint);
        }
    }

    @Override
    public GameStatus getStatus() {
        if (tieBreakFinished) {
            if (firstPlayerPoint > secondPlayerPoint) {
                return GameStatus.FIRST_PLAYER_WON;
            }
            return GameStatus.SECOND_PLAYER_WON;
        }
        return GameStatus.ONGOING;
    }

    private int addPoint(int winnerTieBreak, int opponentTieBreak) {
        if (isTieBreakWillBeFinished(winnerTieBreak, opponentTieBreak)) {
            tieBreakFinished = true;
            return ++winnerTieBreak;
        }
        return ++winnerTieBreak;
    }

    private boolean isTieBreakWillBeFinished(int winnerTieBreak, int opponentTieBreak) {
        return (winnerTieBreak >= 6) && ((winnerTieBreak - opponentTieBreak) >= 1);
    }
}
