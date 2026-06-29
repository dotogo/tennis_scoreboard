package org.proj4.tennis_scoreboard.model;

import org.proj4.tennis_scoreboard.exception.InvalidParameterException;

import java.util.Optional;

public class OngoingMatch {
    private final OngoingPlayer firstPlayer;
    private final OngoingPlayer secondPlayer;
    private final MatchScore matchScore;

    public OngoingMatch(OngoingPlayer firstPlayer, OngoingPlayer secondPlayer, MatchScore matchScore) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.matchScore = matchScore;
    }

    public boolean isFinished() {
        return !GameStatus.ONGOING.equals(matchScore.getStatus());
    }

    public Optional<OngoingPlayer> getWinner() {
        if (GameStatus.FIRST_PLAYER_WON.equals(matchScore.getStatus())) {
            return Optional.of(firstPlayer);
        }
        if (GameStatus.SECOND_PLAYER_WON.equals(matchScore.getStatus())) {
            return Optional.of(secondPlayer);
        }
        return Optional.empty();
    }

    public void addPoint(int playerId) {
        if (isFinished()) {
            throw new UnsupportedOperationException("Ongoing match is already finished");
        }

        if (playerId == firstPlayer.id()) {
            matchScore.addPointFirstPlayer();
        } else if (playerId == secondPlayer.id()) {
            matchScore.addPointSecondPlayer();
        } else {
            throw new InvalidParameterException("Invalid player id: " + playerId);
        }
    }
}
