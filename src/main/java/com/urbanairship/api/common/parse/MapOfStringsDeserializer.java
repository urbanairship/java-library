/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.common.parse;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.Map;

public class MapOfStringsDeserializer {

    public static final MapOfStringsDeserializer INSTANCE = new MapOfStringsDeserializer();

    private MapOfStringsDeserializer() { }

    public Map<String, String> deserialize(JsonParser parser, String fieldName) throws APIParsingException {
        try {
            return parser.readValueAs(new TypeReference<Map<String, String>>() {});
        }
        catch (IOException e) {
            APIParsingException.raise(String.format("%s must contain simple strings as keys/values", fieldName), parser);
        }
        // Satisfy the java compiler - it can't figure out that
        // APIParsingException.raise() always throws.
        return null;
    }
}
