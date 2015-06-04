/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.actions;

import com.urbanairship.api.push.model.notification.actions.Action;
import com.urbanairship.api.push.model.notification.actions.ActionNameRegistry;
import com.urbanairship.api.push.model.notification.actions.Actions;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public final class ActionsSerializer extends JsonSerializer<Actions> {

    private final ActionNameRegistry registry;

    public ActionsSerializer(ActionNameRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void serialize(Actions actions, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        try {
            for(Action a : actions.allActions()) {
                // Notice we serialize the Action object, NOT it's value. This ensures
                // actions like Open External URL (which just have URI content) actually serialize
                // to an object with type and content fields.
                jsonGenerator.writeObjectField(registry.getFieldName(a.getActionType()), a);
            }
        }
        finally {
            jsonGenerator.writeEndObject();
        }
    }
}
