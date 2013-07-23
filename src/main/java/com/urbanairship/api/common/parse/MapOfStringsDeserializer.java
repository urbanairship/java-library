/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.common.parse;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.Map;

public class MapOfStringsDeserializer {

    public static final MapOfStringsDeserializer INSTANCE = new MapOfStringsDeserializer();

    private MapOfStringsDeserializer() { }

    public Map<String, String> deserialize(JsonParser parser, String fieldName) {
        try {
            return parser.readValueAs(new TypeReference<Map<String, String>>() {});
        }
        catch (IOException e){
            throw new APIParsingException(
                    String.format(
                            "Error %s\nin deserializer MapOfStrings at field name %s",
                            e, fieldName));
        }
    }
}
