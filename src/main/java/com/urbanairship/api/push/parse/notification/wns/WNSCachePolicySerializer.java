/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.wns.WNSPush;

import java.io.IOException;

public class WNSCachePolicySerializer extends JsonSerializer<WNSPush.CachePolicy> {

    @Override
    public void serialize(WNSPush.CachePolicy policy, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(policy.getIdentifier());
    }
}
