/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.templates;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.templates.model.TemplateResponse;
import com.urbanairship.api.templates.parse.TemplatesObjectMapper;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * The TemplateDeleteRequest class builds template deletion requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class TemplateDeleteRequest implements Request<TemplateResponse> {
    private final static String API_TEMPLATES_DELETE = "/api/templates/";
    private final String path;

    private TemplateDeleteRequest(String path) {
        this.path = path;
    }

    /**
     * Create a new template deletion request.
     *
     * @param templateId A template ID as a string
     * @return TemplateDeleteRequest
     */
    public static TemplateDeleteRequest newRequest(String templateId) {
        return new TemplateDeleteRequest(API_TEMPLATES_DELETE + templateId);
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.DELETE;
    }

    @Override
    public String getRequestBody() {
        return null;
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
    public URI getUri(URI baseUri) throws URISyntaxException {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<TemplateResponse> getResponseParser() {
        return response -> TemplatesObjectMapper.getInstance().readValue(response, TemplateResponse.class);
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
    }

    @Override
    public boolean canUseBearerTokenAuth() {
        return false;
    }
}
