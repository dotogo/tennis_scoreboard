package org.proj4.tennis_scoreboard.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/matches")
public class MatchesServlet extends BaseServlet {
    private static final String PARAM_PAGE = "page";
    private static final String PARAM_NAME_FILTER = "filter_by_player_name";

    private static final String REQUIRED_PARAMETERS_MISSING = "One or more parameters have invalid names or are missing. " +
                                                              "Required parameters: \"%s\", \"%s\"".formatted(PARAM_PAGE, PARAM_NAME_FILTER);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();

        if (!parameterMap.containsKey(PARAM_PAGE) || !parameterMap.containsKey(PARAM_NAME_FILTER)) {
            sendErrorResponse(resp, REQUIRED_PARAMETERS_MISSING);
            return;
        }

        String page = parameterMap.get(PARAM_PAGE)[0];
        String playerName = parameterMap.get(PARAM_NAME_FILTER)[0];

        // TODO make page validation
        Integer pageValue = Integer.valueOf(page.trim());

        if (page.trim().isEmpty()) {
            pageValue = 1;
        }
    }
}
