/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.actions;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.actions.ShareAction;

import java.io.IOException;

public final class ShareActionSerializer extends JsonSerializer<ShareAction> {
    @Override
    public void serialize(ShareAction value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(value.getValue());
    }
}
