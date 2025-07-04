package org.proj4.tennis_scoreboard.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.proj4.tennis_scoreboard.entity.Match;
import org.proj4.tennis_scoreboard.entity.Player;
import org.proj4.tennis_scoreboard.util.HibernateUtil;

import java.util.List;

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

    public List<Match> getAllMatches(int page, int pageSize) {
        Transaction transaction = null;
        List<Match> matches = List.of();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            int offset = (page - 1) * pageSize;
            Query<Match> query = session.createQuery("from Match order by id", Match.class);
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);

            matches = query.getResultList();

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();

                // TODO make custom exception
                throw new RuntimeException("Error while getting all matches", e);
            }
        }
        return matches;
    }

    public List<Match> findByPlayer(Player player, int page, int pageSize) {
        Transaction transaction = null;
        List<Match> matches = List.of();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            int offset = (page - 1) * pageSize;
            String hql = "from Match where firstPlayer = :player or secondPlayer = :player order by id";
            Query<Match> query = session.createQuery(hql, Match.class);
            query.setParameter("player", player);
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);

            matches = query.getResultList();

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();

                // TODO make custom exception
                throw new RuntimeException("Error while finding matches by player", e);
            }
        }
        return matches;
    }
}
