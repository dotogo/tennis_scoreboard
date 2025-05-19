package org.proj4.tennis_scoreboard.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.proj4.tennis_scoreboard.entity.Match;
import org.proj4.tennis_scoreboard.entity.MatchScore;

import java.util.UUID;

public class OngoingMatchesService {
    private final Cache<UUID, MatchScore> ongoingMatches = Caffeine.newBuilder().build();

    public Match getMatch(UUID id) {
        MatchScore matchScore = ongoingMatches.getIfPresent(id);

        if (matchScore != null) {
            return matchScore.getMatch();
        }
        //TODO make custom exception
        throw new RuntimeException();
    }

    public UUID addMatch(MatchScore matchScore) {
        UUID id = UUID.randomUUID();
        ongoingMatches.put(id, matchScore);
        return id;
    }


}
