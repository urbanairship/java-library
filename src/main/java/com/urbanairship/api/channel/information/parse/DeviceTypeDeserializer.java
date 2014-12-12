/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.information.parse;

import com.google.common.base.Optional;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.channel.information.model.DeviceType;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public final class DeviceTypeDeserializer extends JsonDeserializer<DeviceType> {

    @Override
    public DeviceType deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        String deviceTypeString = jp.getText();
        Optional<DeviceType> deviceTypeOpt = DeviceType.find(deviceTypeString);

        if (!deviceTypeOpt.isPresent()) {
            throw new APIParsingException("Unrecognized device type " + deviceTypeString);
        }

        return deviceTypeOpt.get();
    }
}