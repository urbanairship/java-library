/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.android;

import com.urbanairship.api.push.model.notification.android.InboxStyle;
import com.urbanairship.api.push.model.notification.android.Style;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class InboxStyleSerializer extends JsonSerializer<InboxStyle> {

    @Override
    public void serialize(InboxStyle style, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField("type", Style.Type.INBOX.getStyleType());
        jgen.writeArrayFieldStart("lines");
        for (String value : style.getContent()) {
            jgen.writeString(value);
        }
        jgen.writeEndArray();

        if (style.getTitle().isPresent()) {
            jgen.writeStringField("title", style.getTitle().get());
        }
        if (style.getSummary().isPresent()) {
            jgen.writeStringField("summary", style.getSummary().get());
        }

        jgen.writeEndObject();
    }
}
