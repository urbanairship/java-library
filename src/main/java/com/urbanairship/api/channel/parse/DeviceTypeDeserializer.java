/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.parse;

import com.google.common.base.Optional;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.common.parse.APIParsingException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public final class DeviceTypeDeserializer extends JsonDeserializer<ChannelType> {

    @Override
    public ChannelType deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        String deviceTypeString = jp.getText();
        Optional<ChannelType> deviceTypeOpt = ChannelType.find(deviceTypeString);

        if (!deviceTypeOpt.isPresent()) {
            throw new APIParsingException("Unrecognized device type " + deviceTypeString);
        }

        return deviceTypeOpt.get();
    }
}
