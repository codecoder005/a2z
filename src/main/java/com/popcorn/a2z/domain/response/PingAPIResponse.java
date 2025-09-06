package com.popcorn.a2z.domain.response;

import lombok.*;

import java.time.LocalDateTime;


/**
 * {@code PingAPIResponse} represents the standard response format
 * for the {@code /ping} health-check endpoint.
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li>{@code status} - A numeric code that represents the result
 *       of the ping check (typically 200 for success).</li>
 *   <li>{@code message} - A human-readable message describing the
 *       service state (e.g., "Service is running").</li>
 *   <li>{@code timestamp} - The exact time (according to the server’s
 *       clock) when the response was generated.</li>
 * </ul>
 *
 * <p><b>Purpose:</b></p>
 * <ul>
 *   <li>Provides a clear, structured format for monitoring systems.</li>
 *   <li>Makes it easy for humans to read and machines to parse
 *       health-check results.</li>
 *   <li>Helps correlate events and uptime checks with precise timestamps.</li>
 * </ul>
 *
 * <p><b>Example:</b></p>
 * <pre>{@code
 * PingAPIResponse response = new PingAPIResponse();
 * response.setStatus(200);
 * response.setMessage("Service is running");
 * response.setTimestamp(LocalDateTime.now());
 * }</pre>
 */
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
