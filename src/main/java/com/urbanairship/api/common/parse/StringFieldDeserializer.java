package com.urbanairship.api.common.parse;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import java.io.IOException;

public class StringFieldDeserializer {

    public static final StringFieldDeserializer INSTANCE = new StringFieldDeserializer();

    private StringFieldDeserializer() { }

    public String deserialize(JsonParser parser, String fieldName) throws IOException {
        JsonToken token = parser.getCurrentToken();
        if (token != JsonToken.VALUE_STRING) {
            APIParsingException.raise(String.format("\"%s\" field is expected to be a string", fieldName), parser);
        }

        return parser.getText();
    }
}
