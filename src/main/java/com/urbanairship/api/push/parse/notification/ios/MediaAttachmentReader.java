package com.urbanairship.api.push.parse.notification.ios;


import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.notification.ios.MediaAttachment;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;

import java.io.IOException;

public class MediaAttachmentReader implements JsonObjectReader<MediaAttachment> {

    private MediaAttachment.Builder builder = MediaAttachment.newBuilder();
    private OptionsDeserializer optionsDS = new OptionsDeserializer();
    private ContentDeserializer contentDS = new ContentDeserializer();


    @Override
    public MediaAttachment validateAndBuild() throws IOException {
        try {
            return builder.build();
        }catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }

    public void readOptions(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setOptions(optionsDS.deserialize(parser, context));
    }

    public void readUrl(JsonParser parser) throws IOException {
        builder.setUrl(parser.getText());
    }

    public void readContent(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setContent(contentDS.deserialize(parser, context));
    }
}
