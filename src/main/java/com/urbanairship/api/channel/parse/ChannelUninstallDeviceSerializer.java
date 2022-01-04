package com.urbanairship.api.channel.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.channel.model.ChannelUninstallDevice;

import java.io.IOException;
import java.util.Set;

public class ChannelUninstallDeviceSerializer extends JsonSerializer<ChannelUninstallDevice> {
    @Override
    public void serialize(ChannelUninstallDevice ChannelUninstallDevice, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectField("channel_id", ChannelUninstallDevice.getChannelId());
        jgen.writeObjectField("device_type", ChannelUninstallDevice.getChannelType().getIdentifier());

        jgen.writeEndObject();
    }
}
