/*
 * Copyright (c) 2013-2021.  Urban Airship and Contributors
 */

package com.urbanairship.api.inbox;


import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.model.GenericResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The InboxDeleteRequest class builds delete rich push requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class InboxDeleteRequest implements Request<GenericResponse> {

    private final String pushId;

    private InboxDeleteRequest(String pushId) {
        this.pushId = pushId;
    }

    /**
     * Create the delete request.
     *
     * @param pushId String
     * @return InboxDeleteRequest
     */
    public static InboxDeleteRequest newRequest(String pushId) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(pushId), "Push ID may not be null");
        return new InboxDeleteRequest(pushId);
    }

    @Override
    public ContentType getContentType() {
        return null;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.DELETE;
    }

    @Override
    public String getRequestBody() {
        return null;
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, "/api/user/messages/" + pushId);
    }

    @Override
    public ResponseParser<GenericResponse> getResponseParser() {
        return RequestUtils.GENERIC_RESPONSE_PARSER;
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
