package com.urbanairship.api.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Future;

public abstract class RequestClient {
    private static final Logger log = LoggerFactory.getLogger(RequestClient.class);


    public abstract <T> Response execute(Request<T> request, ResponseCallback callback);
    public abstract <T> Response execute(Request<T> request);

    public abstract <T> Future<Response> executeAsync(final Request<T> request, final ResponseCallback callback);
    public abstract <T> Future<Response> executeAsync(final Request<T> request);

    public abstract String getAppKey();
    public abstract Optional<String> getAppSecret();
    public abstract Optional<String> getBearerToken();

    public abstract void close() throws IOException;

    /**
     * Retrieve the client user agent.
     *
     * @return The user agent.
     */
    @VisibleForTesting
    public String getUserAgent() {
        String userAgent = "UNKNOWN";
        InputStream stream = getClass().getResourceAsStream("/client.properties");

        if (stream != null) {
            Properties props = new Properties();
            try {
                props.load(stream);
                stream.close();
                userAgent = "UAJavaLib/" + props.get("client.version");
            } catch (IOException e) {
                log.error("Failed to retrieve client user agent due to IOException - setting to \"UNKNOWN\"", e);
            }
        }
        return userAgent;
    }
}
