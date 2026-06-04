package org.proj4.tennis_scoreboard.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.proj4.tennis_scoreboard.dao.PlayerDao;
import org.proj4.tennis_scoreboard.entity.Player;
import org.proj4.tennis_scoreboard.exception.DaoException;
import org.proj4.tennis_scoreboard.exception.EntityAlreadyExistsException;
import org.proj4.tennis_scoreboard.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class PlayerDaoImpl implements PlayerDao {

    private static final String ERROR_SAVE_ALL_PLAYERS = "Error saving all players.";
    private static final String ERROR_SAVE_PLAYER = "Error saving player.";
    private static final String ERROR_FINDING_BY_NAME = "Error searching for a player by name.";
    private static final String ERROR_FINDING_BY_NAME_LIKE = "Error searching for a player by part of name.";

    public Optional<Player> findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Player> query = session.createQuery("from Player where name = :name", Player.class);
            query.setParameter("name", name);

            return query.uniqueResultOptional();

        } catch (Exception e) {
            throw new DaoException(ERROR_FINDING_BY_NAME, e);
        }
    }

    public List<Player> findByNameLike(String nameLike) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Player> query = session.createQuery("from Player where lower(name) LIKE lower(:nameLike)", Player.class);
            query.setParameter("nameLike", "%" + nameLike.trim().toLowerCase() + "%");

            return query.list();

        } catch (Exception e) {
            throw new DaoException(ERROR_FINDING_BY_NAME_LIKE, e);
        }
    }

    public Player save(Player player) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                session.persist(player);
                session.getTransaction().commit();

            } catch (Exception e) {
                handleException(e,session.getTransaction(), ERROR_SAVE_PLAYER);
            }
            return player;
        }
    }

    public List<Player> saveAll(List<Player> players) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                for (Player player : players) {
                    session.persist(player);
                }
                session.getTransaction().commit();

            } catch (Exception e) {
                handleException(e,session.getTransaction(), ERROR_SAVE_ALL_PLAYERS);
            }
            return players;
        }
    }

    private void handleException(Exception e, Transaction transaction, String message) {
        if (e instanceof ConstraintViolationException) {

            if (transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            throw new EntityAlreadyExistsException(e);
        }

        if (transaction != null && transaction.getStatus().canRollback()) {
            transaction.rollback();
        }
        throw new DaoException(message, e);
    }
}
