package com.urbanairship.api.push.parse;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;
import com.urbanairship.api.push.model.Platform;
import com.urbanairship.api.push.model.PushPayload;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class PushPayloadSerializer extends JsonSerializer<PushPayload> {

    @Override
    public void serialize(PushPayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectField("audience", payload.getAudience());
        if (payload.getPlatforms().isAll()) {
            jgen.writeStringField("device_types", "all");
        } else {
            jgen.writeObjectField("device_types", payload.getPlatforms().getPlatforms().get());
        }
        if (payload.getNotification().isPresent()) {
            jgen.writeObjectField("notification", payload.getNotification().get());
        }
        if (payload.getMessage().isPresent()) {
            jgen.writeObjectField("message", payload.getMessage().get());
        }

        jgen.writeEndObject();
    }
}
