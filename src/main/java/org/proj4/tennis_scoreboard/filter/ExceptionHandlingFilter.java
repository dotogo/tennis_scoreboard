package org.proj4.tennis_scoreboard.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.proj4.tennis_scoreboard.exception.InvalidParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter("/*")
public class ExceptionHandlingFilter extends HttpFilter {
    private static final String ATTR_ERROR_MESSAGE = "error_message";
    private static final String EXCEPTION_MESSAGE = "OOPS! Something went wrong :(";

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlingFilter.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(req, res);

        } catch (InvalidParameterException e) {
            sendError(req, res, e, e.getMessage());

        } catch (Exception e) {
            sendError(req, res, e, EXCEPTION_MESSAGE);
        }
    }

    private static void sendError(HttpServletRequest req, HttpServletResponse res, Exception e, String message) throws ServletException, IOException {
        log.error(e.getMessage(), e);

        req.setAttribute(ATTR_ERROR_MESSAGE, message);
        req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, res);
    }
}
