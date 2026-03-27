package org.proj4.tennis_scoreboard.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.proj4.tennis_scoreboard.service.SampleMatchesService;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@WebServlet ("/sample-matches")
public class SampleMatchesServlet extends BaseServlet {
    private static final AtomicBoolean canLaunch = new AtomicBoolean(true);
    private final SampleMatchesService sampleMatchesService = new SampleMatchesService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (canLaunch.compareAndSet(true, false)) {
            int numberOfMatches = 50;
            sampleMatchesService.saveSampleMatches(numberOfMatches);
        }

        resp.sendRedirect(req.getContextPath() + "/matches");
    }
}
