package org.proj4.tennis_scoreboard.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter("/*")
public class ExceptionHandlingFilter extends HttpFilter {
    private static final String ATTR_ERROR_MESSAGE = "error_message";
    private static final String ERROR_MESSAGE = "OOPS! Something went wrong :(";

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlingFilter.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(req, res);

        } catch (Exception e) {
            log.error(e.getMessage(), e);

            req.setAttribute(ATTR_ERROR_MESSAGE, ERROR_MESSAGE);
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, res);
        }
    }
}
