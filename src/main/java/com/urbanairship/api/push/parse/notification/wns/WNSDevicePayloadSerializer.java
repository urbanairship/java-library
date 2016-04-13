/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.model.notification.wns.WNSDevicePayload;
import com.urbanairship.api.push.model.notification.wns.WNSPush;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class WNSDevicePayloadSerializer extends JsonSerializer<WNSDevicePayload> {
    @Override
    public void serialize(WNSDevicePayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        jgen.writeStartObject();

        if (payload.getAlert().isPresent()) {
            jgen.writeStringField("alert", payload.getAlert().get());
        }

        if (payload.getBody().isPresent()) {
            WNSPush body = payload.getBody().get();

            if (body.getToast().isPresent()) {
                jgen.writeObjectField("toast", body.getToast().get());
            }

            if (body.getTile().isPresent()) {
                jgen.writeObjectField("tile", body.getTile().get());
            }

            if (body.getBadge().isPresent()) {
                jgen.writeObjectField("badge", body.getBadge().get());
            }

            if (body.getCachePolicy().isPresent()) {
                jgen.writeObjectField("cache_policy", body.getCachePolicy().get());
            }
        }

        jgen.writeEndObject();
    }
}
