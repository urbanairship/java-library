package com.urbanairship.api.journeys;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.model.GenericResponse;
import com.urbanairship.api.journeys.model.JourneyTriggerPayload;
import com.urbanairship.api.journeys.parse.JourneysObjectMapper;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Request for POST /api/journeys/trigger.
 *
 * <p>Enters an audience into a Sequence identified by a {@code triggering_id}.
 * If multiple Sequences share a {@code triggering_id}, all of them are triggered.
 *
 * <p><strong>Note:</strong> This endpoint only supports OAuth 2.0 authentication
 * (scope {@code jny}). Configure your {@code UrbanAirshipClient} with
 * {@code OAuthCredentials} — Basic Auth and static Bearer tokens are not accepted.
 *
 * @see <a href="https://www.airship.com/docs/developer/rest-api/ua/operations/journeys/#triggerjourneyssegment">
 *      Airship Journeys API — Enter a Sequence</a>
 */
public class JourneyTriggerRequest implements Request<GenericResponse> {

    private static final String API_JOURNEYS_TRIGGER_PATH = "/api/journeys/trigger";

    private final JourneyTriggerPayload payload;

    private JourneyTriggerRequest(JourneyTriggerPayload payload) {
        Preconditions.checkNotNull(payload, "Payload required when creating a journey trigger request");
        this.payload = payload;
    }

    /**
     * Create a new trigger request with the given payload.
     *
     * @param payload JourneyTriggerPayload
     * @return JourneyTriggerRequest
     */
    public static JourneyTriggerRequest newRequest(JourneyTriggerPayload payload) {
        return new JourneyTriggerRequest(payload);
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
        return RequestUtils.resolveURI(baseUri, API_JOURNEYS_TRIGGER_PATH);
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
