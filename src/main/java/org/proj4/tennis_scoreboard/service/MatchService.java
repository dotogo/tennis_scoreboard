package org.proj4.tennis_scoreboard.service;

import org.proj4.tennis_scoreboard.dao.MatchDao;
import org.proj4.tennis_scoreboard.dao.PlayerDao;
import org.proj4.tennis_scoreboard.dao.impl.MatchDaoImpl;
import org.proj4.tennis_scoreboard.dao.impl.PlayerDaoImpl;
import org.proj4.tennis_scoreboard.dto.MatchDto;
import org.proj4.tennis_scoreboard.dto.pagination.PaginatedResult;
import org.proj4.tennis_scoreboard.entity.Match;
import org.proj4.tennis_scoreboard.entity.Player;
import org.proj4.tennis_scoreboard.mapper.MatchMapper;

import java.util.List;
import java.util.Optional;

public class MatchService {
    private static final int DEFAULT_PAGE_SIZE = 5;
    private final MatchDao matchDao;
    private final PlayerDao playerDao;

    public MatchService() {
        this(new MatchDaoImpl(), new PlayerDaoImpl());
    }

    public MatchService(MatchDao matchDao, PlayerDao playerDao) {
        this.matchDao = matchDao;
        this.playerDao = playerDao;
    }

    public PaginatedResult<MatchDto> getMatchesPaginated(int currentPage, int pageSize) {
        if (currentPage < 1) {
            currentPage = 1;
        }

        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        List<Match> allMatches = matchDao.getAllMatches(currentPage, pageSize);
        List<MatchDto> items = MatchMapper.INSTANCE.toDtoList(allMatches);

        int totalItems = matchDao.countAll();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        return new PaginatedResult<>(items, currentPage, totalPages, totalItems);
    }

    public Optional<PaginatedResult<MatchDto>> getMatchesByPlayer(String playerName, int currentPage, int pageSize) {
        if (currentPage < 1) {
            currentPage = 1;
        }

        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        int totalItems = 0;
        List<Match> matches;

        List<Player> players = playerDao.findByNameLike(playerName);

        if (players.isEmpty()) {
            return Optional.empty();
        }

        matches = matchDao.findByPlayers(players, currentPage, pageSize);
        totalItems = matchDao.countAllByPlayers(players);

        List<MatchDto> items = MatchMapper.INSTANCE.toDtoList(matches);
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        return Optional.of(new PaginatedResult<>(items, currentPage, totalPages, totalItems));
    }
}
