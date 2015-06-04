/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.actions;

import com.urbanairship.api.push.model.notification.actions.ShareAction;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public final class ShareActionSerializer extends JsonSerializer<ShareAction> {
    @Override
    public void serialize(ShareAction value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(value.getValue());
    }
}
