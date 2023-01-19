/*
 * Copyright (c) 2013-2022.  Airship and Contributors
 */

package com.urbanairship.api.attributelists;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.attributelists.model.AttributeListsListingResponse;
import com.urbanairship.api.attributelists.parse.AttributeListsObjectMapper;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * The AttributeListsListingRequest class builds attribute lists listing requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class AttributeListsListingRequest implements Request<AttributeListsListingResponse> {
    private final static String ATTRIBUTE_LISTS_PATH = "/api/attribute-lists/";


    /**
     * Create a request to list all attribute lists.
     *
     * @return AttributeListsListingRequest
     */
    public static AttributeListsListingRequest newRequest() {
        return new AttributeListsListingRequest();
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
    public Request.HttpMethod getHttpMethod() {
        return Request.HttpMethod.GET;
    }

    @Override
    public String getRequestBody() {
        return null;
    }

    @Override
    public URI getUri(URI baseUri) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(RequestUtils.resolveURI(baseUri, ATTRIBUTE_LISTS_PATH));

        return builder.build();
    }

    @Override
    public ResponseParser<AttributeListsListingResponse> getResponseParser() {
        return response -> AttributeListsObjectMapper.getInstance().readValue(response, AttributeListsListingResponse.class);
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
    }

    @Override
    public boolean canUseBearerTokenAuth() {
        return true;
    }
}
