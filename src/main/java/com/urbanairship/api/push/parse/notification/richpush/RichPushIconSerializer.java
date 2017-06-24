/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.richpush;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.richpush.RichPushIcon;

import java.io.IOException;

public class RichPushIconSerializer extends JsonSerializer<RichPushIcon> {
    @Override
    public void serialize(RichPushIcon icon, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("list_icon", icon.getListIcon());
        jgen.writeEndObject();
    }
}
