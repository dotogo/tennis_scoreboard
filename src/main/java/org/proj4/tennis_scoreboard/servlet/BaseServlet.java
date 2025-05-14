package org.proj4.tennis_scoreboard.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.proj4.tennis_scoreboard.dto.ErrorResponse;
import org.proj4.tennis_scoreboard.util.JsonUtil;

import java.io.IOException;

public class BaseServlet extends HttpServlet {

    protected void sendErrorResponse(HttpServletResponse resp, int status, String message) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(message);
        String json = JsonUtil.toJson(errorResponse);

        resp.setStatus(status);
        resp.getWriter().write(json);
    }

}
