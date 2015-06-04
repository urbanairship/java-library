/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.actions;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.actions.Actions;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public final class ActionsDeserializer extends JsonDeserializer<Actions> {

    private static final FieldParserRegistry<Actions, ActionsReader> READ_ACTIONS = new MapFieldParserRegistry<Actions, ActionsReader>(ImmutableMap.<String, FieldParser<ActionsReader>>builder()
            .put("add_tag", new FieldParser<ActionsReader>() {
                @Override
                public void parse(ActionsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readAddTags(jsonParser);
                }
            })
            .put("remove_tag", new FieldParser<ActionsReader>() {
                @Override
                public void parse(ActionsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readRemoveTags(jsonParser);
                }
            })
            .put("open", new FieldParser<ActionsReader>() {
                @Override
                public void parse(ActionsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readOpen(jsonParser);
                }
            })
            .put("app_defined", new FieldParser<ActionsReader>() {
                @Override
                public void parse(ActionsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readAppDefined(jsonParser);
                }
            })
            .put("share", new FieldParser<ActionsReader>() {
                @Override
                public void parse(ActionsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readShare(jsonParser);
                }
            })
            .build());

    private static final StandardObjectDeserializer<Actions, ActionsReader> deserializer = new StandardObjectDeserializer<Actions, ActionsReader>(READ_ACTIONS, new Supplier<ActionsReader>() {
        @Override
        public ActionsReader get() {
            return new ActionsReader();
        }
    });

    public Actions deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
