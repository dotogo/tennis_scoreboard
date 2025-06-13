package org.proj4.tennis_scoreboard.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.proj4.tennis_scoreboard.entity.OngoingMatch;
import org.proj4.tennis_scoreboard.entity.PlayerScore;
import org.proj4.tennis_scoreboard.service.FinishedMatchesPersistenceService;
import org.proj4.tennis_scoreboard.service.MatchScoreCalculationService;
import org.proj4.tennis_scoreboard.service.OngoingMatchesService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends BaseServlet {
    private static final String PARAM_UUID = "uuid";
    private static final String ATTR_MATCH = "match";
    private static final String PARAM_POINT_WINNER = "point-winner";
    private static final String INVALID_UUID = "Invalid UUID";

    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
    private final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidFromParameter = req.getParameter(PARAM_UUID);

        try {
            UUID.fromString(uuidFromParameter);
        } catch (IllegalArgumentException e) {
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, INVALID_UUID);
        }

        UUID uuid = UUID.fromString(uuidFromParameter);
        OngoingMatch match = ongoingMatchesService.getMatch(uuid);

        String pointWinner = req.getParameter(PARAM_POINT_WINNER);

        matchScoreCalculationService.updateMatchScore(match, pointWinner);

        PlayerScore firstPlayerScore = match.getFirstPlayerScore();
        PlayerScore secondPlayerScore = match.getSecondPlayerScore();

        if (matchScoreCalculationService.isFinishedMatch(firstPlayerScore, secondPlayerScore)) {
            finishedMatchesPersistenceService.persistMatch(match);

            req.setAttribute(ATTR_MATCH, match);
            req.getRequestDispatcher("/WEB-INF/views/final-score.jsp").forward(req, resp);

//            resp.getWriter().write("Match finished");
            return;
        }

        req.setAttribute(ATTR_MATCH, match);
        req.getRequestDispatcher("/WEB-INF/views/match-score.jsp").forward(req, resp);
    }
}
