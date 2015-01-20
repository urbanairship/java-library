/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.interactive;

import com.urbanairship.api.push.model.notification.interactive.Interactive;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.util.Map;

public class InteractiveSerializer extends JsonSerializer<Interactive> {

    @Override
    public void serialize(Interactive interactive, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("type", interactive.getType());

        if(interactive.getButtonActions().isPresent()) {
            jsonGenerator.writeObjectField("button_actions", interactive.getButtonActions().get());
        }

        jsonGenerator.writeEndObject();
    }
}
