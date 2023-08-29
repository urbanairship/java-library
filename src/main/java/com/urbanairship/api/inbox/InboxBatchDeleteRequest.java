package com.urbanairship.api.inbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.inbox.model.InboxBatchDeleteResponse;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The InboxBatchDeleteRequest class builds delete rich pushes requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class InboxBatchDeleteRequest implements Request<InboxBatchDeleteResponse> {
    private final static ObjectMapper MAPPER = new ObjectMapper();
    private final static String API_INBOX_BATCH_DELETE = "/api/user/messages/batch-delete/";
    private final Map<String, List<String>> payload = new HashMap<>();

    private InboxBatchDeleteRequest(List<String> messageIds) {
        payload.put(Constants.MESSAGE_IDS, messageIds);
    }

    /**
     * Create new inbox batch delete request.
     *
     * @param messageIds List of strings.
     * @return InboxBatchDeleteRequest
     */
    public static InboxBatchDeleteRequest newRequest(List<String> messageIds) {
        return new InboxBatchDeleteRequest(messageIds);
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
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        try {
            return ChannelObjectMapper.getInstance().writeValueAsString(payload);
        } catch (Exception ex) {
            return "{ \"exception\" : \"" + ex.getClass().getName() + "\", \"message\" : \"" + ex.getMessage() + "\" }";
        }
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, API_INBOX_BATCH_DELETE);
    }

    @Override
    public ResponseParser<InboxBatchDeleteResponse> getResponseParser() {
        return response -> MAPPER.readValue(response,InboxBatchDeleteResponse.class);
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