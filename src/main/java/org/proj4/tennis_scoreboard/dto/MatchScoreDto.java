package org.proj4.tennis_scoreboard.dto;

public record MatchScoreDto(String firstPlayerSets, String secondPlayerSets, SetScoreDto setScoreDto) {

    public String getFirstPlayerSets() {
        return firstPlayerSets;
    }
    public String getSecondPlayerSets() {
        return secondPlayerSets;
    }

    public String getFirstPlayerGames() {
        return setScoreDto.firstPlayerGames();
    }

    public String getSecondPlayerGames() {
        return setScoreDto.secondPlayerGames();
    }

    public String getFirstPlayerPoints() {
        return setScoreDto.gameScoreDto().firstPlayerScore();
    }

    public String getSecondPlayerPoints() {
        return setScoreDto.gameScoreDto().secondPlayerScore();
    }

    public boolean isTieBreak() {
        return setScoreDto.tieBreak();
    }

}
