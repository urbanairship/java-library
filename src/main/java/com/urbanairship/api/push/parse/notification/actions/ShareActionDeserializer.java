/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.actions;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.notification.actions.ShareAction;

import java.io.IOException;

public class ShareActionDeserializer extends JsonDeserializer<ShareAction> {
    @Override
    public ShareAction deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return new ShareAction(StringFieldDeserializer.INSTANCE.deserialize(jp, "share"));
    }
}
