package com.urbanairship.api.channel.parse.createandsend;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.createandsend.model.notification.CreateAndSendPayload;

import java.io.IOException;

public class CreateAndSendEmailPayloadSerializer extends JsonSerializer<CreateAndSendPayload> {
    @Override
    public void serialize(CreateAndSendPayload payload, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        String[] deviceArray = {"email"};

            jgen.writeStartObject();
            jgen.writeObjectField("audience", payload.getAudience().get());
            jgen.writeObjectField("device_types", deviceArray);
            jgen.writeObjectField("notification", payload.getNotification().get());
            jgen.writeObjectField("campaigns", payload.getCampaigns().get());

            jgen.writeEndObject();

    }
}
