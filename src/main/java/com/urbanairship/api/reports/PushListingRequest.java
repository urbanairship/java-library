/*
 * Copyright (c) 2013-2016. Urban Airship and Contributors
 */

package com.urbanairship.api.reports;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.reports.model.PushListingResponse;
import com.urbanairship.api.reports.parse.ReportsObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


/**
 * The PushListingRequest class builds push listing requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}
 */
public class PushListingRequest implements Request<PushListingResponse> {
    private final static String API_PUSH_RESPONSE_LISTING = "/api/reports/responses/list/";
    private final String path;
    private final boolean nextPageRequest;
    private DateTime start;
    private DateTime end;
    private Optional<Integer> limit = Optional.absent();
    private Optional<String> pushIdStart = Optional.absent();

    private PushListingRequest(String path, boolean nextPageRequest) {
        this.path = path;
        this.nextPageRequest = nextPageRequest;
    }

    /**
     * New PushListingRequest
     *
     * @return PushListingRequest
     */
    public static PushListingRequest newRequest() {
        return new PushListingRequest(API_PUSH_RESPONSE_LISTING, false);
    }

    /**
     * Create a new push listing request listing using a next page URI.
     *
     * @param nextPage URI
     * @return PushListingRequest
     */
    public static PushListingRequest newRequest(URI nextPage) {
        Preconditions.checkNotNull(nextPage, "Next page URI cannot be null");
        return new PushListingRequest(nextPage.getPath() + "?" + nextPage.getQuery(), true);
    }

    /**
     * Get the request start date
     *
     * @return DateTime
     */
    public DateTime getStart() {
        return start;
    }

    /**
     * Set the request start date
     *
     * @param start DateTime
     * @return PushListingRequest
     */
    public PushListingRequest setStart(DateTime start) {
        this.start = start;
        return this;
    }

    /**
     * Get the request end date
     *
     * @return DateTime
     */
    public DateTime getEnd() {
        return end;
    }

    /**
     * Set the request end date
     *
     * @param end DateTime
     * @return PushListingRequest
     */
    public PushListingRequest setEnd(DateTime end) {
        this.end = end;
        return this;
    }

    /**
     * Get the page limit
     *
     * @return Integer
     */
    public Optional<Integer> getLimit() {
        return limit;
    }

    /**
     * Set the page limit
     *
     * @param limit Integer
     * @return Integer
     */
    public PushListingRequest setLimit(Integer limit) {
        this.limit = Optional.of(limit);
        return this;
    }

    /**
     * Get the starting push ID
     *
     * @return String
     */
    public Optional<String> getPushIdStart() {
        return pushIdStart;
    }

    /**
     * Set the starting push ID
     *
     * @param pushIdStart String
     * @return String
     */
    public PushListingRequest setPushIdStart(String pushIdStart) {
        this.pushIdStart = Optional.of(pushIdStart);
        return this;
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
        URIBuilder builder = new URIBuilder(RequestUtils.resolveURI(baseUri, path));

        if (!nextPageRequest) {
            Preconditions.checkNotNull(this.start, "start cannot be null");
            Preconditions.checkNotNull(this.end, "end cannot be null");
            Preconditions.checkArgument(end.isAfter(start), "end date must occur after start date");
            builder.addParameter("start", this.start.toString(DateFormats.DATE_FORMATTER));
            builder.addParameter("end", this.end.toString(DateFormats.DATE_FORMATTER));

            if (this.limit.isPresent())
                builder.addParameter("limit", Integer.toString(this.limit.get()));

            if (this.pushIdStart.isPresent())
                builder.addParameter("push_id_start", this.pushIdStart.get());
        }

        return builder.build();
    }

    @Override
    public ResponseParser<PushListingResponse> getResponseParser() {
        return new ResponseParser<PushListingResponse>() {
            @Override
            public PushListingResponse parse(String response) throws IOException {
                return ReportsObjectMapper.getInstance().readValue(response, PushListingResponse.class);
            }
        };
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
