/*
 * Copyright (c) 2013-2015. Urban Airship and Contributors
 */

package com.urbanairship.api.reports;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
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
    private final static String API_PUSH_RESPONSE_LISTING = "/api/reports/responses/list";

    private final DateTime start;
    private final DateTime end;
    private final Optional<Integer> limit;
    private final Optional<String> pushIdStart;

    private PushListingRequest() {
        this(null,null,null,null);
    }

    private PushListingRequest(DateTime start,
                               DateTime end,
                               Optional<Integer> limit,
                               Optional<String> pushIdStart)
    {
        this.start = start;
        this.end = end;
        this.limit = limit;
        this.pushIdStart = pushIdStart;
    }

    /**
     * New PushInfoRequest builder
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
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
     * Get the request end date
     *
     * @return DateTime
     */
    public DateTime getEnd() {
        return end;
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
     * Get the starting push ID
     *
     * @return String
     */
    public Optional<String> getPushIdStart() {
        return pushIdStart;
    }

    @Override
    public ContentType getContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, UA_VERSION);
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
    public URI getUri(URI baseUri) {
        URI uri;
        URIBuilder builder = new URIBuilder(RequestUtils.resolveURI(baseUri, API_PUSH_RESPONSE_LISTING));
        builder.addParameter("start", this.start.toString());
        builder.addParameter("end", this.end.toString());

        if (this.limit.isPresent())
            builder.addParameter("limit", Integer.toString(this.limit.get()));

        if (this.pushIdStart.isPresent())
            builder.addParameter("push_id_start", this.pushIdStart.get());

        try {
            uri = builder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return uri;
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

    public static class Builder {

        // required
        private DateTime start;
        private DateTime end;

        // optional
        private Integer limit;
        private String pushIdStart;

        private Builder() {
        }

        /**
         * Set the start date
         *
         * @param value DateTime
         * @return Builder
         */
        public Builder start(DateTime value) {
            this.start = value;
            return this;
        }

        /**
         * Set the end date
         *
         * @param value DateTime
         * @return Builder
         */
        public Builder end(DateTime value) {
            this.end = value;
            return this;
        }

        /**
         * Set the limit
         *
         * @param value Integer
         * @return Builder
         */
        public Builder limit(Integer value) {
            this.limit = value;
            return this;
        }

        /**
         * Set the starting push ID
         *
         * @param value String
         * @return Builder
         */
        public Builder pushIdStart(String value) {
            this.pushIdStart = value;
            return this;
        }

        /**
         * Build the PushListingRequest object
         *
         * <pre>
         * 1. start cannot be null
         * 2. end cannot be null
         * 3. end must be chronologically later than start
         * </pre>
         *
         * @return PushListingRequest
         */
        public PushListingRequest build() {
            Preconditions.checkNotNull(start);
            Preconditions.checkNotNull(end);
            Preconditions.checkArgument(end.isAfter(start));

            return new PushListingRequest(
                    start,
                    end,
                    Optional.fromNullable(limit),
                    Optional.fromNullable(pushIdStart));
        }
    }
}
