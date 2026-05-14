package org.proj4.tennis_scoreboard.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MatchServletTest {

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private PrintWriter out;
    @InjectMocks
    private MatchServlet matchServlet;


    @ParameterizedTest (name = "{0}")
    @MethodSource("getArgumentsForParametersInvalidOrEmptyTest")
    void badRequestStatusIfParametersInvalidOrEmpty(String description, Map<String, String[]> parameterMapStub) throws IOException {
        Mockito.doReturn(out).when(resp).getWriter();
        Mockito.doReturn(parameterMapStub).when(req).getParameterMap();

        matchServlet.doPost(req, resp);

        Mockito.verify(resp).setStatus(HttpServletResponse.SC_BAD_REQUEST);
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
                Arguments.of("firstPlayerEmpty", firstPlayerEmpty),
                Arguments.of("secondPlayerEmpty", secondPlayerEmpty),
                Arguments.of("samePlayers", samePlayers)
        );
    }

}
