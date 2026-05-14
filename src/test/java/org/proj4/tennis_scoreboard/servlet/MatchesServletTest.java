package org.proj4.tennis_scoreboard.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension .class)
class MatchesServletTest {

    private MatchesServlet matchesServlet;

    @BeforeEach
    void prepare() {
        matchesServlet = new MatchesServlet();
    }

    @ParameterizedTest
    @ValueSource(strings = {"-5", "0", "2.5", "5,1", "text"})
    @NullAndEmptySource
    void return1IfPageParameterIsInvalid(String pageParam) {
        int pageNumber = matchesServlet.getPageNumber(pageParam);

        assertThat(pageNumber).isEqualTo(1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"5", "1", "10", "20"})
    void returnValidPageNumber(String pageParam) {
        int pageNumber = matchesServlet.getPageNumber(pageParam);

        assertThat(pageNumber).isEqualTo(Integer.parseInt(pageParam));
    }



}
