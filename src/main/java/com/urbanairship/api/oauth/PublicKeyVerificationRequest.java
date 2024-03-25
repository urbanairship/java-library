package com.urbanairship.api.oauth;

import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.ResponseParser;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class PublicKeyVerificationRequest implements Request<String> {

    private final String keyId;
    private final boolean useEuropeanSite;

    private PublicKeyVerificationRequest(String keyId, boolean useEuropeanSite) {
        this.keyId = keyId;
        this.useEuropeanSite = useEuropeanSite;
    }

    private PublicKeyVerificationRequest(String keyId) {
        this(keyId, false);
    }

    public static PublicKeyVerificationRequest newRequest(String keyId, boolean useEuropeanSite) {
        return new PublicKeyVerificationRequest(keyId, useEuropeanSite);
    }

    public static PublicKeyVerificationRequest newRequest(String keyId) {
        return new PublicKeyVerificationRequest(keyId);
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getRequestBody() {
        return null;
    }

    @Override
    public ContentType getContentType() {
        return ContentType.TEXT_PLAIN;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/plain");
        return headers;
    }

    @Override
    public URI getUri(URI baseUri) throws URISyntaxException {
        String baseEndpoint = useEuropeanSite ? "https://oauth2.asnapieu.com" : "https://oauth2.asnapius.com";
        return new URI(baseEndpoint + "/verify/public_key/" + this.keyId);
    }

    @Override
    public ResponseParser<String> getResponseParser() {
        return response -> response;
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
    }

    @Override
    public boolean canUseBearerTokenAuth() {
        return false;
    }
}
