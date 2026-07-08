package org.proj4.tennis_scoreboard.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.proj4.tennis_scoreboard.dto.MatchScoreDto;
import org.proj4.tennis_scoreboard.util.Validator;
import org.proj4.tennis_scoreboard.model.OngoingMatch;
import org.proj4.tennis_scoreboard.service.FinishedMatchesPersistenceService;
import org.proj4.tennis_scoreboard.service.MatchScoreCalculationService;
import org.proj4.tennis_scoreboard.service.OngoingMatchesService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {
    private static final String PARAM_UUID = "uuid";
    private static final String ATTR_MATCH_SCORE_DTO = "matchScoreDto";
    private static final String ATTR_FIRST_PLAYER = "firstPlayer";
    private static final String ATTR_SECOND_PLAYER = "secondPlayer";
    private static final String PARAM_POINT_WINNER = "point-winner";
    private static final String INVALID_UUID = "Invalid UUID";
    private static final String INVALID_PLAYER_ID = "Invalid player ID";
    private static final String ERROR_GETTING_MATCH = "Something went wrong while getting ongoing match or a match with\n" +
                                                      "UUID = %s does not exist";
    private static final String ATTR_ERROR_MESSAGE = "error_message";

    private OngoingMatchesService ongoingMatchesService;
    private MatchScoreCalculationService matchScoreCalculationService;
    private FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    public MatchScoreServlet() {

    }

    public MatchScoreServlet(OngoingMatchesService ongoingMatchesService,
                             MatchScoreCalculationService matchScoreCalculationService,
                             FinishedMatchesPersistenceService finishedMatchesPersistenceService ) {

        this.ongoingMatchesService = ongoingMatchesService;
        this.matchScoreCalculationService = matchScoreCalculationService;
        this.finishedMatchesPersistenceService = finishedMatchesPersistenceService;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ServletContext servletContext = getServletContext();

        if (ongoingMatchesService == null) {
            this.ongoingMatchesService = (OngoingMatchesService) servletContext.getAttribute("ongoingMatchesService");
        }

        if (matchScoreCalculationService == null) {
            this.matchScoreCalculationService = (MatchScoreCalculationService) servletContext.getAttribute("matchScoreCalculationService");
        }

        if (finishedMatchesPersistenceService == null) {
            this.finishedMatchesPersistenceService = (FinishedMatchesPersistenceService) servletContext.getAttribute("finishedMatchesPersistenceService");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String uuidFromParameter = req.getParameter(PARAM_UUID);

        if (!Validator.isValidUuid(uuidFromParameter)) {
            sendErrorForward(req, resp, INVALID_UUID);
            return;
        }

        UUID uuid = UUID.fromString(uuidFromParameter);

        Optional<OngoingMatch> matchOptional = ongoingMatchesService.getMatch(uuid);

        if (matchOptional.isPresent()) {
            OngoingMatch ongoingMatch = matchOptional.get();
            MatchScoreDto matchScoreDto = ongoingMatch.getMatchScoreDto();

            req.setAttribute(ATTR_MATCH_SCORE_DTO, matchScoreDto);
            req.setAttribute(ATTR_FIRST_PLAYER, ongoingMatch.getFirstPlayer());
            req.setAttribute(ATTR_SECOND_PLAYER, ongoingMatch.getSecondPlayer());
            req.getRequestDispatcher("/WEB-INF/views/match-score.jsp").forward(req, resp);
        }

        if (matchOptional.isEmpty()) {
            sendErrorForward(req, resp, ERROR_GETTING_MATCH.formatted(uuidFromParameter));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidFromParameter = req.getParameter(PARAM_UUID);

        if (!Validator.isValidUuid(uuidFromParameter)) {
            sendErrorForward(req, resp, INVALID_UUID);
            return;
        }

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
        int playerId;

        try {
            playerId = Integer.parseInt(pointWinner);
        } catch (NumberFormatException e) {
            sendErrorForward(req, resp, INVALID_PLAYER_ID);
            return;
        }

        match.addPoint(playerId);

        if (match.isFinished()) {
            finishedMatchesPersistenceService.persistMatch(match);
            ongoingMatchesService.deleteMatch(uuid);

            req.setAttribute(ATTR_MATCH_SCORE_DTO, match.getMatchScoreDto());
            req.setAttribute(ATTR_FIRST_PLAYER, match.getFirstPlayer());
            req.setAttribute(ATTR_SECOND_PLAYER, match.getSecondPlayer());
            req.getRequestDispatcher("/WEB-INF/views/final-score.jsp").forward(req, resp);
            return;
        }

        req.setAttribute(ATTR_MATCH_SCORE_DTO, match.getMatchScoreDto());
        req.setAttribute(ATTR_FIRST_PLAYER, match.getFirstPlayer());
        req.setAttribute(ATTR_SECOND_PLAYER, match.getSecondPlayer());
        req.getRequestDispatcher("/WEB-INF/views/match-score.jsp").forward(req, resp);
    }

    private void sendErrorForward(HttpServletRequest req, HttpServletResponse resp, Object attribute) throws IOException, ServletException {
        req.setAttribute(ATTR_ERROR_MESSAGE, attribute);
        req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
    }
}
