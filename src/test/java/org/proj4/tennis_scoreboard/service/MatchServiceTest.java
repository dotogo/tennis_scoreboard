package org.proj4.tennis_scoreboard.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.proj4.tennis_scoreboard.dao.MatchDao;
import org.proj4.tennis_scoreboard.dao.PlayerDao;
import org.proj4.tennis_scoreboard.dto.MatchDto;
import org.proj4.tennis_scoreboard.dto.pagination.PaginatedResult;
import org.proj4.tennis_scoreboard.entity.Match;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {
    @Mock
    private MatchDao matchDao;
    @Mock
    private PlayerDao playerDao;
    @InjectMocks
    private MatchService matchService;

    @Test
    void checkPaginatedMatchesIfParametersAreValid() {
        int currentPage = 7;
        int pageSize = 3;

        List<Match> allMatches = List.of(new Match(), new Match(), new Match(), new Match(), new Match(), new Match(), new Match(), new Match());
        int totalItems = allMatches.size();
        int totalPages = 3;

        doReturn(allMatches).when(matchDao).getAllMatches(currentPage, pageSize);
        doReturn(totalItems).when(matchDao).countAll();

        PaginatedResult<MatchDto> result = matchService.getMatchesPaginated(currentPage, pageSize);

        assertAll(
                () -> assertThat(result.getTotalPages()).isEqualTo(totalPages),
                () -> assertThat(result.getCurrentPage()).isEqualTo(currentPage),
                () -> assertThat(result.getTotalItems()).isEqualTo(totalItems)
        );
    }

    @Test
    void checkPaginatedMatchesIfParametersAreNotValidAndUseDefaultPage1AndPageSize5() {
        int currentPage = 0;
        int pageSize = 0;

        List<Match> allMatches = List.of(new Match(), new Match(), new Match(), new Match(), new Match(), new Match(), new Match(), new Match());
        int totalItems = allMatches.size();
        int totalPages = 2;

        doReturn(allMatches).when(matchDao).getAllMatches(1, 5);
        doReturn(totalItems).when(matchDao).countAll();

        PaginatedResult<MatchDto> result = matchService.getMatchesPaginated(currentPage, pageSize);

        assertAll(
                () -> assertThat(result.getTotalPages()).isEqualTo(totalPages),
                () -> assertThat(result.getCurrentPage()).isEqualTo(1),
                () -> assertThat(result.getTotalItems()).isEqualTo(totalItems)
        );
    }

}
