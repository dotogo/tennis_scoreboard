package org.proj4.tennis_scoreboard.service;

import org.proj4.tennis_scoreboard.dao.MatchDao;
import org.proj4.tennis_scoreboard.dto.MatchDto;
import org.proj4.tennis_scoreboard.dto.pagination.PaginatedResult;
import org.proj4.tennis_scoreboard.entity.Match;
import org.proj4.tennis_scoreboard.mapper.MatchMapper;

import java.util.List;

public class MatchService {
    private final MatchDao matchDao = new MatchDao();

    public PaginatedResult<MatchDto> getMatchesPaginated(int currentPage, int pageSize) {
        if (currentPage < 1) {
            currentPage = 1;
        }
        List<Match> allMatches = matchDao.getAllMatches(currentPage, pageSize);
        List<MatchDto> items = MatchMapper.INSTANCE.toDtoList(allMatches);

        int totalItems = matchDao.countAll();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        return new PaginatedResult<>(items, currentPage, totalPages, totalItems);
    }
}
