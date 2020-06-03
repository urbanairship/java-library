/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.common.parse;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.Map;



public class MapOfObjectsDeserializer {

    public static final MapOfObjectsDeserializer INSTANCE = new MapOfObjectsDeserializer();

    private MapOfObjectsDeserializer() { }

    public Map<String, Object> deserialize(JsonParser parser, String fieldName) throws APIParsingException {
        try {
            return parser.readValueAs(new TypeReference<Map<String, Object>>() {});
        }
        catch (IOException e) {
            APIParsingException.raise(String.format("%s was not valid", fieldName), parser);
        }
        // Satisfy the java compiler - it can't figure out that
        // APIParsingException.raise() always throws.
        return null;
    }
}
