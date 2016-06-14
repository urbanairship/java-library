/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.nameduser;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.nameduser.model.NamedUserListingResponse;
import com.urbanairship.api.nameduser.parse.NamedUserObjectMapper;
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

    private final String path;

    private NamedUserListingRequest(String path) {
        this.path = path;
    }

    /**
     * Create named user lookup request.
     *
     * @param namedUserId String
     * @return NamedUserListingRequest
     */
    public static NamedUserListingRequest newRequest(String namedUserId) {
        return new NamedUserListingRequest(API_NAMED_USERS_GET + "?id=" + namedUserId);
    }

    /**
     * Create named user listing request.
     *
     * @return NamedUserListingRequest
     */
    public static NamedUserListingRequest newRequest() {
        return new NamedUserListingRequest(API_NAMED_USERS_GET);
    }

    /**
     * Create a request for named user listing using a next page URI.
     *
     * @param nextPage URI
     * @return NamedUserListingRequest
     */
    public static NamedUserListingRequest newRequest(URI nextPage) {
        Preconditions.checkNotNull(nextPage, "Next page URI cannot be null");
        return new NamedUserListingRequest(nextPage.getPath() + "?" + nextPage.getQuery());
    }


    @Override
    public ContentType getContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
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
        return RequestUtils.resolveURI(baseUri, path);
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