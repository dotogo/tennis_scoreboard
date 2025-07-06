package org.proj4.tennis_scoreboard.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.proj4.tennis_scoreboard.dto.MatchDto;
import org.proj4.tennis_scoreboard.entity.Match;

import java.util.List;

public interface MatchMapper {
    MatchMapper INSTANCE = Mappers.getMapper(MatchMapper.class);

    @Mapping(source = "firstPlayer.name", target = "firstPlayerName")
    @Mapping(source = "secondPlayer.name", target = "secondPlayerName")
    @Mapping(source = "winner.name", target = "winnerName")
    MatchDto toDto(Match match);

    List<MatchDto> toDtoList(List<Match> matches);
}
