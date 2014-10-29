/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.actions;

import com.urbanairship.api.push.model.notification.actions.OpenExternalURLAction;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public final class ExternalURLSerializer extends JsonSerializer<OpenExternalURLAction> {
    @Override
    public void serialize(OpenExternalURLAction value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        try {
            jgen.writeObjectField("type", "url");
            jgen.writeObjectField("content", value.getValue().toString());
        }
        finally {
            jgen.writeEndObject();
        }
    }
}