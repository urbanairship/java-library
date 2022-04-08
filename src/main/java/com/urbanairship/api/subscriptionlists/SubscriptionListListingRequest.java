/*
 * Copyright (c) 2013-2022.  Urban Airship and Contributors
 */

package com.urbanairship.api.subscriptionlists;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.subscriptionlists.model.SubscriptionListListingResponse;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The SubscriptionListsListingRequest class builds subscription list listing requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class SubscriptionListListingRequest implements Request<SubscriptionListListingResponse> {
    private final static String API_SUBSCRIPTION_LISTS_PATH = "/api/subscription_lists/";

    private final String path;
    private final static ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.registerModule(new GuavaModule());
    }


    private SubscriptionListListingRequest(String path) { this.path = path; }

    /**
     * Create a request to list all subscription lists.
     *
     * @return SubscriptionListListingRequest
     */
    public static SubscriptionListListingRequest newRequest() {
        return new SubscriptionListListingRequest(API_SUBSCRIPTION_LISTS_PATH);
    }

    @Override
    public ContentType getContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(org.apache.http.HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
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
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<SubscriptionListListingResponse> getResponseParser() {
        return response -> MAPPER.readValue(response, SubscriptionListListingResponse.class);
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
