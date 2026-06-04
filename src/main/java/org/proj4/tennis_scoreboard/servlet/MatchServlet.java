package org.proj4.tennis_scoreboard.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.proj4.tennis_scoreboard.entity.*;
import org.proj4.tennis_scoreboard.service.OngoingMatchesService;
import org.proj4.tennis_scoreboard.service.PlayerService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet("/new-match")
public class MatchServlet extends HttpServlet {
    private static final String PARAM_FIRST_PLAYER = "first-player";
    private static final String PARAM_SECOND_PLAYER = "second-player";

    private static final String ATTR_ERROR_MESSAGE = "error_message";

    private static final String REQUIRED_PARAMETERS_MISSING = "One or more parameters have invalid names or are missing.\n " +
                                                              "Required parameters: \"%s\", \"%s\"".formatted(PARAM_FIRST_PLAYER, PARAM_SECOND_PLAYER);

    private static final String FIRST_PLAYER_EMPTY = "First player name cannot be empty.";
    private static final String SECOND_PLAYER_EMPTY = "Second player name cannot be empty.";
    private static final String SAME_PLAYER_NAMES = "Player names cannot be the same.";
    private static final String SOMETHING_WENT_WRONG = "OOPS! Something went wrong :(";

    private PlayerService playerService;
    private OngoingMatchesService ongoingMatchesService;

    public MatchServlet() {

    }

    public MatchServlet(PlayerService playerService, OngoingMatchesService ongoingMatchesService) {
        this.playerService = playerService;
        this.ongoingMatchesService = ongoingMatchesService;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();

        if (playerService == null) {
            this.playerService = (PlayerService) servletContext.getAttribute("playerService");
        }

        if (ongoingMatchesService == null) {
            this.ongoingMatchesService = (OngoingMatchesService) servletContext.getAttribute("ongoingMatchesService");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Map<String, String[]> parameterMap = req.getParameterMap();

        if (!parameterMap.containsKey(PARAM_FIRST_PLAYER) || !parameterMap.containsKey(PARAM_SECOND_PLAYER)) {

            sendErrorForward(req, resp, REQUIRED_PARAMETERS_MISSING);
            return;
        }

        String firstPlayerName = parameterMap.get(PARAM_FIRST_PLAYER)[0];
        String secondPlayerName = parameterMap.get(PARAM_SECOND_PLAYER)[0];

        if (firstPlayerName.isBlank()) {
            sendErrorForward(req, resp, FIRST_PLAYER_EMPTY);
            return;
        }

        if (secondPlayerName.isBlank()) {
            sendErrorForward(req, resp, SECOND_PLAYER_EMPTY);
            return;
        }

        if (firstPlayerName.trim().equals(secondPlayerName.trim())) {
            sendErrorForward(req, resp, SAME_PLAYER_NAMES);
            return;
        }

        List<Player> players = playerService.findOrCreate(firstPlayerName, secondPlayerName);
        Player first = players.get(0);
        Player second = players.get(1);

        OngoingMatch ongoingMatch = new OngoingMatch(first, second, new PlayerScore(), new PlayerScore());
        UUID uuid = ongoingMatchesService.addMatch(ongoingMatch);

        resp.sendRedirect("/match-score?uuid=" + URLEncoder.encode(uuid.toString(), StandardCharsets.UTF_8));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/new-match.jsp").forward(req, resp);
    }

    private void sendErrorForward(HttpServletRequest req, HttpServletResponse resp, Object attribute) throws IOException, ServletException {
        req.setAttribute(ATTR_ERROR_MESSAGE, attribute);
        req.getRequestDispatcher("/WEB-INF/views/new-match.jsp").forward(req, resp);
    }
}
