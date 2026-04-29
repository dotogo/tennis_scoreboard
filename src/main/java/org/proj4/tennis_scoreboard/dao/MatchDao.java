package org.proj4.tennis_scoreboard.dao;

import org.proj4.tennis_scoreboard.entity.Match;
import org.proj4.tennis_scoreboard.entity.Player;

import java.util.List;

public interface MatchDao extends Dao<Match> {

    List<Match> getAllMatches(int page, int pageSize);

    List<Match> findByPlayer(Player player, int page, int pageSize);

    int countAll();

    int countAllByPlayer(Player player);


}
