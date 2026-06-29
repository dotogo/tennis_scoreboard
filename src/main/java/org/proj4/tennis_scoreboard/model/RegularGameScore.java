package org.proj4.tennis_scoreboard.model;

import org.proj4.tennis_scoreboard.dto.GameScoreDto;
import org.proj4.tennis_scoreboard.entity.Point;

public class RegularGameScore implements TennisScore {
    private Point firstPlayerPoint;
    private Point secondPlayerPoint;
    private boolean gameFinished;

    protected RegularGameScore() {
        firstPlayerPoint = Point.LOVE;
        secondPlayerPoint = Point.LOVE;
        gameFinished = false;
    }

    @Override
    public void addPointFirstPlayer() {
        firstPlayerPoint = addPoint(firstPlayerPoint, secondPlayerPoint);
    }

    @Override
    public void addPointSecondPlayer() {
        secondPlayerPoint = addPoint(secondPlayerPoint, firstPlayerPoint);
    }

    @Override
    public GameStatus getStatus() {
        if (gameFinished) {
            if (firstPlayerPoint.ordinal() > secondPlayerPoint.ordinal()) {
                return GameStatus.FIRST_PLAYER_WON;
            }
            return GameStatus.SECOND_PLAYER_WON;
        }
        return GameStatus.ONGOING;
    }

    protected GameScoreDto getGameScoreDto() {
        return new GameScoreDto(firstPlayerPoint.getValue(), secondPlayerPoint.getValue());
    }

    protected boolean isDeuce() {
        return Point.FORTY.equals(firstPlayerPoint) && Point.FORTY.equals(secondPlayerPoint);
    }

    private boolean isGameFinishedAfterDeuce(Point currentWinnerPoints) {
        return Point.AD.equals(currentWinnerPoints);
    }

    private Point addPoint(Point winnerPoints, Point opponentPoints) {
        if (isBackToDeuceFromAdvantage(winnerPoints, opponentPoints)) {
            backToDeuce();
            return winnerPoints;
        }

        if (isGameFinishedAfterDeuce(winnerPoints)) {
            gameFinished = true;
            return winnerPoints;
        }

        if (isGameFinishedOnPoints(winnerPoints, opponentPoints)) {
            gameFinished = true;
            return winnerPoints;
        }
        return incrementPoint(winnerPoints);
    }

    private boolean isGameFinishedOnPoints(Point winnerPoints, Point opponentPoints) {
        return (Point.FORTY.equals(winnerPoints)) && (opponentPoints.ordinal() <= Point.THIRTY.ordinal());
    }
    
    private Point incrementPoint(Point current) {
        return switch (current) {
            case LOVE -> Point.FIFTEEN;
            case FIFTEEN -> Point.THIRTY;
            case THIRTY -> Point.FORTY;
            case FORTY, AD -> Point.AD;
        };
    }

    private boolean isBackToDeuceFromAdvantage(Point winnerPoints, Point opponentPoints) {
        return Point.FORTY.equals(winnerPoints) && Point.AD.equals(opponentPoints);
    }

    private void backToDeuce() {
        firstPlayerPoint = Point.FORTY;
        secondPlayerPoint = Point.FORTY;
    }
}
