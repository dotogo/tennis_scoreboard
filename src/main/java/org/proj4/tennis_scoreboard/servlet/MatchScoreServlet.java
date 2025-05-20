package org.proj4.tennis_scoreboard.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends BaseServlet {
    private static final String PARAM_UUID = "uuid";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidFromParameter = req.getParameter(PARAM_UUID);
        UUID uuid = UUID.fromString(uuidFromParameter);

//        resp.setContentType("text/html");
        resp.getWriter().write(uuid.toString());
    }
}
