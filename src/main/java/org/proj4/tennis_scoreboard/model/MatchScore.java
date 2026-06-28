package org.proj4.tennis_scoreboard.model;

public class MatchScore implements TennisScore {
    private static final int SETS_TO_WIN = 2;

    private int firstPlayerSets;
    private int secondPlayerSets;
    private boolean matchFinished;

    private TennisScore currentSetScore;

    public MatchScore() {
        currentSetScore = new SetScore();
    }

    @Override
    public void addPointFirstPlayer() {
        if (!matchFinished && GameStatus.ONGOING.equals(currentSetScore.getStatus())) {
            currentSetScore.addPointFirstPlayer();
        }

        firstPlayerSets = calculatePlayerSets(GameStatus.FIRST_PLAYER_WON, firstPlayerSets);

        finishMatchIfPlayerWon(firstPlayerSets);

        updateCurrentSetScoreIfNewSetStarting();
    }

    @Override
    public void addPointSecondPlayer() {
        if (!matchFinished && GameStatus.ONGOING.equals(currentSetScore.getStatus())) {
            currentSetScore.addPointSecondPlayer();
        }

        secondPlayerSets = calculatePlayerSets(GameStatus.SECOND_PLAYER_WON, secondPlayerSets);

        finishMatchIfPlayerWon(secondPlayerSets);

        updateCurrentSetScoreIfNewSetStarting();
    }

    @Override
    public GameStatus getStatus() {
        if (matchFinished) {
            if (firstPlayerSets > secondPlayerSets) {
                return GameStatus.FIRST_PLAYER_WON;
            }
            return GameStatus.SECOND_PLAYER_WON;
        }
        return GameStatus.ONGOING;
    }

    private int calculatePlayerSets(GameStatus gameStatus, int pointWinnerSets) {
        if (gameStatus.equals(currentSetScore.getStatus())) {
            return ++pointWinnerSets;
        }
        return pointWinnerSets;
    }

    private void finishMatchIfPlayerWon(int winnerPoints) {
        if (winnerPoints == SETS_TO_WIN) {
            matchFinished = true;
        }
    }

    private void updateCurrentSetScoreIfNewSetStarting() {
        if (!matchFinished && currentSetScore.getStatus() != GameStatus.ONGOING) {
            currentSetScore = new SetScore();
        }
    }
}
