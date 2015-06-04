/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.actions;

import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.notification.actions.ShareAction;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class ShareActionDeserializer extends JsonDeserializer<ShareAction> {
    @Override
    public ShareAction deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return new ShareAction(StringFieldDeserializer.INSTANCE.deserialize(jp, "share"));
    }
}
