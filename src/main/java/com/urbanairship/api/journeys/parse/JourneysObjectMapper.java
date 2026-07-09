package com.urbanairship.api.journeys.parse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.urbanairship.api.common.parse.CommonObjectMapper;
import com.urbanairship.api.journeys.model.JourneyExitPayload;
import com.urbanairship.api.journeys.model.JourneyTriggerPayload;
import com.urbanairship.api.push.parse.PushObjectMapper;

public class JourneysObjectMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Journeys API Module");

    static {
        MODULE
                .addSerializer(JourneyTriggerPayload.class, new JourneyTriggerPayloadSerializer())
                .addSerializer(JourneyExitPayload.class, new JourneyExitPayloadSerializer());

        MAPPER.registerModule(MODULE);
        MAPPER.registerModule(PushObjectMapper.getModule());
        MAPPER.registerModule(CommonObjectMapper.getModule());
        MAPPER.registerModule(new Jdk8Module());
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

    private JourneysObjectMapper() {}
}
