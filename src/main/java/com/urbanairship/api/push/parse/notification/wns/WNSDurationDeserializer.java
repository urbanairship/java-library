/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.model.notification.wns.WNSToastData;
import com.urbanairship.api.push.parse.*;
import com.urbanairship.api.common.parse.*;
import com.google.common.base.Optional;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class WNSDurationDeserializer extends JsonDeserializer<WNSToastData.Duration> {
    public static final WNSDurationDeserializer INSTANCE = new WNSDurationDeserializer();

    @Override
    public WNSToastData.Duration deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String value = jp.getText();
        WNSToastData.Duration duration = WNSToastData.Duration.get(value);
        if (duration == null) {
            APIParsingException.raise("Unrecognized WNS toast duration " + value, jp);
        }
        return duration;
    }
}
