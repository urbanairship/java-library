/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.templates;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.templates.model.PartialPushPayload;
import com.urbanairship.api.templates.model.TemplateResponse;
import com.urbanairship.api.templates.model.TemplateVariable;
import com.urbanairship.api.templates.model.TemplateView;
import com.urbanairship.api.templates.parse.TemplatesObjectMapper;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The TemplateRequest class builds template creation and update requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class TemplateRequest implements Request<TemplateResponse> {

    private static final String API_POST_TEMPLATE = "/api/templates/";

    private final TemplateView.Builder builder = TemplateView.newBuilder();
    private final String path;

    private TemplateRequest(String path) {
        this.path = path;
    }

    /**
     * Return a new template creation request.
     *
     * @return TemplateRequest
     */
    public static TemplateRequest newRequest() {
        return new TemplateRequest(API_POST_TEMPLATE);
    }

    /**
     * Return a new template update request.
     *
     * @param templateId The template ID to update
     * @return TemplateRequest
     */
    public static TemplateRequest newRequest(String templateId) {
        return new TemplateRequest(API_POST_TEMPLATE + templateId);
    }

    /**
     * Set the template name.
     *
     * @param name A string.
     * @return TemplateRequest
     */
    public TemplateRequest setName(String name) {
        this.builder.setName(name);
        return this;
    }

    /**
     * Set the template description.
     *
     * @param description A string
     * @return TemplateRequest
     */
    public TemplateRequest setDescription(String description) {
        this.builder.setDescription(description);
        return this;
    }

    /**
     * Add a variable to the template.
     *
     * @param variable A TemplateVariable object
     * @return TemplateRequest
     */
    public TemplateRequest addVariable(TemplateVariable variable) {
        this.builder.addVariable(variable);
        return this;
    }

    /**
     * Add a list of variables to the template.
     *
     * @param variables A list of template variable objects.
     * @return TemplateRequest
     */
    public TemplateRequest addAllVariables(List<TemplateVariable> variables) {
        this.builder.addAllVariables(variables);
        return this;
    }

    /**
     * Set the template partial push payload.
     *
     * @param push A PartialPushPayload object
     * @return TemplateRequest
     */
    public TemplateRequest setPush(PartialPushPayload push) {
        this.builder.setPushPayload(push);
        return this;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        ObjectMapper mapper = TemplatesObjectMapper.getInstance().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsString(this.builder.build());
        } catch (Exception ex) {
            return "{ \"exception\" : \"" + ex.getClass().getName() + "\", \"message\" : \"" + ex.getMessage() + "\" }";
        }
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
    public URI getUri(URI baseUri) throws URISyntaxException {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<TemplateResponse> getResponseParser() {
        return new ResponseParser<TemplateResponse>() {
            @Override
            public TemplateResponse parse(String response) throws IOException {
                return TemplatesObjectMapper.getInstance().readValue(response, TemplateResponse.class);
            }
        };
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
    }
}
