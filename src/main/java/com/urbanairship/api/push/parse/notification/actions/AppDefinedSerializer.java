package com.urbanairship.api.push.parse.notification.actions;

import com.urbanairship.api.push.model.notification.actions.AppDefinedAction;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class AppDefinedSerializer extends JsonSerializer<AppDefinedAction> {
    @Override
    public void serialize(AppDefinedAction value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeTree(value.getValue());
    }
}