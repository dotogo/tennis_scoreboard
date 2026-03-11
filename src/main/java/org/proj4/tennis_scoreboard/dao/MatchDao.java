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

            String hql = """
                    select m from Match m
                    join fetch m.firstPlayer p1
                    join fetch m.secondPlayer p2
                    join fetch m.winner winner
                    order by m.id
                    
                    """;
            Query<Match> query = session.createQuery(hql, Match.class);
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

    public long countAll() {
        Transaction transaction = null;
        long count = 0;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "select count(m) from Match m";
            Query<Long> query = session.createQuery(hql, Long.class);
            count = query.getSingleResult();

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();

                // TODO make custom exception
                throw new RuntimeException("Error while counting all matches", e);
            }
        }
        return count;
    }
}
