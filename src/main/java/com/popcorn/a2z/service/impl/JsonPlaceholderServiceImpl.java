package com.popcorn.a2z.service.impl;

import com.popcorn.a2z.domain.response.JsonPostDTO;
import com.popcorn.a2z.feignclient.JsonPlaceholderClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class JsonPlaceholderServiceImpl {
    private final JsonPlaceholderClient jsonPlaceholderClient;

    @Retry(name = "retry-getAllPosts", fallbackMethod = "getAllPostsFallback")
    @CircuitBreaker(name = "cb-getAllPosts", fallbackMethod = "getAllPostsFallback")
    public Collection<JsonPostDTO> getAllPosts() {
        log.info("JsonPlaceholderServiceImpl::getAllPosts");
        ResponseEntity<Collection<JsonPostDTO>> response = jsonPlaceholderClient.getAllPosts();

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            log.warn("Received non-successful response: status={}, body={}", response.getStatusCode(), response.getBody());
            throw new RuntimeException("Non-success response from JsonPlaceholder API");
        }
    }

    public Collection<JsonPostDTO> getAllPostsFallback(Throwable t) {
        log.error("Fallback triggered for getAllPosts due to: {}", t.getMessage(), t);
        return Collections.emptyList();
    }

    @Retry(name = "retry-getPostById", fallbackMethod = "getPostByIdFallback")
    @CircuitBreaker(name = "cb-getPostById", fallbackMethod = "getPostByIdFallback")
    public JsonPostDTO getPostById(final Long id) {
        log.info("JsonPlaceholderServiceImpl::getPostById id={}", id);
        ResponseEntity<JsonPostDTO> response = jsonPlaceholderClient.getPostById(id);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("Non-success response for post id=" + id);
        }
    }

    public JsonPostDTO getPostByIdFallback(final Long id, Throwable t) {
        log.error("Fallback triggered for getPostById id={} due to: {}", id, t.getMessage(), t);
        return new JsonPostDTO(id, -1L, "Fallback title", "Fallback body");
    }
}
