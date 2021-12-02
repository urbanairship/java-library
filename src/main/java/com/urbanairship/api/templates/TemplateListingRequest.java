/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.templates;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.templates.model.TemplateListingResponse;
import com.urbanairship.api.templates.parse.TemplatesObjectMapper;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * The TemplateListingRequest class builds template listing requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class TemplateListingRequest implements Request<TemplateListingResponse> {
    private final static String API_TEMPLATES_GET = "/api/templates/";
    private final String path;
    private Integer page;
    private Integer pageSize;

    private TemplateListingRequest(String path) {
        this.path = path;
    }

    /**
     * Create a new TemplateListingRequest object for a template listing request.
     *
     * @return TemplateListingRequest
     */
    public static TemplateListingRequest newRequest() {
        return new TemplateListingRequest(API_TEMPLATES_GET);
    }

    /**
     * Create a new TemplateListingRequest object for a template lookup request.
     *
     * @param templateId The template ID to lookup
     * @return TemplateListingRequest
     */
    public static TemplateListingRequest newRequest(String templateId) {
        return new TemplateListingRequest(API_TEMPLATES_GET + templateId);
    }

    /**
     * Set the page URL parameter.
     *
     * @param page An int
     * @return TemplateListingRequest
     */
    public TemplateListingRequest setPage(int page) {
        this.page = page;
        return this;
    }

    /**
     * Set the page_size URL parameter.
     *
     * @param pageSize An int
     * @return TemplateListingRequest
     */
    public TemplateListingRequest setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
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
        URI uri;
        URIBuilder builder = new URIBuilder(RequestUtils.resolveURI(baseUri, path));

        if (page != null) {
            builder.addParameter("page", page.toString());
        }
        if (pageSize != null) {
            builder.addParameter("page_size", pageSize.toString());
        }

        try {
            uri = builder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return uri;
    }

    @Override
    public ResponseParser<TemplateListingResponse> getResponseParser() {
        return new ResponseParser<TemplateListingResponse>() {
            @Override
            public TemplateListingResponse parse(String response) throws IOException {
                return TemplatesObjectMapper.getInstance().readValue(response, TemplateListingResponse.class);
            }
        };
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
