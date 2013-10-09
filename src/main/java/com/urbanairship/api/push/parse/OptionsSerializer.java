/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse;

import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.push.model.Options;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class OptionsSerializer extends JsonSerializer<Options> {

    public static final OptionsSerializer INSTANCE = new OptionsSerializer();

    private OptionsSerializer() {
    }

    @Override
    public void serialize(Options value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        // At most one of expiry or expirySeconds will be set. See Options.Builder.

        if (value.getExpiry() != null) {
            jgen.writeStringField("expiry", DateFormats.DATE_FORMATTER.print(value.getExpiry()));
        }

        if (value.getExpirySeconds() != null) {
            jgen.writeNumberField("expiry", value.getExpirySeconds());
        }

        jgen.writeEndObject();
    }
}
