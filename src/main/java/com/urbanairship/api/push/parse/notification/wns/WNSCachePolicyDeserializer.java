/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.notification.wns.WNSPush;

import java.io.IOException;

public class WNSCachePolicyDeserializer extends JsonDeserializer<WNSPush.CachePolicy> {
    public static final WNSCachePolicyDeserializer INSTANCE = new WNSCachePolicyDeserializer();
    @Override
    public WNSPush.CachePolicy deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String value = jp.getText();
        WNSPush.CachePolicy type = WNSPush.CachePolicy.get(value);
        if (type == null) {
            APIParsingException.raise("Unrecognized WNS cache policy " + value, jp);
        }
        return type;
    }
}
