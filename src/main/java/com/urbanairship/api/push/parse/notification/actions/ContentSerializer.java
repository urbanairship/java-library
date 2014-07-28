package com.urbanairship.api.push.parse.notification.actions;

import com.urbanairship.api.push.model.notification.actions.Content;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

/**
 * ContentSerializer
 * @author <a href="mailto:n.sowen@2scale.net">Nils Sowen</a>
 */
public class ContentSerializer extends JsonSerializer<Content> {
    @Override
    public void serialize(Content content, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("body", content.getBody());
        jsonGenerator.writeStringField("content_type", content.getContentType());
        jsonGenerator.writeStringField("content_encoding", content.getContentEncoding());
        jsonGenerator.writeEndObject();
    }

}
