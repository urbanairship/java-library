/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.common.parse;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;

public class IntFieldDeserializer {

    public static final IntFieldDeserializer INSTANCE = new IntFieldDeserializer();

    private IntFieldDeserializer() { }

    public int deserialize(JsonParser parser, String fieldName) throws IOException {
        JsonToken token = parser.getCurrentToken();
        if (token != JsonToken.VALUE_NUMBER_INT) {
            throw new APIParsingException(String.format("\"%s\" field is expected to be an integer.", fieldName));
        }
       return parser.getValueAsInt();
    }
}
