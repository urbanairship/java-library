/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.templates;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.templates.model.TemplatePushPayload;
import com.urbanairship.api.templates.model.TemplateResponse;
import com.urbanairship.api.templates.parse.TemplatesObjectMapper;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The TemplatePushRequest object builds a template push request to be executed in the
 *  {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class TemplatePushRequest implements Request<TemplateResponse> {
    private final static String TEMPLATE_PUSH = "/api/templates/push/";
    private final static String TEMPLATE_PUSH_VALIDATE = TEMPLATE_PUSH + "validate/";

    private List<TemplatePushPayload> templatePushPayloads = new ArrayList<TemplatePushPayload>();
    private boolean validateOnly = false;

    private TemplatePushRequest() {}

    /**
     * Create a new TemplatePushRequest.
     *
     * @return TemplatePushRequest
     */
    public static TemplatePushRequest newRequest() {
        return new TemplatePushRequest();
    }

    /**
     * Add a template push payload to the request.
     *
     * @param templatePushPayload A TemplatePushPayload object
     * @return TemplatePushRequest
     */
    public TemplatePushRequest addTemplatePushPayload(TemplatePushPayload templatePushPayload) {
        this.templatePushPayloads.add(templatePushPayload);
        return this;
    }

    /**
     * Add all template push payloads to the request.
     *
     * @param templatePushPayloads A list of TemplatePushPayload objects
     * @return TemplatePushRequest
     */
    public TemplatePushRequest addAllTemplatePushPayloads(List<TemplatePushPayload> templatePushPayloads) {
        this.templatePushPayloads.addAll(templatePushPayloads);
        return this;
    }

    /**
     * Set the template push to validate only.
     *
     * @param validateOnly A boolean
     * @return TemplatePushRequest
     */
    public TemplatePushRequest setValidateOnly(boolean validateOnly) {
        this.validateOnly = validateOnly;
        return this;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        try {
            if (templatePushPayloads.size() == 1) {
                return TemplatesObjectMapper.getInstance().writeValueAsString(templatePushPayloads.get(0));
            }
            return TemplatesObjectMapper.getInstance().writeValueAsString(templatePushPayloads);
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
        String path = validateOnly ? TEMPLATE_PUSH_VALIDATE : TEMPLATE_PUSH;
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
