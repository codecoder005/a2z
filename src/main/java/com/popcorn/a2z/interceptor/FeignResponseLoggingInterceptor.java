package com.popcorn.a2z.interceptor;

import feign.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * Logs everything about a Feign response: status, protocol, headers, Set-Cookie details,
 * raw body, the decoded object (or the exception thrown for error statuses), elapsed time,
 * and the request as it was handed to the HTTP client (final URL and headers).
 *
 * The raw body can only be printed when the body is repeatable. Register
 * {@link FeignResponseBufferingCapability} so that is always the case.
 */
@Slf4j
public class FeignResponseLoggingInterceptor implements ResponseInterceptor {


    private final FeignLogFormatter fmt;

    public FeignResponseLoggingInterceptor(FeignLogFormatter fmt) {
        this.fmt = fmt;
    }

    @Override
    public Object intercept(InvocationContext context, ResponseInterceptor.Chain chain) throws Exception {
        long elapsedMs = fmt.elapsedMillisAndClear();
        Response response = context.response();
        boolean logging = log.isInfoEnabled();

        // Read the body BEFORE decoding: the decoder may close the stream afterwards.
        BodyCapture body = logging ? capture(response) : null;

        Object result = null;
        Exception failure = null;
        try {
            result = chain.next(context); // decodes, or throws for error statuses (FeignException)
        } catch (Exception ex) {
            failure = ex;
        }

        if (logging) {
            try {
                log.info("{}", describe(response, body, elapsedMs, result, failure));
            } catch (RuntimeException ex) {
                log.warn("Could not log Feign response", ex); // logging must never break the call
            }
        }

        if (failure != null) {
            throw failure; // behave exactly as if this interceptor was not there
        }
        return result;
    }

    private String describe(Response response, BodyCapture body, long elapsedMs, Object result, Exception failure) {
        Request request = response.request();
        RequestTemplate template = request != null ? request.requestTemplate() : null;
        MethodMetadata md = template != null ? template.methodMetadata() : null;

        StringBuilder sb = new StringBuilder("\n<<<<<<<<<< FEIGN RESPONSE <<<<<<<<<<\n");
        FeignLogFormatter.field(sb, "Thread", Thread.currentThread().getName());
        if (md != null) {
            FeignLogFormatter.field(sb, "Feign config key", md.configKey());
            Method method = md.method();
            if (method != null) {
                FeignLogFormatter.field(sb, "Java method", method.toGenericString());
            }
            if (md.returnType() != null) {
                FeignLogFormatter.field(sb, "Declared return type", md.returnType().getTypeName());
            }
        }
        if (request != null) {
            FeignLogFormatter.field(sb, "Request", request.httpMethod() + " " + request.url());
        }
        String reason = response.reason();
        FeignLogFormatter.field(sb, "Status", response.status() + (reason != null ? " " + reason : ""));
        if (response.protocolVersion() != null) {
            FeignLogFormatter.field(sb, "Protocol", response.protocolVersion());
        }
        FeignLogFormatter.field(sb, "Elapsed", elapsedMs >= 0
                ? elapsedMs + " ms (request interceptor -> response received)"
                : "n/a (no request timestamp on this thread, e.g. an async client)");

        Map<String, Collection<String>> headers = response.headers();
        FeignLogFormatter.field(sb, "Headers", fmt.headers(headers));
        FeignLogFormatter.field(sb, "Cookies (Set-Cookie)", fmt.responseCookies(headers));

        String bodyText;
        if (body == null || body.note != null) {
            bodyText = body == null ? "(not captured)" : body.note;
        } else {
            bodyText = fmt.bodyBlock(
                    body.bytes,
                    response.charset(),
                    FeignLogFormatter.firstHeader(headers, "Content-Type"),
                    FeignLogFormatter.firstHeader(headers, "Content-Encoding"));
        }
        FeignLogFormatter.field(sb, "Body", bodyText);

        if (failure != null) {
            FeignLogFormatter.field(sb, "Result", "EXCEPTION " + failure.getClass().getName() + ": " + failure.getMessage());
        } else if (result == null) {
            FeignLogFormatter.field(sb, "Decoded result", "null");
        } else if (result instanceof Response) {
            FeignLogFormatter.field(sb, "Decoded result", "feign.Response (passed through to the caller)");
        } else {
            FeignLogFormatter.field(sb, "Decoded type", result.getClass().getName());
            FeignLogFormatter.field(sb, "Decoded value", fmt.truncate(String.valueOf(result)));
        }

        if (request != null) {
            Map<String, Collection<String>> sent = request.headers();
            FeignLogFormatter.field(sb, "Request headers sent", fmt.headers(sent));
        }

        sb.append("<<<<<<<<<< END FEIGN RESPONSE <<<<<<<<<<");
        return sb.toString();
    }

    private BodyCapture capture(Response response) {
        Response.Body body = response.body();
        if (body == null) {
            return new BodyCapture(null, "(no body)");
        }
        if (!body.isRepeatable()) {
            return new BodyCapture(null, "<not logged: the response stream can only be read once (it is larger than "
                    + "feign.logging.max-buffered-response-bytes, it is a stream, or "
                    + "FeignResponseBufferingCapability is not registered)>");
        }
        try (InputStream in = body.asInputStream()) {
            return new BodyCapture(Util.toByteArray(in), null);
        } catch (IOException ex) {
            return new BodyCapture(null, "<could not read body: " + ex + ">");
        }
    }

    private static final class BodyCapture {
        final byte[] bytes;
        final String note;

        BodyCapture(byte[] bytes, String note) {
            this.bytes = bytes;
            this.note = note;
        }
    }
}