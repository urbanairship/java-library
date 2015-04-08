package com.urbanairship.api.push.parse.notification;

import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.Interactive;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class InteractiveSerializer extends JsonSerializer<Interactive> {
    @Override
    public void serialize(Interactive value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("type", value.getType());
        if (value.getButtonActions().size() > 0) {
            jgen.writeObjectFieldStart("button_actions");
            for (String buttonID : value.getButtonActions().keySet()) {
                Actions actions = value.getButtonActions().get(buttonID);
                jgen.writeObjectField(buttonID, actions);
            }
            jgen.writeEndObject();
        }
        jgen.writeEndObject();
    }
}
