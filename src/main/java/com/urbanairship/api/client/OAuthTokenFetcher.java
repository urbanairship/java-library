package com.urbanairship.api.client;

import com.google.common.io.BaseEncoding;
import com.urbanairship.api.client.parse.OAuthTokenResponse;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class OAuthTokenFetcher {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public OAuthTokenFetcher() {
        this.httpClient = HttpClients.createDefault();
        this.objectMapper = new ObjectMapper();
    }

    public OAuthTokenResponse fetchToken(OAuthCredentials credentials, String baseOAuthURL) throws IOException {
        int maxRetries = 3;
        int retryCount = 0;
        boolean success = false;

        String accessToken = null;
        long expiresIn = 0;
        while (!success && retryCount < maxRetries) {
            if (retryCount > 0) {
                try {
                    Thread.sleep(1000 * (long) Math.pow(2, retryCount));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Interrupted while waiting to retry token fetch", e);
                }
            }

            HttpPost post = new HttpPost(baseOAuthURL + "/token");
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
            post.setHeader("Accept", "application/json");

            if (credentials.getClientSecret().isPresent()) {
                String clientId = credentials.getClientId();
                String clientSecret = credentials.getClientSecret().get();
                String authHeader = BaseEncoding.base64().encode((clientId + ":" + clientSecret).getBytes());
                post.setHeader("Authorization", "Basic " + authHeader);
            }

            List<NameValuePair> params = credentials.toParams();
            post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

            HttpResponse response = httpClient.execute(post);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                Map<String, Object> responseMap = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>() {
                });
                if (responseMap.containsKey("access_token")) {
                    accessToken = (String) responseMap.get("access_token");
                    expiresIn = (Integer) responseMap.get("expires_in");
                    success = true;
                } else {
                    throw new IOException("Failed to obtain access token: " + jsonResponse);
                }
            } else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST ||
                    response.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED ||
                    response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_ACCEPTABLE) {
                throw new IOException("Failed to obtain access token with status code: " +
                        response.getStatusLine().getStatusCode() +
                        ". Response: " + EntityUtils.toString(response.getEntity()));
            } else {
                retryCount++;
            }
        }

        if (!success) {
            throw new IOException("Failed to obtain access token after " + maxRetries + " attempts.");
        }

        return new OAuthTokenResponse(accessToken, expiresIn);
    }
}
