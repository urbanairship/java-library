/*
 * Copyright (c) 2013-2022.  Urban Airship and Contributors
 */

package com.urbanairship.api.tags;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.tags.model.TagListListingResponse;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The TagListListingRequest class builds tag list listing requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class TagListListingRequest implements Request<TagListListingResponse> {
    private final static String TAG_LISTS_PATH = "/api/tag-lists";

    private final static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JodaModule());
        mapper.registerModule(new GuavaModule());
    }

    private TagListListingRequest() {}

    /**
     * Create a request to list all tag lists.
     *
     * @return TagListListingRequest
     */
    public static TagListListingRequest newRequest() {
        return new TagListListingRequest();
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
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, TAG_LISTS_PATH);
    }

    @Override
    public ResponseParser<TagListListingResponse> getResponseParser() {
        return response -> mapper.readValue(response, TagListListingResponse.class);
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
