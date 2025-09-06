package com.popcorn.a2z.exception;

/**
 * {@code UserNotFoundException} is a specialized application exception
 * that indicates a requested user could not be found in the system.
 *
 * <p><b>Purpose:</b></p>
 * <ul>
 *   <li>Thrown when an operation attempts to fetch a user by ID, username,
 *       or other criteria, but no matching user exists.</li>
 *   <li>Provides a clear, descriptive way to signal "user not found"
 *       scenarios to the service layer, controllers, or clients.</li>
 *   <li>Extends {@link AppException}, making it part of the
 *       application’s unified exception hierarchy.</li>
 * </ul>
 *
 * <p><b>Typical Use Cases:</b></p>
 * <ul>
 *   <li>When calling {@code getUserById(123)} and no user exists
 *       with ID 123.</li>
 *   <li>When attempting login with a non-existing email or username.</li>
 *   <li>When performing a search that requires the user to exist
 *       before applying business rules.</li>
 * </ul>
 *
 * <p><b>Best Practices:</b></p>
 * <ul>
 *   <li>Always provide a descriptive message with enough context
 *       (e.g., include the user ID or username that was not found).</li>
 *   <li>Catch this exception in a global exception handler
 *       (e.g., {@code @ControllerAdvice}) to return a proper HTTP status
 *       like 404 Not Found with a helpful JSON response.</li>
 * </ul>
 *
 * <p><b>Example:</b></p>
 * <pre>{@code
 * public UserDTO getUserById(Long userId) {
 *     return userRepository.findById(userId)
 *         .map(UserMapper::toDTO)
 *         .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
 * }
 * }</pre>
 */
public class UserNotFoundException extends AppException {

    /**
     * Creates a new {@code UserNotFoundException} with the given message.
     *
     * @param message a human-readable description of the error,
     *                typically including the missing user’s ID or identifier
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}