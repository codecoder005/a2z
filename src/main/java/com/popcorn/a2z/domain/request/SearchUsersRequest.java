package com.popcorn.a2z.domain.request;

import com.popcorn.a2z.search.SearchStrategy;
import com.popcorn.a2z.search.SortingOrder;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchUsersRequest {
    private List<SearchOption> searchOptions;
    private String sortBy = "userId";
    private SortingOrder sortingOrder = SortingOrder.ASC;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SearchOption {
        private String field;
        private Object data;
        private SearchStrategy searchStrategy;
    }
}
