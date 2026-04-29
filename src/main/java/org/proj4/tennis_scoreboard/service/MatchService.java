package org.proj4.tennis_scoreboard.service;

import org.proj4.tennis_scoreboard.Exception.NotFoundException;
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
    private final MatchDao matchDao = new MatchDaoImpl();
    private final PlayerDao playerDao = new PlayerDaoImpl();

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

    public PaginatedResult<MatchDto> getMatchesByPlayer(String playerName, int currentPage, int pageSize) {
        if (currentPage < 1) {
            currentPage = 1;
        }

        int totalItems = 0;
        List<Match> matches;

        Optional<Player> optionalPlayer = playerDao.findByName(playerName);

        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();

            matches = matchDao.findByPlayer(player, currentPage, pageSize);
            totalItems = matchDao.countAllByPlayer(player);

            List<MatchDto> items = MatchMapper.INSTANCE.toDtoList(matches);
            int totalPages = (int) Math.ceil((double) totalItems / pageSize);

            return new PaginatedResult<>(items, currentPage, totalPages, totalItems);

        } else {
            throw new NotFoundException("Player not found");
        }
    }
}
