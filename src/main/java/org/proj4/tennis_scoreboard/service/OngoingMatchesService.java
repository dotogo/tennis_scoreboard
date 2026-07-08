package org.proj4.tennis_scoreboard.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.proj4.tennis_scoreboard.model.OngoingMatch;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class OngoingMatchesService {
    private static final Cache<UUID, OngoingMatch> ongoingMatches = Caffeine.newBuilder().expireAfterAccess(15, TimeUnit.MINUTES).build();

    public Optional<OngoingMatch> getMatch(UUID id) {
        OngoingMatch ongoingMatch = ongoingMatches.getIfPresent(id);
        return Optional.ofNullable(ongoingMatch);
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
