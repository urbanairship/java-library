/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.richpush;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;

import java.io.IOException;

public class RichPushMessageSerializer extends JsonSerializer<RichPushMessage> {
    @Override
    public void serialize(RichPushMessage message, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("title", message.getTitle());
        jgen.writeStringField("body", message.getBody());
        jgen.writeStringField("content_type", message.getContentType());
        jgen.writeStringField("content_encoding", message.getContentEncoding());
        if (message.getExpiry().isPresent()) {
            jgen.writeObjectField("expiry", message.getExpiry().get());
        }
        if (message.getExtra().isPresent()) {
            jgen.writeObjectField("extra", message.getExtra().get());
        }
        if (message.getIcons().isPresent()) {
            jgen.writeObjectField("icons", message.getIcons().get());
        }
        if (message.getRichPushTemplate().isPresent()) {
            jgen.writeObjectField("template", message.getRichPushTemplate().get());
        }
        jgen.writeEndObject();
    }
}
