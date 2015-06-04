/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.urbanairship.api.push.model.DeviceType;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class DeviceTypeSerializer extends JsonSerializer<DeviceType> {

    @Override
    public void serialize(DeviceType deviceType, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(deviceType.getIdentifier());
    }
}
