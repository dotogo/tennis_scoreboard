package org.proj4.tennis_scoreboard.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.proj4.tennis_scoreboard.entity.Match;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class MatchesServlet extends BaseServlet {
    private static final String PARAM_PAGE = "page";
    private static final String PARAM_NAME_FILTER = "filter_by_player_name";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pageParam = req.getParameter(PARAM_PAGE);
        String nameParam = req.getParameter(PARAM_NAME_FILTER);

        int page = 1;
        page = parsePageParameterIfExists(pageParam, resp);

        List<Match> matches = null;

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("Page coming soon. \n Completed matches");
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
