/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;
import com.urbanairship.api.push.model.notification.Notification;

import java.io.IOException;
import java.util.Map;

public class NotificationSerializer extends JsonSerializer<Notification> {

    @Override
    public void serialize(Notification notification, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();

        if (notification.getAlert().isPresent()) {
            jsonGenerator.writeStringField("alert", notification.getAlert().get());
        }

        for (Map.Entry<DeviceType, DevicePayloadOverride> entry : notification.getDeviceTypePayloadOverrides().entrySet()) {
            jsonGenerator.writeObjectField(entry.getKey().getIdentifier(), entry.getValue());
        }

        if (notification.getActions().isPresent()) {
            jsonGenerator.writeObjectField("actions", notification.getActions().get());
        }

        if (notification.getInteractive().isPresent()) {
            jsonGenerator.writeObjectField("interactive", notification.getInteractive().get());
        }

        jsonGenerator.writeEndObject();
    }
}
