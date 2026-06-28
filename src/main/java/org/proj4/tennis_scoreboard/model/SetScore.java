package org.proj4.tennis_scoreboard.model;

public class SetScore implements TennisScore {
    private static final int GAMES_FOR_TIEBREAK = 6;
    private static final int GAMES_TO_WIN_SET = 6;
    private static final int MIN_GAME_DIFFERENCE = 2;

    private int firstPlayerGames;
    private int secondPlayerGames;
    private boolean setFinished;

    private TennisScore currentGame;

    public SetScore() {
        currentGame = new RegularGameScore();
    }

    @Override
    public void addPointFirstPlayer() {
        if (GameStatus.ONGOING.equals(currentGame.getStatus())) {
            currentGame.addPointFirstPlayer();
        }

        int gamesBeforeCalculateNewValue = firstPlayerGames;

        firstPlayerGames = calculatePlayerGames(GameStatus.FIRST_PLAYER_WON, firstPlayerGames);

        finishSetIfPlayerWon(GameStatus.FIRST_PLAYER_WON, firstPlayerGames, secondPlayerGames);

        updateCurrentGameIfNewStarting(firstPlayerGames, gamesBeforeCalculateNewValue);
    }

    @Override
    public void addPointSecondPlayer() {
        if (GameStatus.ONGOING.equals(currentGame.getStatus())) {
            currentGame.addPointSecondPlayer();
        }

        int gamesBeforeCalculateNewValue = secondPlayerGames;

        secondPlayerGames = calculatePlayerGames(GameStatus.SECOND_PLAYER_WON, secondPlayerGames);

        finishSetIfPlayerWon(GameStatus.SECOND_PLAYER_WON, secondPlayerGames, firstPlayerGames);

        updateCurrentGameIfNewStarting(secondPlayerGames, gamesBeforeCalculateNewValue);
    }

    @Override
    public GameStatus getStatus() {
        if (setFinished) {
            if (isRegularGame() && firstPlayerGames > secondPlayerGames) {
                return GameStatus.FIRST_PLAYER_WON;
            }

            if (isRegularGame() && secondPlayerGames > firstPlayerGames) {
                return GameStatus.SECOND_PLAYER_WON;
            }

            if (isTieBreak() && GameStatus.FIRST_PLAYER_WON.equals(currentGame.getStatus())) {
                return GameStatus.FIRST_PLAYER_WON;
            }

            if (isTieBreak() && GameStatus.SECOND_PLAYER_WON.equals(currentGame.getStatus())) {
                return GameStatus.SECOND_PLAYER_WON;
            }
        }
        return GameStatus.ONGOING;
    }

    private void finishSetIfPlayerWon(GameStatus gameStatus, int winnerPoints, int opponentPoints) {
        if (isRegularGame() && isSetFinishedOnRegularGame(winnerPoints, opponentPoints)) {
            setFinished = true;
        }

        if (isTieBreak() && gameStatus.equals(currentGame.getStatus())) {
            setFinished = true;
        }
    }

    private int calculatePlayerGames(GameStatus gameStatus, int pointWinnerGames) {
        if (isRegularGame() && gameStatus.equals(currentGame.getStatus())) {
            return ++pointWinnerGames;
        }
        return pointWinnerGames;
    }

    private void updateCurrentGameIfNewStarting(int updatedGamesScore, int oldGamesScore) {
        if (isTieBreakStarting()) {
            currentGame = new TieBreakScore();
        }

        if (!setFinished && isNewRegularGameStarting(updatedGamesScore, oldGamesScore)) {
            currentGame = new RegularGameScore();
        }
    }

    private boolean isTieBreakStarting() {
        if (isRegularGame()) {
            return firstPlayerGames == GAMES_FOR_TIEBREAK && secondPlayerGames == GAMES_FOR_TIEBREAK;
        }
        return false;
    }

    private boolean isNewRegularGameStarting(int updatedGamesScore, int oldGamesScore) {
        return isRegularGame() && (updatedGamesScore - oldGamesScore == 1);
    }

    private boolean isSetFinishedOnRegularGame(int winnerPoints, int opponentPoints) {
        return winnerPoints >= GAMES_TO_WIN_SET && (winnerPoints - opponentPoints) >= MIN_GAME_DIFFERENCE;
    }

    private boolean isRegularGame() {
        return currentGame instanceof RegularGameScore;
    }

    private boolean isTieBreak() {
        return currentGame instanceof TieBreakScore;
    }
}
