package org.proj4.tennis_scoreboard.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.proj4.tennis_scoreboard.entity.Player;
import org.proj4.tennis_scoreboard.service.OngoingMatchesService;
import org.proj4.tennis_scoreboard.service.PlayerService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MatchServletTest {

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    RequestDispatcher dispatcher;
    @Mock
    private PlayerService playerService;
    @Mock
    private OngoingMatchesService ongoingMatchesService;
    @InjectMocks
    private MatchServlet matchServlet;

    @ParameterizedTest (name = "{0}")
    @MethodSource("getArgumentsForParametersInvalidOrEmptyTest")
    @DisplayName("Forward to new-match.jsp if parameters are invalid or empty")
    void forwardToNewMatchJspIfParametersInvalidOrEmpty(String description, Map<String, String[]> parameterMapStub) throws IOException, ServletException {
        doReturn(dispatcher).when(req).getRequestDispatcher(anyString());
        doReturn(parameterMapStub).when(req).getParameterMap();

        matchServlet.doPost(req, resp);

        verify(req).getRequestDispatcher("/WEB-INF/views/new-match.jsp");
    }

    @Test
    @DisplayName("Redirect to ongoing match page")
    void redirectToOngoingMatchPage() throws IOException, ServletException {
        Map<String, String[]> parameterMapStub = Map.of("first-player", new String[]{"firstName"}, "second-player", new String[]{"secondName"});

        doReturn(parameterMapStub).when(req).getParameterMap();

        UUID uuid = UUID.randomUUID();

        Player player = new Player();

        doReturn(player).when(playerService).findOrCreate(anyString());
        doReturn(uuid).when(ongoingMatchesService).addMatch(any());

        matchServlet.doPost(req, resp);

        verify(resp).sendRedirect("/match-score?uuid=" + URLEncoder.encode(uuid.toString(), StandardCharsets.UTF_8));
    }


    static Stream<Arguments> getArgumentsForParametersInvalidOrEmptyTest() {
        Map<String, String[]> wrongParameters = Map.of("testKey", new String[]{"testValue"});
        Map<String, String[]> missingParameters = new HashMap<>();
        Map<String, String[]> firstPlayerEmpty = Map.of("first-player", new String[]{""}, "second-player", new String[]{"secondName"});
        Map<String, String[]> secondPlayerEmpty = Map.of("first-player", new String[]{"firstName"}, "second-player", new String[]{""});
        Map<String, String[]> samePlayers = Map.of("first-player", new String[]{"name"}, "second-player", new String[]{"name"});

        return Stream.of(
                Arguments.of("wrongParameters", wrongParameters),
                Arguments.of("missingParameters", missingParameters),
                Arguments.of("firstPlayerNameEmpty", firstPlayerEmpty),
                Arguments.of("secondPlayerNameEmpty", secondPlayerEmpty),
                Arguments.of("samePlayerNames", samePlayers)
        );
    }

}
