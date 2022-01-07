/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.DateTimeDeserializer;
import com.urbanairship.api.push.model.PushExpiry;

import java.io.IOException;

public class PushExpiryDeserializer extends JsonDeserializer<PushExpiry> {

    private static final DateTimeDeserializer DATE_TIME_DESERIALIZER = new DateTimeDeserializer();

    @Override
    public PushExpiry deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        try {
            JsonToken token = parser.getCurrentToken();
            if (token == JsonToken.VALUE_STRING && parser.getText().startsWith("{{") && parser.getText().endsWith("}}")) {
                String expiry = parser.getText();
                return PushExpiry.newBuilder()
                    .setExpiryPersonalization(expiry)
                    .build();                
            }

            else if (token == JsonToken.VALUE_STRING) {
                return PushExpiry.newBuilder()
                .setExpiryTimeStamp(DATE_TIME_DESERIALIZER.deserialize(parser, context))
                .build();

            }

            else if (token == JsonToken.VALUE_NUMBER_INT) {
                int expiry = parser.getIntValue();
                return PushExpiry.newBuilder()
                    .setExpirySeconds(expiry)
                    .build();
            }
            
            else {
                throw APIParsingException.raise(String.format("Unexpected token '%s' while parsing expiry time", token.name()), parser);
            }
    }
        catch ( APIParsingException e ) {
            throw e;
        } catch ( Exception e ) {
            throw APIParsingException.raise(e.getMessage(), parser);
        }
    }
}
