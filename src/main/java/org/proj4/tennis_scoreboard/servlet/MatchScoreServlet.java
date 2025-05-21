package org.proj4.tennis_scoreboard.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.proj4.tennis_scoreboard.entity.OngoingMatch;
import org.proj4.tennis_scoreboard.service.OngoingMatchesService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends BaseServlet {
    private static final String PARAM_UUID = "uuid";
    private static final String ATTR_MATCH = "match";

    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidFromParameter = req.getParameter(PARAM_UUID);
        UUID uuid = UUID.fromString(uuidFromParameter);

        OngoingMatch match = ongoingMatchesService.getMatch(uuid);
        if (match == null) {
            System.out.println("Match not found");
        } else {
            System.out.println("Match found");
        }
        req.setAttribute(ATTR_MATCH, match);

//        resp.getWriter().write(uuid.toString());
        req.getRequestDispatcher("/WEB-INF/views/match-score.jsp").forward(req, resp);
    }
}
