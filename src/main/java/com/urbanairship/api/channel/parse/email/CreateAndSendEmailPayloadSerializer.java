package com.urbanairship.api.channel.parse.email;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.notification.email.CreateAndSendEmailPayload;

import java.io.IOException;

public class CreateAndSendEmailPayloadSerializer extends JsonSerializer<CreateAndSendEmailPayload> {
    @Override
    public void serialize(CreateAndSendEmailPayload payload, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        String[] deviceArray = {"email"};

            jgen.writeStartObject();
            jgen.writeObjectField("audience", payload.getAudience().get());
            jgen.writeObjectField("device_types", deviceArray);
            jgen.writeObjectField("notification", payload.getNotification().get());

            jgen.writeEndObject();

    }
}
