package org.proj4.tennis_scoreboard.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.proj4.tennis_scoreboard.entity.Player;
import org.proj4.tennis_scoreboard.util.HibernateUtil;

import java.util.Optional;

public class PlayerDao {

    public Optional<Player> findByName(String name) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<Player> query = session.createQuery("from Player where name = :name", Player.class);
            query.setParameter("name", name);

            session.getTransaction().commit();
            return query.uniqueResultOptional();
        }
    }

    public Player save(Player player) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            session.persist(player);

            session.getTransaction().commit();
            return player;
        }
    }


}
