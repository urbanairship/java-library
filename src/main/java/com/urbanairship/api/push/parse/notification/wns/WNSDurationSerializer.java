/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.model.notification.wns.WNSToastData;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class WNSDurationSerializer extends JsonSerializer<WNSToastData.Duration> {
    @Override
    public void serialize(WNSToastData.Duration duration, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(duration.getIdentifier());
    }
}
