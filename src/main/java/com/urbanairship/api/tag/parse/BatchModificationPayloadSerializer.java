/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.tag.parse;

import com.urbanairship.api.tag.model.BatchModificationPayload;
import com.urbanairship.api.tag.model.BatchTagSet;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public final class BatchModificationPayloadSerializer extends JsonSerializer<BatchModificationPayload> {

    @Override
    public void serialize(BatchModificationPayload payload, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartArray();

        for (BatchTagSet bts : payload.getBatchObjects()) {
            jgen.writeStartObject();

            switch(bts.getIdType())
            {
                case IOS_CHANNEL:
                    jgen.writeStringField("ios_channel", bts.getDeviceID());
                    break;
                case DEVICE_TOKEN:
                    jgen.writeStringField("device_token", bts.getDeviceID());
                    break;
                case APID:
                    jgen.writeStringField("apid", bts.getDeviceID());
                    break;
            }

            jgen.writeObjectField("tags", bts.getTags());

            jgen.writeEndObject();
        }

        jgen.writeEndArray();
    }
}
