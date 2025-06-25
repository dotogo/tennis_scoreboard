package org.proj4.tennis_scoreboard.dao;

import org.hibernate.Session;
import org.proj4.tennis_scoreboard.entity.Match;
import org.proj4.tennis_scoreboard.util.HibernateUtil;

public class MatchDao {

    public Match save(Match match) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            session.persist(match);
            session.getTransaction().commit();

        } catch (Exception e) {
            // TODO make custom exception
            e.printStackTrace();
        }
        return match;
    }
}
