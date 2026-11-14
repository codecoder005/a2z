package com.popcorn.a2z.interceptor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * Shared formatting helpers used by {@link FeignRequestLoggingInterceptor} and
 * {@link FeignResponseLoggingInterceptor}. Also keeps the per-thread call timer.
 *
 * Key/value data (headers, cookies, path variables, query params) is rendered on one line as
 * {@code [{name=value}, {name=[v1, v2]}]}; an empty collection is rendered as {@code []}.
 */
public class FeignLogFormatter {

    private static final String MASK = "*****";
    private static final int LABEL_WIDTH = 24;
    private static final Pattern PATH_VARIABLE = Pattern.compile("\\{([^}/]+)\\}");
    private static final Set<String> SENSITIVE_HEADERS = new HashSet<String>(Arrays.asList(
            "authorization", "proxy-authorization", "cookie", "set-cookie", "x-api-key", "x-auth-token"));

    private final int maxBodyChars;
    private final boolean maskSensitive;
    private final ThreadLocal<Long> callStartNanos = new ThreadLocal<Long>();

    /**
     * @param maxBodyChars  max characters of a body to print; {@code <= 0} means unlimited
     * @param maskSensitive replace Authorization / Cookie / Set-Cookie / API-key values with *****
     */
    public FeignLogFormatter(int maxBodyChars, boolean maskSensitive) {
        this.maxBodyChars = maxBodyChars;
        this.maskSensitive = maskSensitive;
    }

    // ------------------------------------------------------------------ timing

    void markCallStart() {
        callStartNanos.set(System.nanoTime());
    }

    /** @return millis since {@link #markCallStart()} on this thread, or -1 if it was not recorded */
    long elapsedMillisAndClear() {
        Long start = callStartNanos.get();
        callStartNanos.remove();
        return start == null ? -1L : TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
    }

    // ------------------------------------------------------------- layout helpers

    static void field(StringBuilder sb, String label, Object value) {
        StringBuilder padded = new StringBuilder(label);
        while (padded.length() < LABEL_WIDTH) {
            padded.append(' ');
        }
        sb.append("  ").append(padded).append(": ").append(value).append('\n');
    }

    private static String pair(String name, Object value) {
        return "{" + name + "=" + value + "}";
    }

    /** No value -> "", one value -> the value, several values -> "[v1, v2]". */
    private static String values(Collection<String> values) {
        if (values == null || values.isEmpty()) {
            return "";
        }
        if (values.size() == 1) {
            String only = values.iterator().next();
            return only == null ? "" : only;
        }
        return values.toString();
    }

    private static String list(List<String> items) {
        return "[" + String.join(", ", items) + "]";
    }

    // ------------------------------------------------------------------ headers

    /** e.g. {@code [{Accept=application/json}, {Content-Type=application/json}]} */
    String headers(Map<String, Collection<String>> headers) {
        if (headers == null || headers.isEmpty()) {
            return "[]";
        }
        Map<String, Collection<String>> sorted = new TreeMap<String, Collection<String>>(String.CASE_INSENSITIVE_ORDER);
        sorted.putAll(headers);
        List<String> items = new ArrayList<String>();
        for (Map.Entry<String, Collection<String>> entry : sorted.entrySet()) {
            String name = entry.getKey();
            boolean mask = maskSensitive && SENSITIVE_HEADERS.contains(name.toLowerCase(Locale.ROOT));
            items.add(pair(name, mask ? MASK : values(entry.getValue())));
        }
        return list(items);
    }

    static String firstHeader(Map<String, Collection<String>> headers, String name) {
        List<String> values = headerValues(headers, name);
        return values.isEmpty() ? null : values.get(0);
    }

    static List<String> headerValues(Map<String, Collection<String>> headers, String name) {
        List<String> result = new ArrayList<String>();
        if (headers == null) {
            return result;
        }
        for (Map.Entry<String, Collection<String>> entry : headers.entrySet()) {
            if (entry.getKey() != null && entry.getKey().equalsIgnoreCase(name) && entry.getValue() != null) {
                for (String value : entry.getValue()) {
                    if (value != null) {
                        result.add(value);
                    }
                }
            }
        }
        return result;
    }

    // ------------------------------------------------------------------ cookies

