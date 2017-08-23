/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.parse.notification.ios;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.ios.MediaAttachment;

import java.io.IOException;

public class MediaAttachmentSerializer extends JsonSerializer<MediaAttachment> {
    @Override
    public void serialize(MediaAttachment mediaAttachment, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField("url", mediaAttachment.getUrl());

        if(mediaAttachment.getOptions().isPresent()) {
            jgen.writeObjectField("options", mediaAttachment.getOptions().get());
        }

        if(mediaAttachment.getContent().isPresent()){
            jgen.writeObjectField("content", mediaAttachment.getContent().get());
        }

        jgen.writeEndObject();
    }
}
