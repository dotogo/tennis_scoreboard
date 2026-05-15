package org.proj4.tennis_scoreboard.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.IntStream;

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

    @Test
    void pageRangeSizeEqualsTotalPagesIfDeviationMoreThanTotalPages() {
        int currentPage = 5;
        int totalPages = 10;
        int deviatiion = 100;

        List<Integer> pageRange = matchesServlet.getPageRange(currentPage, totalPages, deviatiion);

        assertThat(pageRange).hasSize(totalPages);
    }

    @Test
    void pageRangeEqualsFromFirstToLastPageIfDeviationMoreThanLastPage() {
        int currentPage = 3;
        int totalPages = 7;
        int deviatiion = 20;

        List<Integer> expected = List.of(1, 2, 3, 4, 5, 6, 7);

        List<Integer> pageRange = matchesServlet.getPageRange(currentPage, totalPages, deviatiion);

        assertThat(pageRange).isEqualTo(expected);
    }

    @Test
    void pageRange_FromOneToDeviationPlusOne_IfCurrentPageLessThanDeviation() {
        int currentPage = 1;
        int totalPages = 10;
        int deviation = 2;

        List<Integer> expected = IntStream.rangeClosed(currentPage, currentPage + deviation)
                .boxed()
                .toList();

        List<Integer> pageRange = matchesServlet.getPageRange(currentPage, totalPages, deviation);

        assertThat(pageRange).isEqualTo(expected);
    }

    @Test
    void pageRage_FromLastPageMinusDeviationToDeviation_IfCurrentPageIsLastPage() {
        int currentPage = 12;
        int totalPages = 12;
        int deviation = 3;

        List<Integer> expected = IntStream.rangeClosed(totalPages - deviation, totalPages)
                .boxed()
                .toList();

        List<Integer> pageRange = matchesServlet.getPageRange(currentPage, totalPages, deviation);

        assertThat(pageRange).isEqualTo(expected);
    }

    @Test
    void currentPageIsInMiddleOfRangeAndDeviationIsNormal() {
        int currentPage = 7;
        int totalPages = 15;
        int deviation = 2;

        List<Integer> expected = List.of(5, 6, 7, 8, 9);

        List<Integer> pageRange = matchesServlet.getPageRange(currentPage, totalPages, deviation);

        assertThat(pageRange).isEqualTo(expected);
    }



}
