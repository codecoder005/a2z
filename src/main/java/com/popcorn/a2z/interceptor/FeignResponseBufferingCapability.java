package com.popcorn.a2z.interceptor;

import feign.Capability;
import feign.Client;
import feign.Response;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Locale;

/**
 * Makes every response body repeatable (held in memory) so that
 * {@link FeignResponseLoggingInterceptor} can read it for logging and the decoder can still read it.
 *
 * Bodies larger than {@code maxBufferedBytes}, and server-sent-event streams, are left untouched
 * (the logger then prints a note instead of the body).
 */
public class FeignResponseBufferingCapability implements Capability {

    private final long maxBufferedBytes;

    public FeignResponseBufferingCapability(long maxBufferedBytes) {
        this.maxBufferedBytes = maxBufferedBytes;
    }

    @Override
    public Client enrich(Client client) {
        return (request, options) -> buffer(client.execute(request, options));
    }

    Response buffer(Response response) throws IOException {
        if (response == null) {
            return null;
        }
        Response.Body body = response.body();
        if (body == null || body.isRepeatable()) {
            return response;
        }
        String contentType = FeignLogFormatter.firstHeader(response.headers(), "Content-Type");
        if (contentType != null && contentType.toLowerCase(Locale.ROOT).startsWith("text/event-stream")) {
            return response; // never buffer a stream
        }
        Integer length = body.length();
        if (length != null && length > maxBufferedBytes) {
            return response;
        }

        InputStream in = body.asInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        boolean complete = false;
        try {
            byte[] chunk = new byte[8192];
            while (out.size() < maxBufferedBytes) {
                int wanted = (int) Math.min(chunk.length, maxBufferedBytes - out.size());
                int read = in.read(chunk, 0, wanted);
                if (read == -1) {
                    complete = true;
                    break;
                }
                out.write(chunk, 0, read);
            }
        } catch (IOException | RuntimeException ex) {
            closeQuietly(in);
            throw ex;
        }

        if (complete) {
            closeQuietly(in);
            return response.toBuilder().body(out.toByteArray()).build();
        }
        // Hit the size cap with data left: stitch what we read back in front of the rest of the stream.
        InputStream rejoined = new SequenceInputStream(new ByteArrayInputStream(out.toByteArray()), in);
        return response.toBuilder().body(rejoined, length).build();
    }

    private static void closeQuietly(InputStream in) {
        try {
            in.close();
        } catch (IOException ignored) {
            // nothing useful to do
        }
    }
}