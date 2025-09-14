package com.popcorn.a2z.domain.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JsonPostDTO {
    private Long userId;
    private Long id;
    private String title;
    private String body;
}
