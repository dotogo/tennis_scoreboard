package org.proj4.tennis_scoreboard.dao;

import org.proj4.tennis_scoreboard.entity.Match;
import org.proj4.tennis_scoreboard.entity.Player;

import java.util.List;

public interface MatchDao extends Dao<Match> {

    List<Match> getAllMatches(int pageSize, int offset);

    List<Match> findByPlayers(List<Player> players, int pageSize, int offset);

    int countAll();

    int countAllByPlayers(List<Player> players);

}