    /** Parses the request {@code Cookie} header ("a=1; b=2") into {@code [{a=1}, {b=2}]}. */
    String requestCookies(Map<String, Collection<String>> headers) {
        List<String> items = new ArrayList<String>();
        for (String line : headerValues(headers, "Cookie")) {
            for (String cookie : line.split(";")) {
                String trimmed = cookie.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }
                int eq = trimmed.indexOf('=');
                String name = eq >= 0 ? trimmed.substring(0, eq) : trimmed;
                String value = eq >= 0 ? trimmed.substring(eq + 1) : "";
                items.add(pair(name, maskSensitive ? MASK : value));
            }
        }
        return list(items);
    }

    /** Parses every {@code Set-Cookie} header into {@code [{name=value, attributes=[Path=/, HttpOnly]}]}. */
    String responseCookies(Map<String, Collection<String>> headers) {
        List<String> items = new ArrayList<String>();
        for (String line : headerValues(headers, "Set-Cookie")) {
            String[] parts = line.split(";");
            String first = parts[0].trim();
            int eq = first.indexOf('=');
            String name = eq >= 0 ? first.substring(0, eq) : first;
            String value = eq >= 0 ? first.substring(eq + 1) : "";
            StringBuilder item = new StringBuilder("{").append(name).append('=').append(maskSensitive ? MASK : value);
            if (parts.length > 1) {
                List<String> attributes = new ArrayList<String>();
                for (int i = 1; i < parts.length; i++) {
                    attributes.add(parts[i].trim());
                }
                item.append(", attributes=").append(attributes);
            }
            items.add(item.append('}').toString());
        }
        return list(items);
    }

    // ------------------------------------------------------ query / path variables

    /** e.g. {@code [{expand=true}, {tag=[a, b]}]} */
    String queries(Map<String, Collection<String>> queries) {
        if (queries == null || queries.isEmpty()) {
            return "[]";
        }
        List<String> items = new ArrayList<String>();
        for (Map.Entry<String, Collection<String>> entry : queries.entrySet()) {
            items.add(pair(entry.getKey(), values(entry.getValue())));
        }
        return list(items);
    }

    /**
     * Rebuilds the path-variable values by matching the unresolved template path ("/users/{id}")
     * against the resolved path ("/users/42"), giving {@code [{id=42}]}.
     */
    String pathVariables(String templatePath, String resolvedPath) {
        if (templatePath == null || resolvedPath == null) {
            return "[]";
        }
        Matcher matcher = PATH_VARIABLE.matcher(templatePath);
        List<String> names = new ArrayList<String>();
        StringBuilder regex = new StringBuilder("^");
        int last = 0;
        while (matcher.find()) {
            regex.append(Pattern.quote(templatePath.substring(last, matcher.start())));
            regex.append("([^/]+)");
            names.add(matcher.group(1).split(":")[0]);
            last = matcher.end();
        }
        if (names.isEmpty()) {
            return "[]";
        }
        regex.append(Pattern.quote(templatePath.substring(last))).append('$');
        Matcher values = Pattern.compile(regex.toString()).matcher(resolvedPath);
        if (!values.matches()) {
            return "(could not map " + names + " from the resolved path)";
        }
        List<String> items = new ArrayList<String>();
        for (int i = 0; i < names.size(); i++) {
            items.add(pair(names.get(i), values.group(i + 1)));
        }
        return list(items);
    }

    /** Which Java method argument feeds which template variable, e.g. {@code [{arg[0]=id}]}. */
    String parameterBindings(Map<Integer, Collection<String>> indexToName) {
        List<String> items = new ArrayList<String>();
        for (Map.Entry<Integer, Collection<String>> entry : new TreeMap<Integer, Collection<String>>(indexToName).entrySet()) {
            items.add(pair("arg[" + entry.getKey() + "]", values(entry.getValue())));
        }
        return list(items);
    }

    // --------------------------------------------------------------------- body

    /**
     * Describes a body: "(empty)", a binary summary, or "(N bytes, charset)" followed by the text.
     * A gzip-encoded body is inflated first when possible.
     */
    String bodyBlock(byte[] body, Charset charset, String contentType, String contentEncoding) {
        if (body == null || body.length == 0) {
            return "(empty)";
        }
        byte[] data = body;
        String origin = "";
        if (contentEncoding != null && contentEncoding.toLowerCase(Locale.ROOT).contains("gzip")) {
            byte[] inflated = gunzip(body);
            if (inflated != null) {
                data = inflated;
                origin = ", gunzipped from " + body.length + " bytes";
            }
        }
        if (!isText(contentType, data)) {
            return "<binary content, " + data.length + " bytes" + origin
                    + (contentType != null ? ", content-type=" + contentType : "") + ">";
        }
        Charset cs = charset != null ? charset : StandardCharsets.UTF_8;
        return "(" + data.length + " bytes, " + cs.name() + origin + ")\n" + truncate(new String(data, cs));
    }

    String truncate(String text) {
        if (maxBodyChars > 0 && text.length() > maxBodyChars) {
            return text.substring(0, maxBodyChars) + "\n... [truncated, " + text.length() + " chars in total]";
        }
        return text;
    }

    private static boolean isText(String contentType, byte[] data) {
        if (contentType != null) {
            String ct = contentType.toLowerCase(Locale.ROOT);
            return ct.startsWith("text/") || ct.contains("json") || ct.contains("xml")
                    || ct.contains("x-www-form-urlencoded") || ct.contains("javascript")
                    || ct.contains("yaml") || ct.contains("graphql") || ct.contains("csv");
        }
        // No content type: treat as text unless it contains NUL bytes.
        for (int i = 0; i < Math.min(data.length, 1024); i++) {
            if (data[i] == 0) {
                return false;
            }
        }
        return true;
    }

    private static byte[] gunzip(byte[] data) {
        try (GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(data));
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] chunk = new byte[4096];
            int read;
            while ((read = in.read(chunk)) != -1) {
                out.write(chunk, 0, read);
            }
            return out.toByteArray();
        } catch (Exception ex) {
            return null; // not actually gzip (the client may already have inflated it)
        }
    }
}