/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Optional;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.DeviceType;

import java.io.IOException;

public class PlatformDeserializer extends JsonDeserializer<DeviceType> {

    @Override
    public DeviceType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String deviceTypeString = jp.getText();
        Optional<DeviceType> platform = DeviceType.fromIdentifierFunction.apply(deviceTypeString);
        if (!platform.isPresent()) {
            APIParsingException.raise(String.format("Unrecognized device type '%s'",deviceTypeString), jp);
        }

        return platform.get();
    }
}
