/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.urbanairship.api.push.model.DeviceTypeData;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class DeviceTypeDataSerializer extends JsonSerializer<DeviceTypeData> {

    @Override
    public void serialize(DeviceTypeData payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (payload.isAll()) {
            jgen.writeString("all");
        } else {
            jgen.writeObject(payload.getDeviceTypes().get());
        }
    }
}
