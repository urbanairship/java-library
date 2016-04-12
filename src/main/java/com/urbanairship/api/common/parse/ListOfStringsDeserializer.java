/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.common.parse;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

public class ListOfStringsDeserializer {

    public static final ListOfStringsDeserializer INSTANCE = new ListOfStringsDeserializer();

    private ListOfStringsDeserializer() { }

    public List<String> deserialize(JsonParser parser, String fieldName) {
        try {
            return parser.readValueAs(new TypeReference<List<String>>() {});
        }
        catch (IOException e) {
            throw new APIParsingException(String.format("%s must be an array of simple strings", fieldName));
        }
    }
}
