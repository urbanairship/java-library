/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.feedback.parse;

import com.urbanairship.api.feedback.model.ApidsFeedbackResponse;
import com.urbanairship.api.feedback.model.DeviceTokensFeedbackResponse;
import com.urbanairship.api.feedback.model.FeedbackPayload;
import com.urbanairship.api.push.parse.PushObjectMapper;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

public class FeedbackObjectMapper {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Feedback API Module", new Version(1, 0, 0, null));

    static {
    MODULE.addDeserializer(ApidsFeedbackResponse.class, new ApidsFeedbackResponseDeserializer());
    MODULE.addDeserializer(DeviceTokensFeedbackResponse.class, new DeviceTokensFeedbackResponseDeserializer());
    MODULE.addSerializer(FeedbackPayload.class, new FeedbackPayloadSerializer());

    MAPPER.registerModule(MODULE);
    MAPPER.registerModule(PushObjectMapper.getModule());
    MAPPER.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
}

    public static SimpleModule getModule() {
        return MODULE;
    }

    public static ObjectMapper getInstance() {
        return MAPPER;
    }

    private FeedbackObjectMapper() {}
}
