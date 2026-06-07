package org.proj4.tennis_scoreboard;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.proj4.tennis_scoreboard.dao.MatchDao;
import org.proj4.tennis_scoreboard.dao.PlayerDao;
import org.proj4.tennis_scoreboard.dao.impl.MatchDaoImpl;
import org.proj4.tennis_scoreboard.dao.impl.PlayerDaoImpl;
import org.proj4.tennis_scoreboard.service.*;
import org.proj4.tennis_scoreboard.util.HibernateUtil;

import java.util.concurrent.atomic.AtomicBoolean;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        MatchService matchService = new MatchService();
        OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
        FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();
        SampleMatchesService sampleMatchesService = new SampleMatchesService();
        PlayerService playerService = new PlayerService();
        PlayerDao playerDao = new PlayerDaoImpl();
        MatchDao matchDao = new MatchDaoImpl();
        AtomicBoolean canLaunch = new AtomicBoolean(true);

        ServletContext context = sce.getServletContext();
        context.setAttribute("matchService", matchService);
        context.setAttribute("ongoingMatchesService", ongoingMatchesService);
        context.setAttribute("matchScoreCalculationService", matchScoreCalculationService);
        context.setAttribute("finishedMatchesPersistenceService", finishedMatchesPersistenceService);
        context.setAttribute("sampleMatchesService", sampleMatchesService);
        context.setAttribute("playerService", playerService);
        context.setAttribute("playerDao", playerDao);
        context.setAttribute("matchDao", matchDao);
        context.setAttribute("sampleMatchesAvailable", canLaunch);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateUtil.getSessionFactory().close();
    }
}
