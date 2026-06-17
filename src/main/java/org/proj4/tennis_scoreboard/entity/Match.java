package org.proj4.tennis_scoreboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Checks;

@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Entity
@Table(name = "matches")
@Checks({
        @Check(constraints = "first_player_id <> second_player_id"),
        @Check(constraints = "winner_player_id = first_player_id OR winner_player_id = second_player_id")
})
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_player_id", nullable = false, updatable = false)
    private Player firstPlayer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_player_id", nullable = false, updatable = false)
    private Player secondPlayer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "winner_player_id", nullable = false, updatable = false)
    private Player winner;

    public Match(Player firstPlayer, Player secondPlayer, Player winner) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.winner = winner;
    }
}
