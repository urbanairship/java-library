/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.type.TypeReference;

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
