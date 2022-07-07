/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.segments;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.model.GenericResponse;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.segments.model.SegmentView;
import com.urbanairship.api.segments.parse.SegmentObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The SegmentRequest class builds segment requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class SegmentRequest implements Request<GenericResponse> {
    private final static String API_SEGMENTS_PATH = "/api/segments/";
    private final SegmentView.Builder builder = SegmentView.newBuilder();
    private final String path;

    private SegmentRequest(String path) {
        this.path = path;
    }

    /**
     * Create a new segment creation request.
     *
     * @return SegmentRequest
     */
    public static SegmentRequest newRequest() {
        return new SegmentRequest(API_SEGMENTS_PATH);
    }

    /**
     * Create a new segment update request.
     *
     * @param segmentId String
     * @return SegmentRequest
     */
    public static SegmentRequest newUpdateRequest(String segmentId) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(segmentId), "Segment ID may not be null");
        return new SegmentRequest(API_SEGMENTS_PATH + segmentId);
    }

    /**
     * Set the criteria for the segment request.
     *
     * @param criteria Selector
     * @return SegmentRequest
     */
    public SegmentRequest setCriteria(Selector criteria) {
        this.builder.setCriteria(criteria);
        return this;
    }

    /**
     * Set the display name of the segment request.
     *
     * @param displayName String
     * @return SegmentRequest
     */
    public SegmentRequest setDisplayName(String displayName) {
        this.builder.setDisplayName(displayName);
        return this;
    }

    /**
     * Get the content type.
     * @return ContentType
     */
    @Override
    public ContentType getContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_JSON);
        return headers;
    }

    @Override
    public HttpMethod getHttpMethod() {
        if (path == API_SEGMENTS_PATH) {
            return HttpMethod.POST;
        }
        return HttpMethod.PUT;
    }

    @Override
    public String getRequestBody() {
        SegmentView segment = builder.build();
        try {
            return SegmentObjectMapper.getInstance().writeValueAsString(segment);
        } catch (Exception ex) {
            return "{ \"exception\" : \"" + ex.getClass().getName() + "\", \"message\" : \"" + ex.getMessage() + "\" }";
        }
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
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
        return true;
    }
}
