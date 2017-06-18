/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import com.urbanairship.api.experiments.model.Experiment;
import com.urbanairship.api.experiments.model.ExperimentResponse;
import com.urbanairship.api.experiments.model.Variant;
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

/**
 * The ExperimentRequest class builds experiment requests to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class ExperimentRequest implements Request<ExperimentResponse> {

    private final static String EXPERIMENT_PATH = "/api/experiments/";
    private final static String EXPERIMENT_VALIDATE_PATH = "/api/experiments/validate/";

    private final Experiment experiment;
    private boolean validateOnly;

    private ExperimentRequest(Experiment experiment) {
        Preconditions.checkNotNull(experiment, "Experiment payload required when creating a push request");
        this.experiment = experiment;
    }

    /**
     * Create an experiment request.
     *
     * @return ExperimentRequest
     */
    public static ExperimentRequest newRequest(Experiment experiment) {
        return new ExperimentRequest(experiment);
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
        return experiment.toJSON();
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


