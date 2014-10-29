/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.actions;

import com.urbanairship.api.push.model.notification.actions.RemoveTagAction;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public final class RemoveTagActionSerializer extends JsonSerializer<RemoveTagAction> {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Override
    public void serialize(RemoveTagAction tagAction, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeTree(MAPPER.valueToTree(tagAction.getValue()));
    }
}