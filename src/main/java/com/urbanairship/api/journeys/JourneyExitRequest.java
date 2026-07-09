package com.urbanairship.api.journeys;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.model.GenericResponse;
import com.urbanairship.api.journeys.model.JourneyExitPayload;
import com.urbanairship.api.journeys.parse.JourneysObjectMapper;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Request for POST /api/journeys/exit.
 *
 * <p>Exits in-flight users from a Sequence identified by a {@code triggering_id}.
 * This is a potentially long-running, asynchronous operation.
 *
 * <p><strong>Note:</strong> This endpoint only supports OAuth 2.0 authentication
 * (scope {@code jny}). Configure your {@code UrbanAirshipClient} with
 * {@code OAuthCredentials} — Basic Auth and static Bearer tokens are not accepted.
 *
 * @see <a href="https://www.airship.com/docs/developer/rest-api/ua/operations/journeys/#exitjourneys">
 *      Airship Journeys API — Exit a Sequence</a>
 */
public class JourneyExitRequest implements Request<GenericResponse> {

    private static final String API_JOURNEYS_EXIT_PATH = "/api/journeys/exit";

    private final JourneyExitPayload payload;

    private JourneyExitRequest(JourneyExitPayload payload) {
        Preconditions.checkNotNull(payload, "Payload required when creating a journey exit request");
        this.payload = payload;
    }

    /**
     * Create a new exit request with the given payload.
     *
     * @param payload JourneyExitPayload
     * @return JourneyExitRequest
     */
    public static JourneyExitRequest newRequest(JourneyExitPayload payload) {
        return new JourneyExitRequest(payload);
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        try {
            return JourneysObjectMapper.getInstance().writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ContentType getContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public URI getUri(URI baseUri) throws URISyntaxException {
        return RequestUtils.resolveURI(baseUri, API_JOURNEYS_EXIT_PATH);
    }

    @Override
    public ResponseParser<GenericResponse> getResponseParser() {
        return response -> JourneysObjectMapper.getInstance().readValue(response, GenericResponse.class);
    }

    /**
     * Returns {@code false}: this endpoint does not require a static Bearer token.
     * Use {@code OAuthCredentials} on the client instead.
     */
    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
    }

    /**
     * Returns {@code false}: this endpoint does not accept static Bearer tokens.
     * Use {@code OAuthCredentials} on the client instead.
     */
    @Override
    public boolean canUseBearerTokenAuth() {
        return false;
    }
}
