/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.PushNoThrottle;

import java.io.IOException;

public class PushNoThrottleDeserializer extends JsonDeserializer<PushNoThrottle> {


    @Override
    public PushNoThrottle deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        try {
            JsonToken token = parser.getCurrentToken();
            switch (token) {

                case VALUE_FALSE:
                    return PushNoThrottle.newBuilder()
                        .setValue(Boolean.valueOf(false))
                        .build();

                case VALUE_TRUE:
                    return PushNoThrottle.newBuilder()
                        .setValue(Boolean.valueOf(true))
                        .build();

                default:
                    throw APIParsingException.raise(String.format("Unexpected token '%s' while parsing no_throttle", token.name()), parser);
            }
        }
        catch ( APIParsingException e ) {
            throw e;
        } catch ( Exception e ) {
            throw APIParsingException.raise(e.getMessage(), parser);
        }
    }
}
