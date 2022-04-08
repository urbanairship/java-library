/*
 * Copyright (c) 2013-2022. Urban Airship and Contributors
 */

package com.urbanairship.api.reports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.reports.model.ExperimentVariantReportResponse;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The ExperimentVariantReportRequest class builds a request to the individual variant
 * response statistics request to be executed in the
 * {@link com.urbanairship.api.client.UrbanAirshipClient}
 */
public class ExperimentVariantReportRequest implements Request<ExperimentVariantReportResponse> {
    private final static String API_EXPERIMENT_VARIANT_REPORT = "/api/reports/experiment/detail/";

    private final String path;
    private final static ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.registerModule(new GuavaModule());
    }

    private ExperimentVariantReportRequest(String path) {
        this.path = path;
    }

    /**
     * Request individual Experiment Variant Report for a pushId and its variantId.
     *
     * @param pushId String
     * @param variantId String
     * @return ExperimentVariantReportRequest
     */
    public static ExperimentVariantReportRequest newRequest(String pushId, String variantId) {
        return new ExperimentVariantReportRequest(API_EXPERIMENT_VARIANT_REPORT + pushId + "/" + variantId) ;
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
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<ExperimentVariantReportResponse> getResponseParser() {
        return response -> MAPPER.readValue(response, ExperimentVariantReportResponse.class);
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
