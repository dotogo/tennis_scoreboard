package org.proj4.tennis_scoreboard.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaginatedResult<T> {
    private List<T> items;
    private int currentPage;
    private int totalPages;
    private int totalItems;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
}
