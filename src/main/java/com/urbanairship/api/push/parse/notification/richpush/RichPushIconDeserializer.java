/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.richpush;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.richpush.RichPushIcon;

import java.io.IOException;

public class RichPushIconDeserializer extends JsonDeserializer<RichPushIcon> {

    private static final FieldParserRegistry<RichPushIcon, RichPushIconReader> FIELD_PARSERS = new MapFieldParserRegistry<RichPushIcon, RichPushIconReader>(
            ImmutableMap.<String, FieldParser<RichPushIconReader>>builder()
            .put("list_icon", new FieldParser<RichPushIconReader>() {
                    public void parse(RichPushIconReader reader, JsonParser json, DeserializationContext context) throws IOException {
                        reader.readListIcon(json);
                    }
                })
            .build()
            );

    private final StandardObjectDeserializer<RichPushIcon, ?> deserializer;

    public RichPushIconDeserializer() {
        deserializer = new StandardObjectDeserializer<RichPushIcon, RichPushIconReader>(
            FIELD_PARSERS,
            new Supplier<RichPushIconReader>() {
                @Override
                public RichPushIconReader get() {
                    return new RichPushIconReader();
                }
            }
        );
    }

    @Override
    public RichPushIcon deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
