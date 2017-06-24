/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.notification.wns.WNSToastData;

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
