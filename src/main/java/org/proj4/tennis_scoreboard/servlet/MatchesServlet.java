package org.proj4.tennis_scoreboard.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.proj4.tennis_scoreboard.dto.MatchDto;
import org.proj4.tennis_scoreboard.service.MatchService;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class MatchesServlet extends BaseServlet {
    private static final String PARAM_PAGE = "page";
    private static final String PARAM_NAME_FILTER = "filter_by_player_name";
    private static final int PAGE_SIZE = 10;

    private final MatchService matchService = new MatchService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String pageParam = req.getParameter(PARAM_PAGE);
        String nameParam = req.getParameter(PARAM_NAME_FILTER);

        int page = 1;
        page = parsePageParameterIfExists(pageParam, resp);

        List<MatchDto> matches = matchService.getMatchesPaginated(page, PAGE_SIZE);

        req.setAttribute("matches", matches);
        req.getRequestDispatcher("/WEB-INF/views/matches.jsp").forward(req, resp);

    }

    private int parsePageParameterIfExists(String pageParam, HttpServletResponse resp) throws IOException {
        int page = 1;
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam.trim());
            } catch (NumberFormatException e) {
                sendErrorResponse(resp, "Page value must be an integer.");
            }
        }
        return page;
    }
}
