package org.proj4.tennis_scoreboard.model;

import org.proj4.tennis_scoreboard.entity.Player;

public record OngoingPlayer(int id, String name) {

    public static OngoingPlayer fromPlayer(Player player) {
        return new OngoingPlayer(player.getId(), player.getName());
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
