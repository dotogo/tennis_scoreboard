package org.proj4.tennis_scoreboard.service;

import org.proj4.tennis_scoreboard.dao.PlayerDao;
import org.proj4.tennis_scoreboard.dao.impl.PlayerDaoImpl;
import org.proj4.tennis_scoreboard.entity.Player;
import org.proj4.tennis_scoreboard.exception.InvalidParameterException;
import org.proj4.tennis_scoreboard.util.Validator;

import java.util.List;

public class PlayerService {
    private static final String INVALID_PLAYER_NAME = "Invalid player name. \n Alphanumeric (Eng/Rus), space and the characters _.`- are allowed. " +
                                                      "\n Profanity is prohibited.";
    private static final String TOO_MUCH_SIMILAR_NAMES = "Invalid player name. The names are too similar.";
    private final PlayerDao playerDao;

    public PlayerService() {
        this(new PlayerDaoImpl());
    }

    public PlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public List<Player> findOrCreate(String firstPlayerName, String secondPlayerName) {
        validatePlayerNames(firstPlayerName, secondPlayerName);

        Player firstPlayer = findOrCreate(firstPlayerName);
        Player secondPlayer = findOrCreate(secondPlayerName);
        return List.of(firstPlayer, secondPlayer);

    }

    private Player findOrCreate(String playerName) {
        return playerDao.findByName(playerName)
                .orElseGet(() -> {
                    Player newPlayer = new Player();
                    newPlayer.setName(playerName);
                    return playerDao.save(newPlayer);
                });
    }

    private void validatePlayerNames(String firstPlayerName, String secondPlayerName) {
        if (!Validator.isValidName(firstPlayerName) || !Validator.isValidName(secondPlayerName)) {
            throw new InvalidParameterException(INVALID_PLAYER_NAME);
        }

        if (Validator.isTwoNamesTooMuchSimilar(firstPlayerName, secondPlayerName)) {
            throw new InvalidParameterException(TOO_MUCH_SIMILAR_NAMES);
        }
    }
}
