/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.richpush;

import com.urbanairship.api.push.model.notification.richpush.RichPushIcon;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class RichPushIconSerializer extends JsonSerializer<RichPushIcon> {
    @Override
    public void serialize(RichPushIcon icon, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("list_icon", icon.getListIcon());
        jgen.writeEndObject();
    }
}
