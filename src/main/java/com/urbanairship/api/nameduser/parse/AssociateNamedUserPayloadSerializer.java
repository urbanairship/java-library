/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.nameduser.parse;

import com.urbanairship.api.nameduser.model.AssociateNamedUserPayload;
import com.urbanairship.api.tag.model.AddRemoveNamedUserFromTagPayload;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public final class AssociateNamedUserPayloadSerializer extends JsonSerializer<AssociateNamedUserPayload> {

    @Override
    public void serialize(AssociateNamedUserPayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField("channel_id", payload.getChannelId());
        jgen.writeStringField("device_type", payload.getDeviceType().getIdentifier());
        jgen.writeStringField("named_user_id", payload.getNamedUserId());
        jgen.writeEndObject();
    }
}
