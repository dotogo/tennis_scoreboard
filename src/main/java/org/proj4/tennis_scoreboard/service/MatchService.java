package org.proj4.tennis_scoreboard.service;

import org.proj4.tennis_scoreboard.dao.MatchDao;
import org.proj4.tennis_scoreboard.dto.MatchDto;
import org.proj4.tennis_scoreboard.entity.Match;
import org.proj4.tennis_scoreboard.mapper.MatchMapper;

import java.util.List;

public class MatchService {
    private final MatchDao matchDao = new MatchDao();

    public List<MatchDto> getMatchesPaginated(int page, int pageSize) {
        List<Match> allMatches = matchDao.getAllMatches(page, pageSize);
        return MatchMapper.INSTANCE.toDtoList(allMatches);
    }
}
