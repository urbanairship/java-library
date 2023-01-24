/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.actions;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.actions.Actions;

import java.io.IOException;

public final class ActionsDeserializer extends JsonDeserializer<Actions> {

    private static final FieldParserRegistry<Actions, ActionsReader> READ_ACTIONS = new MapFieldParserRegistry<Actions, ActionsReader>(ImmutableMap.<String, FieldParser<ActionsReader>>builder()
            .put("add_tag", (reader, jsonParser, deserializationContext) -> reader.readAddTags(jsonParser))
            .put("remove_tag", (reader, jsonParser, deserializationContext) -> reader.readRemoveTags(jsonParser))
            .put("open", (reader, jsonParser, deserializationContext) -> reader.readOpen(jsonParser))
            .put("app_defined", (reader, jsonParser, deserializationContext) -> reader.readAppDefined(jsonParser))
            .put("share", (reader, jsonParser, deserializationContext) -> reader.readShare(jsonParser))
            .build());

    private static final StandardObjectDeserializer<Actions, ActionsReader> deserializer = new StandardObjectDeserializer<Actions, ActionsReader>(READ_ACTIONS, () -> new ActionsReader());

    public Actions deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
