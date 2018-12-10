/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.DeviceTypeData;

import java.io.IOException;

public class DeviceTypeDataSerializer extends JsonSerializer<DeviceTypeData> {

    @Override
    public void serialize(DeviceTypeData payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeObject(payload.getDeviceTypes().get());
    }
}
