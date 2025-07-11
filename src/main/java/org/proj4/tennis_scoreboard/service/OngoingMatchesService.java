package org.proj4.tennis_scoreboard.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.proj4.tennis_scoreboard.entity.OngoingMatch;

import java.util.UUID;

public class OngoingMatchesService {
    private static final Cache<UUID, OngoingMatch> ongoingMatches = Caffeine.newBuilder().build();

    public OngoingMatch getMatch(UUID id) {
        OngoingMatch ongoingMatch = ongoingMatches.getIfPresent(id);

        if (ongoingMatch != null) {
            return ongoingMatch;
        }
        //TODO make custom exception
        throw new RuntimeException("ongoingMatch is null");
    }

    public UUID addMatch(OngoingMatch ongoingMatch) {
        UUID id = UUID.randomUUID();
        ongoingMatches.put(id, ongoingMatch);
        return id;
    }

    public void deleteMatch(UUID uuid) {
        ongoingMatches.invalidate(uuid);
    }
}
