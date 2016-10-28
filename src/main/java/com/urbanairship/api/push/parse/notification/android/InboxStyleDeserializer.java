/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.android;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.android.InboxStyle;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class InboxStyleDeserializer extends JsonDeserializer<InboxStyle> {
    private static final FieldParserRegistry<InboxStyle, InboxStyleReader> FIELD_PARSERS = new MapFieldParserRegistry<InboxStyle, InboxStyleReader>(
            ImmutableMap.<String, FieldParser<InboxStyleReader>>builder()
                    .put("title", new FieldParser<InboxStyleReader>() {
                        public void parse(InboxStyleReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readTitle(json);
                        }
                    })
                    .put("summary", new FieldParser<InboxStyleReader>() {
                        public void parse(InboxStyleReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readSummary(json);
                        }
                    })
                    .put("lines", new FieldParser<InboxStyleReader>() {
                        public void parse(InboxStyleReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readContent(json);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<InboxStyle, ?> deserializer;

    public InboxStyleDeserializer() {
        deserializer = new StandardObjectDeserializer<InboxStyle, InboxStyleReader>(
                FIELD_PARSERS,
                new Supplier<InboxStyleReader>() {
                    @Override
                    public InboxStyleReader get() {
                        return new InboxStyleReader();
                    }
                }
        );
    }

    @Override
    public InboxStyle deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return deserializer.deserialize(jp, ctxt);
    }
}
