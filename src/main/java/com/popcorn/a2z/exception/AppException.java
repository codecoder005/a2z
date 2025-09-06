package com.popcorn.a2z.exception;

import lombok.Getter;

/**
 * {@code AppException} is a custom runtime exception that represents
 * application-specific errors within the system.
 *
 * <p><b>Purpose:</b></p>
 * <ul>
 *   <li>Provides a unified way of throwing and handling exceptions
 *       that are specific to the application domain.</li>
 *   <li>Extends {@link RuntimeException}, meaning it is unchecked
 *       and does not require explicit declaration in method signatures.</li>
 *   <li>Can be used to simplify error propagation while ensuring
 *       meaningful error messages are provided.</li>
 * </ul>
 *
 * <p><b>Typical Use Cases:</b></p>
 * <ul>
 *   <li>Throwing an exception when invalid data is passed into a service method.</li>
 *   <li>Indicating a business rule violation (e.g., "User already exists").</li>
 *   <li>Wrapping lower-level exceptions to provide application context
 *       (e.g., converting a {@code SQLException} into an {@code AppException}).</li>
 * </ul>
 *
 * <p><b>Best Practices:</b></p>
 * <ul>
 *   <li>Always provide a clear, descriptive message when creating the exception.</li>
 *   <li>Consider using different subclasses of {@code AppException}
 *       if your application has multiple error domains
 *       (e.g., {@code ValidationException}, {@code AuthenticationException}).</li>
 *   <li>Handle this exception centrally using
 *       {@code @ControllerAdvice} in Spring Boot to return
 *       consistent error responses to clients.</li>
 * </ul>
 *
 * <p><b>Example:</b></p>
 * <pre>{@code
 * if (userRepository.existsByEmail(email)) {
 *     throw new AppException("User with email " + email + " already exists");
 * }
 * }</pre>
 */
@Getter
public class AppException extends RuntimeException {

    /**
     * Creates a new {@code AppException} with the given message.
     *
     * @param message a human-readable description of the error,
     *                explaining why the exception was thrown
     */
    public AppException(String message) {
        super(message);
    }
}