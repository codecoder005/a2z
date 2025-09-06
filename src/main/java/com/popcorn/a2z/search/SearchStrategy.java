package com.popcorn.a2z.search;

import lombok.Getter;

/**
 * {@code SearchStrategy} defines the set of supported operators
 * that can be applied when filtering user search queries.
 *
 * <p>This enumeration is typically used inside {@link com.popcorn.a2z.domain.request.SearchUsersRequest.SearchOption}
 * to specify how a given field should be compared against the provided value.</p>
 *
 * <p><b>Supported Strategies:</b></p>
 * <ul>
 *   <li>{@link #EQUALS} - Exact match, e.g. {@code email = 'john.doe@example.com'}</li>
 *   <li>{@link #NOT_EQUALS} - Opposite of equals, e.g. {@code status != 'ACTIVE'}</li>
 *   <li>{@link #LIKE} - Partial match, useful for pattern-based searches,
 *       e.g. {@code username LIKE '%john%'}</li>
 *   <li>{@link #LESS_THAN} - Checks if a value is smaller,
 *       e.g. {@code age < 30}</li>
 *   <li>{@link #LESS_THAN_OR_EQUALS} - Allows smaller or equal values,
 *       e.g. {@code age <= 30}</li>
 *   <li>{@link #GREATER_THAN} - Checks if a value is larger,
 *       e.g. {@code age > 30}</li>
 *   <li>{@link #GREATER_THAN_OR_EQUALS} - Allows larger or equal values,
 *       e.g. {@code age >= 30}</li>
 * </ul>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * SearchOption option = SearchOption.builder()
 *     .field("age")
 *     .data(25)
 *     .searchStrategy(SearchStrategy.GREATER_THAN)
 *     .build();
 * }</pre>
 */
@Getter
public enum SearchStrategy {

    /** Exact match (e.g., {@code field = value}). */
    EQUALS("="),

    /** Not equal match (e.g., {@code field != value}). */
    NOT_EQUALS("!="),

    /** Pattern-based partial match (e.g., {@code field LIKE '%value%'}). */
    LIKE("%_%"),

    /** Strictly smaller (e.g., {@code field < value}). */
    LESS_THAN("<"),

    /** Smaller or equal (e.g., {@code field <= value}). */
    LESS_THAN_OR_EQUALS("<="),

    /** Strictly greater (e.g., {@code field > value}). */
    GREATER_THAN(">"),

    /** Greater or equal (e.g., {@code field >= value}). */
    GREATER_THAN_OR_EQUALS(">=");

    /**
     * The operator signature as it would typically appear in an expression.
     */
    private final String signature;

    SearchStrategy(String signature) {
        this.signature = signature;
    }
}