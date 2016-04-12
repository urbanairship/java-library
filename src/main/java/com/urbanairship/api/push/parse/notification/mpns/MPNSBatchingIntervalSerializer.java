/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.mpns;

import com.urbanairship.api.push.model.notification.mpns.MPNSPush;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class MPNSBatchingIntervalSerializer extends JsonSerializer<MPNSPush.BatchingInterval> {
    @Override
    public void serialize(MPNSPush.BatchingInterval batchingInterval, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(batchingInterval.name().toLowerCase());
    }
}
