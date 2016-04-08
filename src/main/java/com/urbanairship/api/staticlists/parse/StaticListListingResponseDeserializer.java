/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.staticlists.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.staticlists.model.StaticListListingResponse;
import com.urbanairship.api.staticlists.model.StaticListListingResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class StaticListListingResponseDeserializer extends JsonDeserializer<StaticListListingResponse> {
    private static final FieldParserRegistry<StaticListListingResponse, StaticListListingResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<StaticListListingResponse, StaticListListingResponseReader>(
                    ImmutableMap.<String, FieldParser<StaticListListingResponseReader>>builder()
                            .put("ok", new FieldParser<StaticListListingResponseReader>() {
                                @Override
                                public void parse(StaticListListingResponseReader reader,
                                                  JsonParser jsonParser,
                                                  DeserializationContext deserializationContext) throws IOException {
                                    reader.readOk(jsonParser);
                                }
                            })
                            .put("lists", new FieldParser<StaticListListingResponseReader>() {
                                @Override
                                public void parse(StaticListListingResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readStaticListObjects(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<StaticListListingResponse, ?> deserializer;

    public StaticListListingResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<StaticListListingResponse, StaticListListingResponseReader>(
                FIELD_PARSERS,
                new Supplier<StaticListListingResponseReader>() {
                    @Override
                    public StaticListListingResponseReader get() {
                        return new StaticListListingResponseReader();
                    }
                }
        );
    }

    @Override
    public StaticListListingResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }


}
