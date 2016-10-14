/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.parse.notification.ios;


import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.notification.ios.Content;
import com.urbanairship.api.push.model.notification.ios.MediaAttachment;
import com.urbanairship.api.push.model.notification.ios.Options;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;

import java.io.IOException;

public class MediaAttachmentReader implements JsonObjectReader<MediaAttachment> {

    private final MediaAttachment.Builder builder;

    public MediaAttachmentReader() {
        this.builder = MediaAttachment.newBuilder();
    }

    @Override
    public MediaAttachment validateAndBuild() throws IOException {
        try {
            return builder.build();
        }catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }

    public void readOptions(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setOptions(parser.readValueAs(Options.class));
    }

    public void readUrl(JsonParser parser) throws IOException {
        builder.setUrl(parser.getText());
    }

    public void readContent(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setContent(parser.readValueAs(Content.class));
    }
}
