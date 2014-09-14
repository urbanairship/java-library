package com.urbanairship.api.push.parse.notification.actions;

import com.urbanairship.api.push.model.notification.actions.Open;
import com.urbanairship.api.push.model.notification.actions.OpenType;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

/**
 * OpenSerializer
 *
 * @author <a href="mailto:n.sowen@2scale.net">Nils Sowen</a>
 */
public class OpenSerializer extends JsonSerializer<Open> {

    @Override
    public void serialize(Open open, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("type", open.getType().get().getIdentifier());


        if (open.getType().get() == OpenType.LANDING_PAGE) {
            jsonGenerator.writeObjectField("content", open.getContentData().get().getContentObject().get());
        } else {
            if (open.getContentData().get().getContent().isPresent())
                jsonGenerator.writeObjectField("content", open.getContentData().get().getContent().get());
        }

        jsonGenerator.writeEndObject();
    }


}
