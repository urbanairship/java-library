/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.model.notification.wns.WNSPush;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class WNSCachePolicySerializer extends JsonSerializer<WNSPush.CachePolicy> {

    @Override
    public void serialize(WNSPush.CachePolicy policy, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(policy.getIdentifier());
    }
}
