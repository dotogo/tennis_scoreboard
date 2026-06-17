package org.proj4.tennis_scoreboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "players", indexes = {@Index(name = "idx_player_name", columnList = "player_name")})
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "player_name", nullable = false, length = 50, unique = true)
    private String name;

    public Player(String name) {
        this.name = name;
    }
}
