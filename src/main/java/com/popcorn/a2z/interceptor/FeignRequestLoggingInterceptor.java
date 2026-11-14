package com.popcorn.a2z.interceptor;

import feign.MethodMetadata;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Target;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs everything that is known about an outgoing Feign request: target, Java method,
 * full URL, path template and path variables, query params, headers, cookies and body.
 *
 * Note: Feign runs request interceptors after the arguments have been resolved into the
 * template, so the raw Java argument values are not available here. Path variables are
 * reconstructed by matching the path template against the resolved path.
 */
public class FeignRequestLoggingInterceptor implements RequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(FeignRequestLoggingInterceptor.class);

    private final FeignLogFormatter fmt;

    public FeignRequestLoggingInterceptor(FeignLogFormatter fmt) {
        this.fmt = fmt;
    }

    @Override
    public void apply(RequestTemplate template) {
        fmt.markCallStart(); // lets the response interceptor report elapsed time
        if (!log.isInfoEnabled()) {
            return;
        }
        try {
            log.info("{}", describe(template));
        } catch (RuntimeException ex) {
            log.warn("Could not log Feign request", ex); // logging must never break the call
        }
    }

    private String describe(RequestTemplate template) {
        StringBuilder sb = new StringBuilder("\n>>>>>>>>>> FEIGN REQUEST >>>>>>>>>>\n");
        MethodMetadata md = template.methodMetadata();
        Target<?> target = template.feignTarget();

        FeignLogFormatter.field(sb, "Thread", Thread.currentThread().getName());
        if (md != null) {
            FeignLogFormatter.field(sb, "Feign config key", md.configKey());
            Method method = md.method();
            if (method != null) {
                FeignLogFormatter.field(sb, "Java method", method.toGenericString());
            }
        }
        if (target != null) {
            FeignLogFormatter.field(sb, "Client interface", target.type().getName());
            FeignLogFormatter.field(sb, "Target name", target.name());
            FeignLogFormatter.field(sb, "Target base URL", target.url());
        }

        // URL. The target's base URL is applied after interceptors run, so prepend it here.
        String relative = template.url();
        boolean absolute = relative.startsWith("http://") || relative.startsWith("https://");
        String fullUrl = (absolute || target == null) ? relative : target.url() + relative;
        FeignLogFormatter.field(sb, "HTTP method", template.method());
        FeignLogFormatter.field(sb, "Full URL", fullUrl);

        String templatePath = (md != null && md.template() != null) ? md.template().path() : null;
        if (templatePath != null) {
            FeignLogFormatter.field(sb, "Path template", templatePath);
        }
        FeignLogFormatter.field(sb, "Resolved path", template.path());
        FeignLogFormatter.field(sb, "Path variables", fmt.pathVariables(templatePath, template.path()));

        String queryLine = template.queryLine();
        FeignLogFormatter.field(sb, "Query line", (queryLine == null || queryLine.isEmpty()) ? "(none)" : queryLine);
        Map<String, Collection<String>> queries = template.queries();
        FeignLogFormatter.field(sb, "Query params", fmt.queries(queries));

        Map<String, Collection<String>> headers = template.headers();
        FeignLogFormatter.field(sb, "Headers", fmt.headers(headers));
        FeignLogFormatter.field(sb, "Cookies", fmt.requestCookies(headers));

        if (md != null && md.indexToName() != null && !md.indexToName().isEmpty()) {
            FeignLogFormatter.field(sb, "Parameter bindings", fmt.parameterBindings(md.indexToName()));
        }

        if (template.bodyTemplate() != null) {
            FeignLogFormatter.field(sb, "Body template", template.bodyTemplate());
        }
        FeignLogFormatter.field(sb, "Body", fmt.bodyBlock(
                template.body(),
                template.requestCharset(),
                FeignLogFormatter.firstHeader(headers, "Content-Type"),
                FeignLogFormatter.firstHeader(headers, "Content-Encoding")));

        sb.append(">>>>>>>>>> END FEIGN REQUEST >>>>>>>>>>");
        return sb.toString();
    }
}