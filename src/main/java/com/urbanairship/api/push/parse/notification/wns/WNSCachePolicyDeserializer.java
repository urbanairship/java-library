/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.model.notification.wns.WNSPush;
import com.urbanairship.api.push.parse.*;
import com.urbanairship.api.common.parse.*;
import com.google.common.base.Optional;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

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
