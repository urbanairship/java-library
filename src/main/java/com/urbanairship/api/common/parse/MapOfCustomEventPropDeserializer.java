/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.common.parse;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.customevents.model.CustomEventPropValue;

import java.io.IOException;
import java.util.Map;

public class MapOfCustomEventPropDeserializer {

    public static final MapOfCustomEventPropDeserializer INSTANCE = new MapOfCustomEventPropDeserializer();

    private MapOfCustomEventPropDeserializer() { }

    public Map<String, CustomEventPropValue> deserialize(JsonParser parser, String fieldName) throws APIParsingException {
        try {
            return parser.readValueAs(new TypeReference<Map<String, CustomEventPropValue>>() {});
        }
        catch (IOException e) {
            APIParsingException.raise(String.format("%s must contain simple strings as keys/values", fieldName), parser);
        }
        // Satisfy the java compiler - it can't figure out that
        // APIParsingException.raise() always throws.
        return null;
    }
}
