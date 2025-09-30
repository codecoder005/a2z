package com.popcorn.a2z.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import feign.RequestInterceptor;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import java.util.Optional;

@Configuration
public class AppConfigBeans {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    public Gson jsonHelper() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.setPrettyPrinting().create();
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("SYSTEM");
    }

    @Bean
    @Profile("!prod")
    public CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeClientInfo(true);
        filter.setIncludeQueryString(true);
        filter.setIncludeHeaders(true);       // ⚠️ Mask sensitive headers if needed
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(100_000);
        filter.setBeforeMessagePrefix("➡️ Request [");
        filter.setBeforeMessageSuffix("]");
        filter.setAfterMessagePrefix("✅ Completed [");
        filter.setAfterMessageSuffix("]");
        return filter;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    /**
     * Feign RequestInterceptor that propagates the current tracing context
     * using the W3C Trace Context format (traceparent header).
     *
     * <p>Why this is needed:</p>
     * <ul>
     *   <li>Micrometer generates spans for incoming requests, but Feign by default
     *       does not automatically propagate them to downstream services.</li>
     *   <li>The W3C Trace Context standard defines a {@code traceparent} header
     *       that carries trace ID, span ID, and sampling state in the format:</li>
     *   <pre>
     *       traceparent: {version}-{trace-id}-{span-id}-{trace-flags}
     *   </pre>
     *   <li>By injecting this header into every Feign request, the downstream service
     *       can join the same trace instead of starting a new one.</li>
     * </ul>
     *
     * <p>Example header added by this interceptor:</p>
     * <pre>
     *     traceparent: 00-4bf92f3577b34da6a3ce929d0e0e4736-00f067aa0ba902b7-01
     * </pre>
     *
     * <p>Notes:</p>
     * <ul>
     *   <li>Only W3C headers are propagated (no Zipkin B3 headers).</li>
     *   <li>Downstream services must also be configured with Micrometer/OTel
     *       to extract {@code traceparent} for correlation.</li>
     * </ul>
     */
    @Bean
    public RequestInterceptor tracingFeignInterceptor(Tracer tracer, Propagator propagator) {
        return requestTemplate -> {
            var span = tracer.currentSpan();
            if (span != null) {
                propagator.inject(span.context(), requestTemplate, (req, key, value) -> {
                    assert req != null;
                    req.header(key, value);
                });
            }
        };
    }
}
