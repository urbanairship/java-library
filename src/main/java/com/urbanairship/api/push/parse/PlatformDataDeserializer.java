/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;

import java.io.IOException;
import java.util.Set;

public class PlatformDataDeserializer extends JsonDeserializer<DeviceTypeData> {

    public static final PlatformDataDeserializer INSTANCE = new PlatformDataDeserializer();

    public PlatformDataDeserializer() { }

    @Override
    public DeviceTypeData deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        DeviceTypeData.Builder builder = DeviceTypeData.newBuilder();

        JsonToken token = parser.getCurrentToken();
        if (token == JsonToken.VALUE_STRING) {
            String value = parser.getText();
            if (!"all".equals(value)) {
                APIParsingException.raise(String.format("Invalid value \"%s\" for \"device_types\". Must be \"all\" or an array of platform identifiers.",
                                                        value),
                                          parser);
            } else {
                builder.setAll(true);
            }
        } else {
            Set<DeviceType> deviceTypes = parser.readValueAs(new TypeReference<Set<DeviceType>>() {});
            builder.addAllDeviceTypes(deviceTypes);
        }
        return builder.build();
    }
}
