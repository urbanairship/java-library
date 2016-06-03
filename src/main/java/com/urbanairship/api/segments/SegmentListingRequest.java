/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.segments.model.SegmentListingResponse;
import com.urbanairship.api.segments.parse.SegmentObjectMapper;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The SegmentListingRequest class builds segment listing requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class SegmentListingRequest implements Request<SegmentListingResponse> {
    private final static String API_SEGMENTS_PATH = "/api/segments/";

    /**
     * Create new request for segment listing.
     *
     * @return SegmentRequest
     */
    public static SegmentListingRequest newRequest() {
        return new SegmentListingRequest();
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
        return RequestUtils.resolveURI(baseUri, API_SEGMENTS_PATH);
    }

    @Override
    public ResponseParser<SegmentListingResponse> getResponseParser() {
        return new ResponseParser<SegmentListingResponse>() {
            @Override
            public SegmentListingResponse parse(String response) throws IOException {
                return SegmentObjectMapper.getInstance().readValue(response, SegmentListingResponse.class);
            }
        };
    }
}