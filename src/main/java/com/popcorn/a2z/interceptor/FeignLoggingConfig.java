package com.popcorn.a2z.interceptor;

import feign.Capability;
import feign.RequestInterceptor;
import feign.ResponseInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registers the logging interceptors for ALL Feign clients in the application.
 *
 * Properties (all optional):
 *   feign.logging.enabled                  = true     set to false to switch everything off
 *   feign.logging.max-body-chars           = 50000    max characters printed per body (0 = unlimited)
 *   feign.logging.mask-sensitive-headers   = false    mask Authorization, Cookie, Set-Cookie, X-Api-Key...
 *   feign.logging.max-buffered-response-bytes = 10485760   responses above this size are not logged
 *
 * Also set the log level of this package if you filter by logger:
 *   logging.level.com.example.feign.logging=INFO
 */
@Configuration
@ConditionalOnProperty(prefix = "feign.logging", name = "enabled", havingValue = "true", matchIfMissing = true)
public class FeignLoggingConfig {

    @Bean
    public FeignLogFormatter feignLogFormatter(
            @Value("${feign.logging.max-body-chars:50000}") int maxBodyChars,
            @Value("${feign.logging.mask-sensitive-headers:false}") boolean maskSensitiveHeaders) {
        return new FeignLogFormatter(maxBodyChars, maskSensitiveHeaders);
    }

    /** Spring Cloud OpenFeign applies every RequestInterceptor bean to every client. */
    @Bean
    public RequestInterceptor feignRequestLoggingInterceptor(FeignLogFormatter formatter) {
        return new FeignRequestLoggingInterceptor(formatter);
    }

    /**
     * Spring Cloud OpenFeign looks up ONE ResponseInterceptor bean and attaches it to every client.
     * Do not also add it through a FeignBuilderCustomizer or it will run (and log) twice.
     */
    @Bean
    public ResponseInterceptor feignResponseLoggingInterceptor(FeignLogFormatter formatter) {
        return new FeignResponseLoggingInterceptor(formatter);
    }

    /** Spring Cloud OpenFeign applies every Capability bean to every client. */
    @Bean
    public Capability feignResponseBufferingCapability(
            @Value("${feign.logging.max-buffered-response-bytes:10485760}") long maxBufferedBytes) {
        return new FeignResponseBufferingCapability(maxBufferedBytes);
    }
}