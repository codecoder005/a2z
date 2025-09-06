package com.popcorn.a2z.restapi;
import com.popcorn.a2z.domain.response.PingAPIResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The {@code PingAPI} interface defines a simple health-check endpoint
 * that allows clients to verify whether the service is running and
 * responsive.
 *
 * <p><b>Purpose:</b></p>
 * <ul>
 *   <li>Acts as a "heartbeat" or "ping" endpoint for monitoring tools,
 *       load balancers, or client applications.</li>
 *   <li>Provides a lightweight way to check the service’s availability
 *       without requiring complex authentication or business logic.</li>
 *   <li>Helps developers and administrators quickly confirm
 *       service uptime and response status.</li>
 * </ul>
 *
 * <p><b>Response Format:</b></p>
 * The endpoint always responds with JSON, wrapping the result
 * inside a {@link PingAPIResponse} object that contains:
 * <ul>
 *   <li>{@code status} - A numeric HTTP-like status code (e.g., 200 for OK).</li>
 *   <li>{@code message} - A human-readable string (e.g., "Service is running").</li>
 *   <li>{@code timestamp} - The exact server time when the ping was processed.</li>
 * </ul>
 *
 * <p><b>Typical Use Cases:</b></p>
 * <ul>
 *   <li>A load balancer regularly calls {@code /ping} to ensure the
 *       backend instance is alive before routing traffic to it.</li>
 *   <li>A DevOps monitoring system (such as Prometheus or Nagios)
 *       periodically checks this endpoint to track service health.</li>
 *   <li>A frontend application checks connectivity to the backend
 *       before initiating login or other requests.</li>
 * </ul>
 */
public interface PingAPI {

    /**
     * Responds with a simple status message confirming the service
     * is alive and operational.
     *
     * <p><b>Endpoint:</b> {@code GET /ping}</p>
     *
     * <p><b>Response:</b></p>
     * <ul>
     *   <li>If the service is running normally, returns an HTTP 200 OK
     *       response containing a {@link PingAPIResponse} object with
     *       a success status, a message (e.g., "Service is running"),
     *       and a timestamp.</li>
     *   <li>If the service encounters issues, implementations may
     *       return alternative error codes (e.g., 500 Internal Server Error)
     *       depending on the design.</li>
     * </ul>
     *
     * <p><b>Example Response:</b></p>
     * <pre>{@code
     * {
     *   "status": 200,
     *   "message": "Service is running",
     *   "timestamp": "2025-09-06T08:30:45"
     * }
     * }</pre>
     *
     * @return a response containing the health-check result
     */
    @GetMapping(value = "/ping", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<PingAPIResponse> ping();
}