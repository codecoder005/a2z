package com.popcorn.a2z.domain.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PingAPIResponse {
    private Integer status;
    private String message;
    private LocalDateTime timestamp;
}
