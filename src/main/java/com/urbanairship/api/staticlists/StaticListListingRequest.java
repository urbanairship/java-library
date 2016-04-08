/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.staticlists;

import com.google.common.base.Optional;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.staticlists.model.StaticListListingResponse;
import com.urbanairship.api.staticlists.parse.StaticListsObjectMapper;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * The StaticListListingRequest class builds static list listing requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class StaticListListingRequest implements Request<StaticListListingResponse> {
    private final static String API_LISTS_PATH = "/api/lists/";
    private final static String TYPE_PARAM = "type";
    private Optional<ListType> type;

    public enum ListType {all, lifecycle, user}

    private StaticListListingRequest() {
        this.type = Optional.absent();
    }

    /**
     * Create a request to list all static lists.
     *
     * @return StaticListListingRequest
     */
    public static StaticListListingRequest newRequest() {
        return new StaticListListingRequest();
    }

    /**
     * Set the type of lists to be returned.
     *
     * @param type StaticListType representing the list type
     * @return StaticListListingRequest
     */
    public StaticListListingRequest type(ListType type) {
        this.type = Optional.of(type);
        return this;
    }

    @Override
    public ContentType getContentType() {
        return null;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, UA_VERSION);
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
        URIBuilder builder = new URIBuilder(RequestUtils.resolveURI(baseUri, API_LISTS_PATH));

        if (type.isPresent()) {
            builder.addParameter(TYPE_PARAM, this.type.get().toString());
        }

        return builder.build();
    }

    @Override
    public ResponseParser<StaticListListingResponse> getResponseParser() {
        return new ResponseParser<StaticListListingResponse>() {
            @Override
            public StaticListListingResponse parse(String response) throws IOException {
                return StaticListsObjectMapper.getInstance().readValue(response, StaticListListingResponse.class);
            }
        };
    }
}
