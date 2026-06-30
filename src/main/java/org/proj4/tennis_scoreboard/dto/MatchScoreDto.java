package org.proj4.tennis_scoreboard.dto;

public record MatchScoreDto(String firstPlayerSets, String secondPlayerSets, SetScoreDto setScoreDto) {

    public String firstPlayerGames() {
        return setScoreDto.firstPlayerGames();
    }

    public String secondPlayerGames() {
        return setScoreDto.secondPlayerGames();
    }

    public String firstPlayerPoints() {
        return setScoreDto.gameScoreDto().firstPlayerScore();
    }

    public String secondPlayerPoints() {
        return setScoreDto.gameScoreDto().secondPlayerScore();
    }

    public boolean isTieBreak() {
        return setScoreDto.tieBreak();
    }

}
