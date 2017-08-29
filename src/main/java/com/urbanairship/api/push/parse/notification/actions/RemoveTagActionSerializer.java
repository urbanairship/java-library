/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.actions;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.actions.RemoveTagAction;
import com.urbanairship.api.push.parse.PushObjectMapper;

import java.io.IOException;

public final class RemoveTagActionSerializer extends JsonSerializer<RemoveTagAction> {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Override
    public void serialize(RemoveTagAction tagAction, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeTree(MAPPER.valueToTree(tagAction.getValue()));
    }
}
