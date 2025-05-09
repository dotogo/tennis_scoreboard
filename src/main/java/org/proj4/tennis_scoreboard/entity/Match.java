package org.proj4.tennis_scoreboard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_player_id", nullable = false)
    private Player firstPlayer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_player_id", nullable = false)
    private Player secondPlayer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "winner_player_id", nullable = false)
    private Player winner;
}
