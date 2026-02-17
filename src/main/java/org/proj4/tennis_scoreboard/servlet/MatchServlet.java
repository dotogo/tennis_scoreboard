package org.proj4.tennis_scoreboard.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.proj4.tennis_scoreboard.dao.PlayerDao;
import org.proj4.tennis_scoreboard.entity.*;
import org.proj4.tennis_scoreboard.service.OngoingMatchesService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

@WebServlet("/new-match")
public class MatchServlet extends BaseServlet {
    private static final String PARAM_FIRST_PLAYER = "first-player";
    private static final String PARAM_SECOND_PLAYER = "second-player";

    private static final String REQUIRED_PARAMETERS_MISSING = "One or more parameters have invalid names or are missing. " +
                                                              "Required parameters: \"%s\", \"%s\"".formatted(PARAM_FIRST_PLAYER, PARAM_SECOND_PLAYER);

    private static final String FIRST_PLAYER_EMPTY = "First player name cannot be empty.";
    private static final String SECOND_PLAYER_EMPTY = "Second player name cannot be empty.";
    private static final String SAME_PLAYER_NAMES = "Player names cannot be the same.";

    public static final String ATTR_FIRST_PLAYER = "firstPlayer";
    public static final String ATTR_SECOND_PLAYER = "secondPlayer";

    private final PlayerDao playerDao = new PlayerDao();
    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();

        if (!parameterMap.containsKey(PARAM_FIRST_PLAYER) || !parameterMap.containsKey(PARAM_SECOND_PLAYER)) {

            sendErrorResponse(resp, REQUIRED_PARAMETERS_MISSING);
            return;
        }

        String firstPlayer = parameterMap.get(PARAM_FIRST_PLAYER)[0];
        String secondPlayer = parameterMap.get(PARAM_SECOND_PLAYER)[0];

        if (firstPlayer == null || firstPlayer.trim().isEmpty()) {
            sendErrorResponse(resp, FIRST_PLAYER_EMPTY);
            return;
        }

        if (secondPlayer == null || secondPlayer.trim().isEmpty()) {
            sendErrorResponse(resp, SECOND_PLAYER_EMPTY);
            return;
        }

        if (firstPlayer.trim().equals(secondPlayer.trim())) {
            sendErrorResponse(resp, SAME_PLAYER_NAMES);
            return;
        }

        try {
            Player first = playerDao.findByName(firstPlayer)
                    .orElseGet(() -> {
                        Player newPlayer = new Player();
                        newPlayer.setName(firstPlayer);
                        return playerDao.save(newPlayer);
                    });
//            req.setAttribute(ATTR_FIRST_PLAYER, first);

            Player second = playerDao.findByName(secondPlayer)
                    .orElseGet(() -> {
                        Player newPlayer = new Player();
                        newPlayer.setName(secondPlayer);
                        return playerDao.save(newPlayer);
                    });
//            req.setAttribute(ATTR_SECOND_PLAYER, second);
//            req.getRequestDispatcher("/WEB-INF/views/players.jsp").forward(req, resp);

            OngoingMatch ongoingMatch = new OngoingMatch(first, second, new PlayerScore(), new PlayerScore());
            UUID uuid = ongoingMatchesService.addMatch(ongoingMatch);

//            resp.sendRedirect("/match-score?uuid=" + uuid.toString());
            resp.sendRedirect("/match-score?uuid=" + URLEncoder.encode(uuid.toString(), StandardCharsets.UTF_8));


        } catch (Exception e) {
            sendErrorResponse(resp, e.getMessage());
        }

    }
}
