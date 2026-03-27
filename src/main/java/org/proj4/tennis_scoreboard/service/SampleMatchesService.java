package org.proj4.tennis_scoreboard.service;

import org.proj4.tennis_scoreboard.dao.MatchDao;
import org.proj4.tennis_scoreboard.dao.PlayerDao;
import org.proj4.tennis_scoreboard.entity.Match;
import org.proj4.tennis_scoreboard.entity.Player;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SampleMatchesService {
    private static final String SAMPLE_PLAYER_NAMES = "samplePlayerNames.txt";

    private final MatchDao matchDao = new MatchDao();
    private final PlayerDao playerDao = new PlayerDao();

    public void saveSampleMatches(int numberOfMatches) {
        List<String> playerNames = readPlayerNames(SAMPLE_PLAYER_NAMES);
        List<Player> players = saveSamplePlayers(playerNames);
        List<Match> matches = generateSampleMatches(players, numberOfMatches);
        matchDao.saveAll(matches);
    }

    private List<String> readPlayerNames(String fileName) {
         try (InputStream is = SampleMatchesService.class.getClassLoader().getResourceAsStream(fileName)) {
             if (is == null) {
                 throw new FileNotFoundException("File not found: " + fileName);
             }

             try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                 List<String> playerNames = new ArrayList<>();
                 String line;
                 while ((line = br.readLine()) != null) {
                     playerNames.add(line);
                 }
                 return playerNames;
             }
         } catch (IOException e) {
             throw new RuntimeException("Can not read sample player names.", e);
         }
    }

    private List<Player> saveSamplePlayers(List<String> playerNames) {
        checkListForEmpty(playerNames);

        List<Player> players = new ArrayList<>();

        for (String playerName : playerNames) {
            Player player = new Player();
            player.setName(playerName);
            players.add(player);
        }
        return playerDao.saveAll(players);
    }

    private List<Match> generateSampleMatches(List<Player> players, int numberOfMatches) {
        checkListForEmpty(players);

        List<Match> matches = new ArrayList<>();

        for (int i = 0; i < numberOfMatches; i++) {
            Match match = new Match();
            List<Player> twoRandomPlayers = getTwoRandomPlayers(players);

            Player firstPlayer = twoRandomPlayers.get(0);
            Player secondPlayer = twoRandomPlayers.get(1);
            Player winner = getRandomPlayer(twoRandomPlayers);

            match.setFirstPlayer(firstPlayer);
            match.setSecondPlayer(secondPlayer);
            match.setWinner(winner);

            matches.add(match);
        }
        return matches;
    }

    private Player getRandomPlayer(List<Player> players) {
        checkListForEmpty(players);
        Collections.shuffle(players);
        return players.get(0);
    }

    private List<Player> getTwoRandomPlayers(List<Player> players) {
        checkListForEmpty(players);
        Collections.shuffle(players);
        return players.subList(0, 2);
    }

    private <T> void checkListForEmpty(List<T> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("The list cannot be empty");
        }
    }

}
