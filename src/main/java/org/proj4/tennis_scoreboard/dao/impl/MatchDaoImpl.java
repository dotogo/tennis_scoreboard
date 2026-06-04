package org.proj4.tennis_scoreboard.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.proj4.tennis_scoreboard.dao.MatchDao;
import org.proj4.tennis_scoreboard.entity.Match;
import org.proj4.tennis_scoreboard.entity.Player;
import org.proj4.tennis_scoreboard.exception.DaoException;
import org.proj4.tennis_scoreboard.util.HibernateUtil;

import java.util.List;

public class MatchDaoImpl implements MatchDao {
    private static final String ERROR_SAVE_ALL_MATCHES = "Error saving all matches.";
    private static final String ERROR_SAVE_MATCH = "Error saving match.";
    private static final String ERROR_GETTING_ALL_MATCHES = "Error getting all matches.";
    private static final String ERROR_FIND_BY_PLAYERS = "Error searching matches by players.";
    private static final String ERROR_COUNT_ALL = "Error counting all matches.";
    private static final String ERROR_COUNT_ALL_BY_PLAYER = "Error counting all matches by player.";

    public Match save(Match match) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                session.persist(match);
                session.getTransaction().commit();

            } catch (Exception e) {
                handleException(e, session.getTransaction(), ERROR_SAVE_MATCH);
            }
        }
        return match;
    }

    public List<Match> saveAll(List<Match> matches) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();

                for (Match match : matches) {
                    session.persist(match);
                }

                session.getTransaction().commit();

            } catch (Exception e) {
                handleException(e, session.getTransaction(), ERROR_SAVE_ALL_MATCHES);
            }
            return matches;
        }
    }

    public List<Match> getAllMatches(int page, int pageSize) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            int offset = (page - 1) * pageSize;

            String hql = """
                    select m from Match m
                    join fetch m.firstPlayer p1
                    join fetch m.secondPlayer p2
                    join fetch m.winner winner
                    order by m.id DESC
                    
                    """;
            Query<Match> query = session.createQuery(hql, Match.class);
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);

            return query.getResultList();

        } catch (Exception e) {
            throw new DaoException(ERROR_GETTING_ALL_MATCHES, e);
        }
    }

    public List<Match> findByPlayers(List<Player> players, int page, int pageSize) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            int offset = (page - 1) * pageSize;

            String hql = """
                        select m from Match m
                        join fetch m.firstPlayer p1
                        join fetch m.secondPlayer p2
                        join fetch m.winner winner
                        where m.firstPlayer IN :players
                        or m.secondPlayer IN :players
                        order by m.id DESC
                        """;
            Query<Match> query = session.createQuery(hql, Match.class);
            query.setParameter("players", players);
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);

            return query.getResultList();

        } catch (Exception e) {
            throw new DaoException(ERROR_FIND_BY_PLAYERS, e);
        }
    }

    public int countAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select count(m) from Match m";
            Query<Long> query = session.createQuery(hql, Long.class);

            return query.getSingleResult().intValue();

        } catch (Exception e) {
            throw new DaoException(ERROR_COUNT_ALL, e);
        }
    }

    public int countAllByPlayers(List<Player> players) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = """
                        select count(m) from Match m
                        where firstPlayer IN :players
                        or secondPlayer IN :players
                        """;
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("players", players);

            return query.getSingleResult().intValue();

        } catch (Exception e) {
            throw new DaoException(ERROR_COUNT_ALL_BY_PLAYER, e);
        }
    }

    private void handleException(Exception e, Transaction transaction, String message) {
        if (transaction != null && transaction.getStatus().canRollback()) {
            transaction.rollback();
        }
        throw new DaoException(message, e);
    }
}
