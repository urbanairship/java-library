/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.actions;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.urbanairship.api.push.model.notification.actions.AppDefinedAction;

import java.io.IOException;

public class AppDefinedSerializer extends JsonSerializer<AppDefinedAction> {
    @Override
    public void serialize(AppDefinedAction value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeTree(value.getValue());
    }
}
