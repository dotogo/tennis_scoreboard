package org.proj4.tennis_scoreboard.service;

import org.proj4.tennis_scoreboard.dao.PlayerDao;
import org.proj4.tennis_scoreboard.dao.impl.PlayerDaoImpl;
import org.proj4.tennis_scoreboard.entity.Player;
import org.proj4.tennis_scoreboard.exception.InvalidParameterException;
import org.proj4.tennis_scoreboard.util.Validator;

public class PlayerService {
    private final PlayerDao playerDao;

    public PlayerService() {
        this(new PlayerDaoImpl());
    }

    public PlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public Player findOrCreate(String playerName) {
        if (!Validator.isValidName(playerName)) {
            throw new InvalidParameterException("Invalid player name. Player name must be alphanumeric (Eng/Rus) and may contain the characters _.`- and space.");
        }

        return playerDao.findByName(playerName)
                .orElseGet(() -> {
                    Player newPlayer = new Player();
                    newPlayer.setName(playerName);
                    return playerDao.save(newPlayer);
                });
    }
}
