package com.urbanairship.api.nameduser;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.nameduser.model.NamedUserListingResponse;
import com.urbanairship.api.nameduser.parse.NamedUserObjectMapper;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * The NamedUserListingRequest class builds named user listing requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class NamedUserListingRequest implements Request<NamedUserListingResponse> {

    private final static String API_NAMED_USERS_GET = "/api/named_users/";

    private final String namedUserId;

    private NamedUserListingRequest(String namedUserId) {
        this.namedUserId = namedUserId;
    }

    /**
     * Create named user lookup request.
     *
     * @param namedUserId String
     * @return NamedUserListingRequest
     */
    public static NamedUserListingRequest createListRequest(String namedUserId) {
        return new NamedUserListingRequest(namedUserId);
    }

    /**
     * Create named user listing request.
     *
     * @return NamedUserListingRequest
     */
    public static NamedUserListingRequest createListAllRequest() {
        return new NamedUserListingRequest(null);
    }

    @Override
    public ContentType getContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION);
        return headers;
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
    public URI getUri(URI baseUri) throws URISyntaxException {
        if (namedUserId == null) {
            return RequestUtils.resolveURI(baseUri, API_NAMED_USERS_GET);
        } else {
            URIBuilder builder = new URIBuilder(RequestUtils.resolveURI(baseUri, API_NAMED_USERS_GET));
            builder.addParameter("id", namedUserId);
            return builder.build();
        }
    }

    @Override
    public ResponseParser<NamedUserListingResponse> getResponseParser() {
        return new ResponseParser<NamedUserListingResponse>() {
            @Override
            public NamedUserListingResponse parse(String response) throws IOException {
                return NamedUserObjectMapper.getInstance().readValue(response, NamedUserListingResponse.class);
            }
        };
    }
}