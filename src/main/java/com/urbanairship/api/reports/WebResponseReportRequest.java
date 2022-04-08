/*
 * Copyright (c) 2013-2022. Urban Airship and Contributors
 */

package com.urbanairship.api.reports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.base.Preconditions;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.reports.model.Precision;
import com.urbanairship.api.reports.model.WebResponseReportResponse;
import org.apache.http.HttpHeaders;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * The WebResponseReportRequest class builds a request to the the web interaction data for the given app key
 * to be executed in the
 * {@link com.urbanairship.api.client.UrbanAirshipClient}
 */
public class WebResponseReportRequest implements Request<WebResponseReportResponse> {
    private final static String API_WEB_RESPONSE_REPORT = "/api/reports/web/interaction";

    private final String path;
    private final String appKey;
    private final DateTime start;
    private DateTime end;
    private Precision precision;
    private final static ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.registerModule(new GuavaModule());
    }

    private WebResponseReportRequest(String path, String appKey, DateTime start) {
        this.path = path;
        this.appKey =appKey;
        this.start = start;
    }

    /**
     * Request WebResponseReportRequest.
     *
     * @param appKey String
     * @param start DateTime
     * @return WebResponseReportRequest
     */
    public static WebResponseReportRequest newRequest(String appKey, DateTime start) {
        return new WebResponseReportRequest(API_WEB_RESPONSE_REPORT, appKey, start) ;
    }

    /**
     * Set the end date parameter.
     *
     * @param end DateTime
     * @return WebResponseReportRequest
     */
    public WebResponseReportRequest setEndTime(DateTime end) {
        this.end = end;
        return this;
    }

    /**
     * Set the precision parameter.
     *
     * @param precision Precision
     * @return WebResponseReportRequest
     */
    public WebResponseReportRequest setPrecision(Precision precision) {
        this.precision = precision;
        return this;
    }

    @Override
    public ContentType getContentType() {
        return ContentType.APPLICATION_JSON;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<>();
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
    public URI getUri(URI baseUri) {
        URI uri;
        URIBuilder builder = new URIBuilder(RequestUtils.resolveURI(baseUri, path));
        Preconditions.checkNotNull(start, "start cannot be null");

        builder.addParameter("app_key", this.appKey);
        builder.addParameter("start", this.start.toString(DateFormats.DATE_FORMATTER));

        if (this.precision != null) {
            builder.addParameter("precision", this.precision.toString());
        }
        if (this.end != null) {
            Preconditions.checkArgument(end.isAfter(start), "end date must occur after start date");
            builder.addParameter("end", this.end.toString(DateFormats.DATE_FORMATTER));
        }

        try {
            uri = builder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return uri;
    }

    @Override
    public ResponseParser<WebResponseReportResponse> getResponseParser() {
        return response -> MAPPER.readValue(response, WebResponseReportResponse.class);
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
