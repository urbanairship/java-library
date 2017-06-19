/*
 * Copyright (c) 2013-2017.  Urban Airship and Contributors
 */

package com.urbanairship.api.experiments.parse;

import com.urbanairship.api.experiments.model.Experiment;
import com.urbanairship.api.experiments.model.ExperimentResponse;
import com.urbanairship.api.experiments.model.PartialPushPayload;
import com.urbanairship.api.experiments.model.Variant;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.parse.DeviceTypeDataSerializer;
import com.urbanairship.api.push.parse.DeviceTypeSerializer;
import com.urbanairship.api.push.parse.PlatformDataDeserializer;
import com.urbanairship.api.push.parse.PlatformDeserializer;
import com.urbanairship.api.push.parse.audience.SelectorDeserializer;
import com.urbanairship.api.push.parse.audience.SelectorSerializer;
import com.urbanairship.api.push.parse.notification.NotificationDeserializer;
import com.urbanairship.api.push.parse.notification.NotificationSerializer;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

public class ExperimentObjectMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule("Experiment API Module", new Version(1, 0, 0, null));

    static {
        MODULE
                .addDeserializer(Experiment.class, new ExperimentDeserializer())
                .addSerializer(Experiment.class, new ExperimentSerializer())
                .addDeserializer(Variant.class, new VariantDeserializer())
                .addSerializer(Variant.class, new VariantSerializer())
                .addDeserializer(PartialPushPayload.class, new PartialPushPayloadDeserializer())
                .addSerializer(Notification.class, new NotificationSerializer())
                .addSerializer(Selector.class, new SelectorSerializer())
                .addDeserializer(Selector.class, new SelectorDeserializer())
                .addSerializer(DeviceTypeData.class, new DeviceTypeDataSerializer())
                .addDeserializer(DeviceTypeData.class, new PlatformDataDeserializer())
                .addSerializer(DeviceType.class, new DeviceTypeSerializer())
                .addDeserializer(DeviceType.class, new PlatformDeserializer())
                .addDeserializer(ExperimentResponse.class, new ExperimentResponseDeserializer())
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
