import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.proj4.tennis_scoreboard.entity.Match;
import org.proj4.tennis_scoreboard.entity.Player;
import org.proj4.tennis_scoreboard.util.HibernateUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class HibernateTest {

    @BeforeEach
    public void cleanup() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createMutationQuery("DELETE FROM Match").executeUpdate();
            session.createMutationQuery("DELETE FROM Player").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    public void testSaveAndGetPlayer() {
        Player player = new Player();
        player.setName("Novak Djokovic");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(player);
            session.getTransaction().commit();
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Player savedPlayer = session.get(Player.class, player.getId());

            assertThat(savedPlayer.getName()).isEqualTo("Novak Djokovic");
            assertThat(savedPlayer.getId()).isEqualTo(player.getId());

            System.out.println("Player saved with id: " + savedPlayer.getId());

            session.getTransaction().commit();
        }
    }

    @Test
    public void testSaveAndGetMatch() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Player player1 = new Player();
            player1.setName("Novak Djokovic");

            Player player2 = new Player();
            player2.setName("Federer");

            Match match = new Match();
            match.setFirstPlayer(player1);
            match.setSecondPlayer(player2);
            match.setWinner(player1);

            session.beginTransaction();

            session.persist(player1);
            session.persist(player2);
            session.persist(match);

            session.getTransaction().commit();
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Match match = session.get(Match.class, 1);
            String firstPlayerName = match.getFirstPlayer().getName();
            String secondPlayerName = match.getSecondPlayer().getName();
            String winner = match.getWinner().getName();

            System.out.println("First player name: " + firstPlayerName);
            System.out.println("Second player name: " + secondPlayerName);
            System.out.println("Winner name: " + winner);

            session.getTransaction().commit();

        }
    }

}
