package org.proj4.tennis_scoreboard.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.proj4.tennis_scoreboard.service.SampleMatchesService;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@WebServlet ("/sample-matches")
public class SampleMatchesServlet extends HttpServlet {
    private static final AtomicBoolean canLaunch = new AtomicBoolean(true);
    private SampleMatchesService sampleMatchesService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.sampleMatchesService = (SampleMatchesService) getServletContext().getAttribute("sampleMatchesService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (canLaunch.compareAndSet(true, false)) {
            int numberOfMatches = 50;
            sampleMatchesService.saveSampleMatches(numberOfMatches);
        }

        resp.sendRedirect(req.getContextPath() + "/matches");
    }
}
