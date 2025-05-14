package org.proj4.tennis_scoreboard.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.proj4.tennis_scoreboard.dao.PlayerDao;

import java.io.IOException;
import java.util.Map;

@WebServlet("/new-match")
public class MatchServlet extends BaseServlet {
    private static final String FIRST_PLAYER = "first-player";
    private static final String SECOND_PLAYER = "second-player";

    private static final String REQUIRED_PARAMETERS_MISSING = "One or more parameters have invalid names or are missing. " +
                                                              "Required parameters: \"%s\", \"%s\"".formatted(FIRST_PLAYER, SECOND_PLAYER);

    private static final String FIRST_PLAYER_EMPTY = "First player name cannot be empty.";
    private static final String SECOND_PLAYER_EMPTY = "Second player name cannot be empty.";
    private static final String SAME_PLAYER_NAMES = "Player names cannot be the same.";

    private final PlayerDao playerDao = new PlayerDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();

        if (!parameterMap.containsKey(FIRST_PLAYER) || !parameterMap.containsKey(SECOND_PLAYER)) {

            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, REQUIRED_PARAMETERS_MISSING);
            return;
        }

        String firstPlayer = parameterMap.get(FIRST_PLAYER)[0];
        String secondPlayer = parameterMap.get(SECOND_PLAYER)[0];

        if (firstPlayer.trim().isEmpty()) {
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, FIRST_PLAYER_EMPTY);
            return;
        }

        if (secondPlayer.trim().isEmpty()) {
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, SECOND_PLAYER_EMPTY);
            return;
        }

        if (firstPlayer.equals(secondPlayer)) {
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, SAME_PLAYER_NAMES);
            return;
        }



    }
}
