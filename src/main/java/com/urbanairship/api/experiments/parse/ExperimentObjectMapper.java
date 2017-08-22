/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.parse;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.urbanairship.api.experiments.model.Experiment;
import com.urbanairship.api.experiments.model.ExperimentResponse;
import com.urbanairship.api.experiments.model.VariantPushPayload;
import com.urbanairship.api.experiments.model.Variant;
import com.urbanairship.api.push.parse.*;

public class ExperimentObjectMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Experiment API Module", new Version(1, 0, 0, null));

    static {
        MODULE
                .addDeserializer(Experiment.class, new ExperimentDeserializer())
                .addSerializer(Experiment.class, new ExperimentSerializer())
                .addDeserializer(Variant.class, new VariantDeserializer())
                .addSerializer(Variant.class, new VariantSerializer())
                .addDeserializer(VariantPushPayload.class, new VariantPushPayloadDeserializer())
                .addSerializer(VariantPushPayload.class, new VariantPushPayloadSerializer())
                .addDeserializer(ExperimentResponse.class, new ExperimentResponseDeserializer());

        MAPPER.registerModule(MODULE);
        MAPPER.registerModule(PushObjectMapper.getModule());
        MAPPER.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public static SimpleModule getModule() {
        return MODULE;
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

    private ExperimentObjectMapper() {}

}
