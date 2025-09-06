package com.popcorn.a2z.search;

import lombok.Getter;

/**
 * {@code SortingOrder} defines the two possible directions for sorting results
 * when retrieving or searching data from the system.
 *
 * <p><b>Supported Values:</b></p>
 * <ul>
 *   <li>{@link #ASC} - Ascending order. Typically means from smallest to largest,
 *       alphabetically A → Z, numerically 1 → 9, or oldest to newest date.</li>
 *   <li>{@link #DESC} - Descending order. Typically means from largest to smallest,
 *       alphabetically Z → A, numerically 9 → 1, or newest to oldest date.</li>
 * </ul>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * // Example: Sort users by "createdOn" in descending order
 * SearchUsersRequest request = SearchUsersRequest.builder()
 *     .sortBy("createdOn")
 *     .sortingOrder(SortingOrder.DESC)
 *     .build();
 * }</pre>
 */
@Getter
public enum SortingOrder {

    /** Sort results in ascending order (A → Z, 1 → 9, oldest → newest). */
    ASC("asc"),

    /** Sort results in descending order (Z → A, 9 → 1, newest → oldest). */
    DESC("desc");

    /**
     * The textual representation of the sorting order,
     * usually used in SQL or query builders.
     */
    private final String order;

    SortingOrder(String order) {
        this.order = order;
    }
}
