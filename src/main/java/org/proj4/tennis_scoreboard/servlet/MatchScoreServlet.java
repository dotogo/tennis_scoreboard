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
import java.util.Optional;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends BaseServlet {
    private static final String PARAM_UUID = "uuid";
    private static final String ATTR_MATCH = "match";
    private static final String PARAM_POINT_WINNER = "point-winner";
    private static final String INVALID_UUID = "Invalid UUID";
    private static final String ERROR_GETTING_MATCH = "Something went wrong while getting ongoing match";

    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
    private final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String uuidFromParameter = req.getParameter(PARAM_UUID);
        checkUuid(resp, uuidFromParameter);
        UUID uuid = UUID.fromString(uuidFromParameter);

        Optional<OngoingMatch> matchOptional = ongoingMatchesService.getMatch(uuid);

        if (matchOptional.isPresent()) {
            req.setAttribute(ATTR_MATCH, matchOptional.get());
            req.getRequestDispatcher("/WEB-INF/views/match-score.jsp").forward(req, resp);
        }

        if (matchOptional.isEmpty()) {
            sendErrorResponse(resp, ERROR_GETTING_MATCH);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidFromParameter = req.getParameter(PARAM_UUID);
        checkUuid(resp, uuidFromParameter);
        UUID uuid = UUID.fromString(uuidFromParameter);

        Optional<OngoingMatch> matchOptional = ongoingMatchesService.getMatch(uuid);

        OngoingMatch match = null;

        if (matchOptional.isPresent()) {
            match = matchOptional.get();
        }

        if (match == null) {
            resp.sendRedirect("/matches");
            return;
        }

        String pointWinner = req.getParameter(PARAM_POINT_WINNER);

        matchScoreCalculationService.updateMatchScore(match, pointWinner);

        PlayerScore firstPlayerScore = match.getFirstPlayerScore();
        PlayerScore secondPlayerScore = match.getSecondPlayerScore();

        if (matchScoreCalculationService.isFinishedMatch(firstPlayerScore, secondPlayerScore)) {
            finishedMatchesPersistenceService.persistMatch(match);
            ongoingMatchesService.deleteMatch(uuid);

            req.setAttribute(ATTR_MATCH, match);
            req.getRequestDispatcher("/WEB-INF/views/final-score.jsp").forward(req, resp);
            return;
        }

        req.setAttribute(ATTR_MATCH, match);
        req.getRequestDispatcher("/WEB-INF/views/match-score.jsp").forward(req, resp);
    }

    private void checkUuid(HttpServletResponse resp, String uuidFromParameter) throws IOException {
        try {
            UUID.fromString(uuidFromParameter);
        } catch (IllegalArgumentException e) {
            sendErrorResponse(resp, INVALID_UUID);
        }
    }
}
