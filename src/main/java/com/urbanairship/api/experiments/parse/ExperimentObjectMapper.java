/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.parse;

import com.urbanairship.api.experiments.model.Experiment;
import com.urbanairship.api.experiments.model.PartialPushPayload;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

public class ExperimentObjectMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Experiment API Module", new Version(1, 0, 0, null));

    static {
        MODULE
                .addDeserializer(Experiment.class, ExperimentDeserializer.INSTANCE)
                .addSerializer(Experiment.class, ExperimentSerializer.INSTANCE)
                .addDeserializer(Experiment.Variant.class, new VariantDeserializer())
                .addSerializer(Experiment.Variant.class, new VariantSerializer());
                .addDeserializer(PartialPushPayload.class, new PartialPushPayloadDeserializer);
                .addSerializer(PartialPushPayload.class, new PartialPushPayloadSerializer());

        MAPPER.registerModule(MODULE);
        MAPPER.registerModule(ExperimentObjectMapper.getModule());
        MAPPER.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public static SimpleModule getModule() {
        return MODULE;
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

    private ExperimentObjectMapper() {}

}
