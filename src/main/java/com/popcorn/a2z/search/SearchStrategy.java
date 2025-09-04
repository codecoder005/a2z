package com.popcorn.a2z.search;

import lombok.Getter;

@Getter
public enum SearchStrategy {
    EQUALS("="),

    NOT_EQUALS("!="),

    LIKE("%_%"),

    LESS_THAN("<"),
    LESS_THAN_OR_EQUALS("<="),
    GREATER_THAN("?"),
    GREATER_THAN_OR_EQUALS(">=");

    private final String signature;

    SearchStrategy(String signature) {
        this.signature = signature;
    }
}
