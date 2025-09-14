package com.popcorn.a2z.feignclient;

import com.popcorn.a2z.domain.response.JsonPostDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

/**
 * Feign client for interacting with the JsonPlaceholder REST API.
 * <p>
 * This client communicates with the remote API at:
 * <a href="https://jsonplaceholder.typicode.com/posts">https://jsonplaceholder.typicode.com/posts</a>.
 * It provides methods to fetch all posts or retrieve a single post by its identifier.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * Collection<JsonPostDTO> posts = jsonPlaceholderClient.getAllPosts().getBody();
 * JsonPostDTO post = jsonPlaceholderClient.getPostById(1L).getBody();
 * }</pre>
 * </p>
 *
 * @author You
 */
@FeignClient(
        name = "json-placeholder-client",
        url = "https://jsonplaceholder.typicode.com",
        path = "/posts"
)
public interface JsonPlaceholderClient {
    /**
     * Retrieves all posts from the JsonPlaceholder API.
     *
     * @return {@link ResponseEntity} containing a collection of {@link JsonPostDTO}.
     *         If successful, the response body contains all posts.
     *         If the API is unreachable or an error occurs, a FeignException is thrown.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Collection<JsonPostDTO>> getAllPosts();

    /**
     * Retrieves a single post by its identifier.
     *
     * @param id the unique identifier of the post to fetch (must not be {@code null}).
     * @return {@link ResponseEntity} containing a {@link JsonPostDTO}.
     *         If the post is found, the response body contains the post details.
     *         If not found, a 404 error is returned and FeignException.NotFound is thrown.
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<JsonPostDTO> getPostById(@PathVariable(name = "id") final Long id);
}
