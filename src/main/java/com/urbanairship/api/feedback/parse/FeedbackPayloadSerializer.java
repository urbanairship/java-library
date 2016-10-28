/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.feedback.parse;

import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.feedback.model.FeedbackPayload;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class FeedbackPayloadSerializer extends JsonSerializer<FeedbackPayload> {

    @Override
    public void serialize(FeedbackPayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeObjectField("since", DateFormats.DATE_FORMATTER.print(payload.getSince()));

        jgen.writeEndObject();
    }
}
