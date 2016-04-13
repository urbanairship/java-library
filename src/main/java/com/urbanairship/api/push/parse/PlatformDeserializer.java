/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.google.common.base.Optional;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.DeviceType;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

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
