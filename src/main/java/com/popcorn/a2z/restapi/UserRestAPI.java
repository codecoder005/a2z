package com.popcorn.a2z.restapi;
import com.popcorn.a2z.domain.request.SearchUsersRequest;
import com.popcorn.a2z.domain.response.UserDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

/**
 * UserRestAPI is a RESTful interface that exposes user-related operations
 * in the system. This interface defines a set of endpoints for retrieving
 * user details, listing all users, and performing advanced searches
 * based on different criteria.
 *
 * <p><b>Overview:</b></p>
 * <ul>
 *   <li>Provides read-only access to user data.</li>
 *   <li>Allows fetching a single user by their unique identifier.</li>
 *   <li>Allows retrieving all registered users in the system.</li>
 *   <li>Supports flexible searching with multiple filters, sorting,
 *       and ordering capabilities.</li>
 *   <li>Ensures responses are returned in standardized JSON format
 *       for easy integration with frontend applications, mobile apps,
 *       or third-party services.</li>
 * </ul>
 *
 * <p><b>Data Flow:</b></p>
 * <ol>
 *   <li>Client (such as a web or mobile app) sends an HTTP request
 *       to one of the endpoints.</li>
 *   <li>The server receives the request and invokes the corresponding method.</li>
 *   <li>A {@link ResponseEntity} is returned containing user details
 *       in {@link UserDTO} format.</li>
 * </ol>
 *
 * <p><b>Content Type:</b> All endpoints produce
 * {@code application/json} responses.</p>
 *
 * <p><b>Typical Use Cases:</b></p>
 * <ul>
 *   <li>Displaying a user profile by ID.</li>
 *   <li>Fetching all users to display in an admin dashboard.</li>
 *   <li>Searching users by multiple attributes such as name, email,
 *       or registration date.</li>
 * </ul>
 */
public interface UserRestAPI {

    /**
     * Retrieves a single user's details using their unique user ID.
     *
     * <p><b>Endpoint:</b> {@code GET /{userId}}</p>
     * <p><b>Path Parameter:</b></p>
     * <ul>
     *   <li>{@code userId} - A unique numeric identifier of the user.</li>
     * </ul>
     *
     * <p><b>Response:</b></p>
     * <ul>
     *   <li>If the user is found, returns {@link ResponseEntity} containing
     *       a {@link UserDTO} object with full details such as name, email,
     *       gender, and audit information (created/updated metadata).</li>
     *   <li>If no user is found, implementations may return
     *       an error response (e.g., 404 Not Found).</li>
     * </ul>
     *
     * <p><b>Example Use Case:</b></p>
     * <ul>
     *   <li>A mobile app fetches a logged-in user's profile by sending
     *       their ID to this endpoint.</li>
     * </ul>
     *
     * @param userId the unique identifier of the user
     * @return a response containing the user details in JSON format
     */
    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDTO> getUserByUserId(@PathVariable(name = "userId") Long userId);

    /**
     * Retrieves all users available in the system.
     *
     * <p><b>Endpoint:</b> {@code GET /}</p>
     *
     * <p><b>Response:</b></p>
     * <ul>
     *   <li>Returns {@link ResponseEntity} containing a JSON array of
     *       {@link UserDTO} objects.</li>
     *   <li>If there are no users in the system, an empty list is returned.</li>
     * </ul>
     *
     * <p><b>Example Use Case:</b></p>
     * <ul>
     *   <li>An admin dashboard fetches all registered users to display them
     *       in a management table.</li>
     * </ul>
     *
     * @return a response containing a collection of all users in JSON format
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Collection<UserDTO>> getAllUsers();

    /**
     * Searches users based on a flexible set of criteria.
     *
     * <p><b>Endpoint:</b> {@code POST /search}</p>
     *
     * <p><b>Request Body:</b></p>
     * The request must contain a {@link SearchUsersRequest} object that defines:
     * <ul>
     *   <li>{@code searchOptions} - A list of filters (e.g., field = "email",
     *       value = "john.doe@example.com", strategy = "EQUALS").</li>
     *   <li>{@code sortBy} - The field used to sort the results
     *       (default: {@code userId}).</li>
     *   <li>{@code sortingOrder} - The order of sorting
     *       (ASC = ascending, DESC = descending).</li>
     * </ul>
     *
     * <p><b>Response:</b></p>
     * <ul>
     *   <li>Returns {@link ResponseEntity} containing a JSON array of
     *       {@link UserDTO} objects that match the given criteria.</li>
     *   <li>If no matches are found, an empty list is returned.</li>
     * </ul>
     *
     * <p><b>Example Use Case:</b></p>
     * <ul>
     *   <li>A search bar in an admin portal allows filtering users by
     *       email, first name, or registration date. The request body
     *       carries these filters and this endpoint returns matching users.</li>
     * </ul>
     *
     * @param searchUsersRequest the search criteria and sorting instructions
     * @return a response containing a list of users matching the search filters
     */
    @PostMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Collection<UserDTO>> searchUsers(@RequestBody SearchUsersRequest searchUsersRequest);
}
