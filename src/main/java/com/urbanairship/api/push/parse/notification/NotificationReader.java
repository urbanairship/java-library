/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.actions.Actions;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.util.Map;

public class NotificationReader implements JsonObjectReader<Notification> {

    private final Map<DeviceType, JsonDeserializer<? extends DevicePayloadOverride>> payloadOverridesDeserializerRegistry;
    private final Map<DeviceType, DevicePayloadOverride> payloadOverrides = Maps.newHashMap();

    private String alert = null;
    private Optional<Actions> optActions = Optional.absent();
    private Optional<Interactive> interactive = Optional.absent();

    public NotificationReader(Map<DeviceType, JsonDeserializer<? extends DevicePayloadOverride>> payloadOverridesDeserializerRegistry) {
        this.payloadOverridesDeserializerRegistry = payloadOverridesDeserializerRegistry;
    }

    public void readAlert(JsonParser parser) throws IOException {
        alert = StringFieldDeserializer.INSTANCE.deserialize(parser, "alert");
    }

    public void readPlatformDevicePayloadOverride(DeviceType deviceType, JsonParser parser, DeserializationContext context) throws IOException {
        JsonDeserializer<? extends DevicePayloadOverride> deserializer = payloadOverridesDeserializerRegistry.get(deviceType);
        if (deserializer == null) {
            APIParsingException.raise("Unsupported platform " + deviceType.getIdentifier(), parser);
        }

        payloadOverrides.put(deviceType, deserializer.deserialize(parser, context));
    }

    public void readActions(JsonParser parser) throws IOException {
        optActions = Optional.of(parser.readValueAs(Actions.class));
    }

    public void readInteractive(JsonParser parser) throws IOException {
        interactive = Optional.of(parser.readValueAs(Interactive.class));
    }

    @Override
    public Notification validateAndBuild() throws IOException {
        if (alert == null && payloadOverrides.isEmpty()) {
            throw new APIParsingException("\"notification\" element must contain at least a default alert or a platform override element");
        }

        Notification.Builder builder = Notification.newBuilder();
        if (alert != null) {
            builder.setAlert(alert);
        }

        for (Map.Entry<DeviceType, DevicePayloadOverride> overrideEntry : payloadOverrides.entrySet()) {
            builder.addDeviceTypeOverride(overrideEntry.getKey(), overrideEntry.getValue());
        }

        if (optActions.isPresent()) {
            builder.setActions(optActions.get());
        }

        if (interactive.isPresent()) {
            builder.setInteractive(interactive.get());
        }

        try {
            return builder.build();
        }
        catch (Exception e) {
            throw new IOException(e);
        }
    }
}
