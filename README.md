
---

**Tennis Scoreboard**

Web application implementing a tennis match scoreboard. Allows creating new matches, tracking the score in real time, and viewing the history of completed matches with filtering by player name.

---

**Functionality**

- Create a new match between two players
- Real-time score tracking with full tennis scoring rules (points, games, sets, tiebreak)
- View completed matches with pagination and filtering by player name
- Automatic player creation if they don't exist in the database

---

**Tennis scoring rules implemented**

- Match is played best of 3 sets
- Standard point progression: 0 → 15 → 30 → 40
- Deuce (40:40) and Advantage rules
- Tiebreak at 6:6 in games, played until one player leads by 2 points with minimum 7 points

---

**Some details for users**

- Player name validation: alphanumeric (English/Russian), spaces and characters `_ . \` -` are allowed, minimum 2 characters, maximum 50 characters
- Profanity filter: player names are checked against built-in dictionaries of profanity words in English and Russian
- Homoglyph detection: names that look identical but use mixed Cyrillic and Latin characters (e.g. "Саша" and "Cаша") are treated as the same name and rejected
- Ongoing matches expire automatically after 15 minutes of inactivity

---

**Some technical details**

- **Rich Domain Model** is used for ongoing match logic. `MatchScore`, `SetScore`, `RegularGameScore`, `TieBreakScore` form a nested hierarchy where each level manages its own state and delegates to the level below. JPA entities (`Match`, `Player`) remain intentionally anemic — this is a conscious tradeoff given Hibernate's constraints (required no-arg constructor, non-final fields, proxy-based lazy loading)
- **Separation of domain and persistence models**: ongoing match uses `OngoingPlayer` (a record) instead of JPA `Player` entity inside domain objects. Conversion happens at the boundary in `PlayerService`
- **Session-per-operation** pattern is used in DAO layer — each DAO method opens and closes its own Hibernate session. This is a conscious decision: the project has no use cases requiring a single transaction spanning multiple DAO operations
- **Race condition** on `OngoingMatch` is not handled — concurrent requests for the same match could theoretically corrupt the score. This is an accepted limitation since the project is single-user per ТЗ
- **Offset calculation** happens in the service layer, not in DAO. DAO accepts ready `offset` and `pageSize` parameters
- **DB initialization** happens explicitly on application startup in `AppContextListener`, not lazily on first request
- **`MatchScoreDto`** uses facade methods to flatten the nested DTO hierarchy (`GameScoreDto` → `SetScoreDto` → `MatchScoreDto`) for clean JSP access without deep property chaining
- Validation constraints are consistent between `Validator` class (max 50 characters) and `@Column(length = 50)` in JPA entity
- Error handling is implemented in `ExceptionHandlingFilter`: `InvalidParameterException` shows a user-friendly message, all other exceptions show a generic message without exposing internal details
- `SessionFactory` is properly closed on application shutdown via `contextDestroyed` in `AppContextListener`
- JSP pages are protected inside `/WEB-INF/views/` and are not directly accessible

---

**Technologies / tools used**

- Java 17
- Jakarta Servlet 6.1.0
- JSP / JSTL 3.0
- Apache Tomcat 10
- Apache Maven
- Hibernate ORM 6.4.4
- H2 in-memory database
- HikariCP connection pool
- Caffeine cache (for ongoing matches storage with TTL expiration)
- MapStruct (entity to DTO mapping)
- Lombok
- Logback / SLF4J
- JUnit 5
- Mockito
- AssertJ