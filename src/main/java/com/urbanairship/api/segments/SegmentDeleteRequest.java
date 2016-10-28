/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The DeleteSegmentRequest class builds delete segment requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class SegmentDeleteRequest implements Request<String> {
    private final static String API_SEGMENTS_PATH = "/api/segments/";
    private final String path;

    private SegmentDeleteRequest(String path) {
        this.path = path;
    }

    /**
     * Create the delete request.
     *
     * @param segmentId String
     * @return DeleteSegmentRequest
     */
    public static SegmentDeleteRequest newRequest(String segmentId) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(segmentId), "Segment ID may not be null.");
        return new SegmentDeleteRequest(API_SEGMENTS_PATH + segmentId);
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
        return Request.HttpMethod.DELETE;
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
    public ResponseParser<String> getResponseParser() {
        return new ResponseParser<String>() {
            @Override
            public String parse(String response) throws IOException {
                return response;
            }
        };
    }
}
