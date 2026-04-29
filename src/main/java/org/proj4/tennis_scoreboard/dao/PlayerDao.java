package org.proj4.tennis_scoreboard.dao;

import org.proj4.tennis_scoreboard.entity.Player;

import java.util.Optional;

public interface PlayerDao extends Dao<Player> {

    Optional<Player> findByName(String name);


}
