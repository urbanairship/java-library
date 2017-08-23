/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.staticlists.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.staticlists.model.StaticListView;

import java.io.IOException;

public class StaticListViewDeserializer extends JsonDeserializer<StaticListView> {
    private static final FieldParserRegistry<StaticListView, StaticListViewReader> FIELD_PARSERS =
            new MapFieldParserRegistry<StaticListView, StaticListViewReader>(
                    ImmutableMap.<String, FieldParser<StaticListViewReader>>builder()
                            .put("ok", new FieldParser<StaticListViewReader>() {
                                @Override
                                public void parse(StaticListViewReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readOk(jsonParser);
                                }
                            })
                            .put("name", new FieldParser<StaticListViewReader>() {
                                @Override
                                public void parse(StaticListViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readName(jsonParser);
                                }
                            })
                            .put("description", new FieldParser<StaticListViewReader>() {
                                @Override
                                public void parse(StaticListViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readDescription(jsonParser);
                                }
                            })
                            .put("extra", new FieldParser<StaticListViewReader>() {
                                @Override
                                public void parse(StaticListViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readExtras(jsonParser);
                                }
                            })
                            .put("created", new FieldParser<StaticListViewReader>() {
                                @Override
                                public void parse(StaticListViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readCreated(jsonParser);
                                }
                            })
                            .put("last_updated", new FieldParser<StaticListViewReader>() {
                                @Override
                                public void parse(StaticListViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readLastUpdated(jsonParser);
                                }
                            })
                            .put("channel_count", new FieldParser<StaticListViewReader>() {
                                @Override
                                public void parse(StaticListViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readChannelCount(jsonParser);
                                }
                            })
                            .put("status", new FieldParser<StaticListViewReader>() {
                                @Override
                                public void parse(StaticListViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readStatus(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<StaticListView, ?> deserializer;

    public StaticListViewDeserializer() {
        deserializer = new StandardObjectDeserializer<StaticListView, StaticListViewReader>(
                FIELD_PARSERS,
                new Supplier<StaticListViewReader>() {
                    @Override
                    public StaticListViewReader get() {
                        return new StaticListViewReader();
                    }
                }
        );
    }

    @Override
    public StaticListView deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }


}
