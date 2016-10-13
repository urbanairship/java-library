package com.urbanairship.api.push.parse.notification.ios;

import com.urbanairship.api.push.model.notification.ios.Content;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

/**
 * Created by devinsmythe on 10/13/16.
 */
public class ContentSerializer extends JsonSerializer<Content> {
    @Override
    public void serialize(Content content, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();

        if(content.getBody().isPresent()) {
            jgen.writeStringField("body", content.getBody().get());
        }

        if(content.getTitle().isPresent()) {
            jgen.writeStringField("title", content.getTitle().get());
        }

        if(content.getSubtitle().isPresent()) {
            jgen.writeStringField("subtitle", content.getSubtitle().get());
        }
    }
}