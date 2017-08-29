/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.common.parse;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;

public class BooleanFieldDeserializer {

    public static final BooleanFieldDeserializer INSTANCE = new BooleanFieldDeserializer();

    private BooleanFieldDeserializer() { }

    public boolean deserialize(JsonParser parser, String fieldName) throws IOException {
        JsonToken token = parser.getCurrentToken();
        if ((token != JsonToken.VALUE_TRUE) && (token != JsonToken.VALUE_FALSE)) {
            throw new APIParsingException(String.format("\"%s\" field is expected to be a boolean value (true/false).", fieldName));
        }
        return parser.getValueAsBoolean();
    }
}
