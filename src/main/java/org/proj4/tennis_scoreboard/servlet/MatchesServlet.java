package org.proj4.tennis_scoreboard.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.proj4.tennis_scoreboard.dto.MatchDto;
import org.proj4.tennis_scoreboard.dto.pagination.PaginatedResult;
import org.proj4.tennis_scoreboard.service.MatchService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/matches")
public class MatchesServlet extends BaseServlet {
    private static final String PARAM_PAGE = "page";
    private static final String PARAM_NAME_FILTER = "filter_by_player_name";
    private static final String PLAYER_NOT_FOUND = "Player not found";
    private static final int PAGE_SIZE = 5;
    private static final int RANGE_PLUS_MINUS_PAGES_FOR_PAGINATION = 2;

    private MatchService matchService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.matchService = (MatchService) getServletContext().getAttribute("matchService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String pageParam = req.getParameter(PARAM_PAGE);
        String nameParam = req.getParameter(PARAM_NAME_FILTER);

        PaginatedResult<MatchDto> matches;

        int currentPage = getPageNumber(pageParam);

        if (nameParam == null || nameParam.isEmpty()) {
            matches = matchService.getMatchesPaginated(currentPage, PAGE_SIZE);
        } else {
            Optional<PaginatedResult<MatchDto>> matchesByPlayer = matchService.getMatchesByPlayer(nameParam, currentPage, PAGE_SIZE);

            if (matchesByPlayer.isPresent()) {
                matches = matchesByPlayer.get();
                req.setAttribute(PARAM_NAME_FILTER, nameParam.trim());
            } else {
                matches = matchService.getMatchesPaginated(currentPage, PAGE_SIZE);
                req.setAttribute(PARAM_NAME_FILTER, PLAYER_NOT_FOUND);
            }
        }

        int totalPages = matches.getTotalPages();
        List<Integer> pageRange = getPageRange(currentPage, totalPages, RANGE_PLUS_MINUS_PAGES_FOR_PAGINATION);

        req.setAttribute("pageRange", pageRange);
        req.setAttribute("matches", matches);
        req.getRequestDispatcher("/WEB-INF/views/matches.jsp").forward(req, resp);
    }

    int getPageNumber(String pageParam)  {
        if (pageParam == null || pageParam.isBlank()) {
            return 1;
        }

        try {
            int page = Integer.parseInt(pageParam.trim());
            return Math.max(1, page);

        } catch (NumberFormatException e) {
            return 1;
        }
    }

    List<Integer> getPageRange(int currentPage, int totalPages, int deviatiion) {
        List<Integer> pageRange = new ArrayList<>();
        int min = Math.max(1, currentPage - deviatiion);
        int max = Math.min(totalPages, currentPage + deviatiion);

        for (int i = min; i <= max; i++) {
            pageRange.add(i);
        }
        return pageRange;
    }
}
