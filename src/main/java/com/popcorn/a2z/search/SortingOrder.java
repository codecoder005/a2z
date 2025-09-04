package com.popcorn.a2z.search;

import lombok.Getter;

@Getter
public enum SortingOrder {
    ASC("asc"),
    DESC("desc");
    private final String order;

    SortingOrder(String order) {
        this.order = order;
    }
}
