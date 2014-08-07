package com.urbanairship.api.tag.parse;

import com.urbanairship.api.tag.model.AddRemoveDeviceFromTagPayload;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public final class AddRemoveDeviceFromTagPayloadSerializer extends JsonSerializer<AddRemoveDeviceFromTagPayload> {

    @Override
    public void serialize(AddRemoveDeviceFromTagPayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (payload.getIOSChannels().isPresent()) {
            jgen.writeObjectFieldStart("ios_tokens");

            if (payload.getIOSChannels().get().getAdd() != null) {
                jgen.writeArrayFieldStart("add");
                for (String s : payload.getIOSChannels().get().getAdd().get())
                {
                    jgen.writeString(s);
                }
                jgen.writeEndArray();

            }

            if (payload.getIOSChannels().get().getRemove() != null) {
                jgen.writeArrayFieldStart("remove");
                for (String s : payload.getIOSChannels().get().getRemove().get())
                {
                    jgen.writeString(s);
                }
                jgen.writeEndArray();
            }

            jgen.writeEndObject();
        }
        if (payload.getDeviceTokens().isPresent()) {
            jgen.writeObjectFieldStart("device_tokens");

            if (payload.getDeviceTokens().get().getAdd() != null) {
                jgen.writeArrayFieldStart("add");
                for (String s : payload.getDeviceTokens().get().getAdd().get())
                {
                    jgen.writeString(s);
                }
                jgen.writeEndArray();

            }

            if (payload.getDeviceTokens().get().getRemove() != null) {
                jgen.writeArrayFieldStart("remove");
                for (String s : payload.getDeviceTokens().get().getRemove().get())
                {
                    jgen.writeString(s);
                }
                jgen.writeEndArray();
            }

            jgen.writeEndObject();
        }
        if (payload.getDevicePins().isPresent()) {
            jgen.writeObjectFieldStart("device_pins");

            if (payload.getDevicePins().get().getAdd() != null) {
                jgen.writeArrayFieldStart("add");
                for (String s : payload.getDevicePins().get().getAdd().get())
                {
                    jgen.writeString(s);
                }
                jgen.writeEndArray();

            }

            if (payload.getDevicePins().get().getRemove() != null) {
                jgen.writeArrayFieldStart("remove");
                for (String s : payload.getDevicePins().get().getRemove().get())
                {
                    jgen.writeString(s);
                }
                jgen.writeEndArray();
            }

            jgen.writeEndObject();
        }
        if (payload.getApids().isPresent()) {
            jgen.writeObjectFieldStart("apids");

            if (payload.getApids().get().getAdd() != null) {
                jgen.writeArrayFieldStart("add");
                for (String s : payload.getApids().get().getAdd().get())
                {
                    jgen.writeString(s);
                }
                jgen.writeEndArray();

            }

            if (payload.getApids().get().getRemove() != null) {
                jgen.writeArrayFieldStart("remove");
                for (String s : payload.getApids().get().getRemove().get())
                {
                    jgen.writeString(s);
                }
                jgen.writeEndArray();
            }

            jgen.writeEndObject();
        }

        jgen.writeEndObject();
    }
}
