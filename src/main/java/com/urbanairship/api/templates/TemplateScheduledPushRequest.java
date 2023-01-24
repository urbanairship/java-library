package com.urbanairship.api.templates;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.schedule.model.ScheduleResponse;
import com.urbanairship.api.schedule.parse.ScheduleObjectMapper;
import com.urbanairship.api.templates.model.TemplateScheduledPushPayload;
import com.urbanairship.api.templates.parse.TemplatesObjectMapper;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateScheduledPushRequest implements Request<ScheduleResponse> {
    private final static String SCHEDULED_TEMPLATE_PUSH = "/api/templates/schedules/";
    private final static String TEMPLATE_PUSH_VALIDATE = SCHEDULED_TEMPLATE_PUSH + "validate/";

    private List<TemplateScheduledPushPayload> scheduledTemplatePushPayloads = new ArrayList<TemplateScheduledPushPayload>();
    private boolean validateOnly = false;

    private TemplateScheduledPushRequest() {}

    /**
     * Create a new TemplatePushRequest.
     *
     * @return TemplatePushRequest
     */
    public static TemplateScheduledPushRequest newRequest() {
        return new TemplateScheduledPushRequest();
    }

    /**
     * Add a template push payload to the request.
     *
     * @param templateScheduledPushPayload A TemplatePushPayload object
     * @return TemplatePushRequest
     */
    public TemplateScheduledPushRequest addTemplateScheduledPushPayload(TemplateScheduledPushPayload templateScheduledPushPayload) {
        this.scheduledTemplatePushPayloads.add(templateScheduledPushPayload);
        return this;
    }

    /**
     * Add all template push payloads to the request.
     *
     * @param scheduledTemplatePushPayload A list of TemplatePushPayload objects
     * @return TemplatePushRequest
     */
    public TemplateScheduledPushRequest addAllTemplatePushPayloads(List<TemplateScheduledPushPayload> scheduledTemplatePushPayload) {
        this.scheduledTemplatePushPayloads.addAll(scheduledTemplatePushPayload);
        return this;
    }

    /**
     * Set the template push to validate only.
     *
     * @param validateOnly A boolean
     * @return TemplatePushRequest
     */
    public TemplateScheduledPushRequest setValidateOnly(boolean validateOnly) {
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
            if (scheduledTemplatePushPayloads.size() == 1) {
                return TemplatesObjectMapper.getInstance().writeValueAsString(scheduledTemplatePushPayloads.get(0));
            }
            return TemplatesObjectMapper.getInstance().writeValueAsString(scheduledTemplatePushPayloads);
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
        String path = validateOnly ? TEMPLATE_PUSH_VALIDATE : SCHEDULED_TEMPLATE_PUSH;
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<ScheduleResponse> getResponseParser() {
        return response -> ScheduleObjectMapper.getInstance().readValue(response, ScheduleResponse.class);
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
