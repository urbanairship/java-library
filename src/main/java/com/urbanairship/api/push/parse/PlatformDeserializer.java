/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.DeviceType;

import java.io.IOException;
import java.util.Optional;

public class PlatformDeserializer extends JsonDeserializer<DeviceType> {

    @Override
    public DeviceType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String deviceTypeString = jp.getText();
        Optional<DeviceType> platform = DeviceType.find(deviceTypeString);
        if (!platform.isPresent()) {
            APIParsingException.raise(String.format("Unrecognized device type '%s'", deviceTypeString), jp);
        }
        return platform.get();
    }
}
