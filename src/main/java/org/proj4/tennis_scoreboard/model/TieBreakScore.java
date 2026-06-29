package org.proj4.tennis_scoreboard.model;

import org.proj4.tennis_scoreboard.dto.GameScoreDto;

public class TieBreakScore implements TennisScore {
    private static final int MIN_TIEBREAK_POINTS = 6;
    private static final int MIN_TIEBREAK_LEAD = 1;

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

    protected GameScoreDto getGameScoreDto() {
        return new GameScoreDto(String.valueOf(firstPlayerPoint), String.valueOf(secondPlayerPoint));
    }

    private int addPoint(int winnerTieBreak, int opponentTieBreak) {
        if (isTieBreakWillBeFinished(winnerTieBreak, opponentTieBreak)) {
            tieBreakFinished = true;
            return ++winnerTieBreak;
        }
        return ++winnerTieBreak;
    }

    private boolean isTieBreakWillBeFinished(int winnerTieBreak, int opponentTieBreak) {
        return (winnerTieBreak >= MIN_TIEBREAK_POINTS) && ((winnerTieBreak - opponentTieBreak) >= MIN_TIEBREAK_LEAD);
    }
}
