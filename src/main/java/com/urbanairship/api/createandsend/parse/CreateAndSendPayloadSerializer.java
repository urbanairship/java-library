package com.urbanairship.api.createandsend.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.createandsend.model.notification.CreateAndSendPayload;

import java.io.IOException;

public class CreateAndSendPayloadSerializer extends JsonSerializer<CreateAndSendPayload> {
    @Override
    public void serialize(CreateAndSendPayload payload, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {

        jgen.writeStartObject();
        jgen.writeObjectField("audience", payload.getAudience().get());
        jgen.writeObjectField("device_types", payload.getNotification().get().getDeviceTypePayloadOverrides().keySet().toArray());
        jgen.writeObjectField("notification", payload.getNotification().get());
        jgen.writeObjectField("campaigns", payload.getCampaigns().get());

        jgen.writeEndObject();

    }
}
