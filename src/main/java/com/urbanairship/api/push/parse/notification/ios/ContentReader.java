package com.urbanairship.api.push.parse.notification.ios;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.notification.ios.Content;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;

import java.io.IOException;

public class ContentReader implements JsonObjectReader<Content> {

    private Content.Builder builder = Content.newBuilder();

    public Content validateAndBuild() throws IOException {
        try {
            return builder.build();
        }catch (Exception e){
            throw new APIParsingException(e.getMessage(), e);
        }
    }

    public void readTitle(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setTitle(parser.getText());
    }

    public void readSubtitle(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setSubtitle(parser.getText());
    }

    public void readBody(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setBody(parser.getText());
    }
}
