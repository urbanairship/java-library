/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.experiments.model.Experiment;
import com.urbanairship.api.experiments.model.ExperimentResponse;
import com.urbanairship.api.experiments.parse.ExperimentObjectMapper;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.Selector;
import org.apache.http.entity.ContentType;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExperimentRequest implements Request<ExperimentResponse> {

    private final static String EXPERIMENT_PATH = "/api/experiments/";
    private final static String EXPERIMENT_VALIDATE_PATH = "/api/experiments/validate/";

    private final Experiment.Builder builder = Experiment.newBuilder();
    private final String path;
    private boolean validateOnly;

    private ExperimentRequest(String path) {
        this.path = path;
    }

    /**
     * Create an experiment request.
     *
     * @return ExperimentRequest
     */
    public static ExperimentRequest newRequest() {
        return new ExperimentRequest(EXPERIMENT_PATH);
    }

    /**
     * Sets if the request should only validate the payload.
     *
     * @param validateOnly {@code true} to only validate the experiment, {@code false} to send the experiment request.
     * @return The experiment request.
     */
    public ExperimentRequest setValidateOnly(boolean validateOnly) {
        this.validateOnly = validateOnly;
        return this;
    }

    /**
     * Set the experiment name.
     *
     * @param name A string.
     * @return ExperimentRequest
     */
    public ExperimentRequest setName(String name) {
        this.builder.setName(name);
        return this;
    }

    /**
     * Set the experiment description.
     *
     * @param description A string
     * @return ExperimentRequest
     */
    public ExperimentRequest setDescription(String description) {
        this.builder.setDescription(description);
        return this;
    }

    /**
     * Set the experiment control group.
     *
     * @param control A control group for the experiment.
     * @return TemplateRequest
     */
    public ExperimentRequest setControl(BigDecimal control) {
        this.builder.setControl(control);
        return this;
    }

    /**
     * Set the audience for the experiment.
     *
     * @param audience The target audience for the experiment.
     * @return ExperimentRequest
     */
    public ExperimentRequest setAudience(Selector audience) {
        this.builder.setAudience(audience);
        return this;
    }

    /**
     * Set the device types for the experiment.
     *
     * @param deviceTypes The target audience for the experiment.
     * @return ExperimentRequest
     */
    public ExperimentRequest setDeviceType(DeviceTypeData deviceTypes) {
        this.builder.setDeviceType(deviceTypes);
        return this;
    }

    /**
     * Add a variant to the experiment.
     *
     * @param variant Variant object.
     * @return ExperimentRequest
     */
    public ExperimentRequest addVariant(Experiment.Variant variant) {
        this.builder.addVariant(variant);
        return this;
    }

    /**
     * Add a list of variants to the template.
     *
     * @param variants A list of variant objects.
     * @return ExperimentRequest
     */
    public ExperimentRequest addAllVariants(List<Experiment.Variant> variants) {
        this.builder.addAllVariants(variants);
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
    public Request.HttpMethod getHttpMethod() {
        return Request.HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        ObjectMapper mapper = ExperimentObjectMapper.getInstance().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        try {
            return mapper.writeValueAsString(this.builder.build());
        } catch (Exception ex) {
            return "{ \"exception\" : \"" + ex.getClass().getName() + "\", \"message\" : \"" + ex.getMessage() + "\" }";
        }
    }

    @Override
    public URI getUri(URI baseUri) {
        String path  = validateOnly ? EXPERIMENT_VALIDATE_PATH : EXPERIMENT_PATH;
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<ExperimentResponse> getResponseParser() {
        return new ResponseParser<ExperimentResponse>() {
            @Override
            public ExperimentResponse parse(String response) throws IOException {
                return ExperimentObjectMapper.getInstance().readValue(response, ExperimentResponse.class);
            }
        };
    }
}


