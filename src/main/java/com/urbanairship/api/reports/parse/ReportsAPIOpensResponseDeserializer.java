/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.*;
import com.urbanairship.api.reports.model.ReportsAPIOpensResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class ReportsAPIOpensResponseDeserializer extends JsonDeserializer<ReportsAPIOpensResponse> {

    private static final FieldParserRegistry<ReportsAPIOpensResponse, ReportsAPIOpensResponseReader> FIELD_PARSERS =
        new MapFieldParserRegistry<ReportsAPIOpensResponse, ReportsAPIOpensResponseReader>(
                ImmutableMap.<String, FieldParser<ReportsAPIOpensResponseReader>>builder()
        .put("opens", new FieldParser<ReportsAPIOpensResponseReader>() {
            @Override
            public void parse(ReportsAPIOpensResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                reader.readOpens(jsonParser);
            }
        })
        .build()
    );

    private final StandardObjectDeserializer<ReportsAPIOpensResponse, ?> deserializer;

    public ReportsAPIOpensResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<ReportsAPIOpensResponse, ReportsAPIOpensResponseReader>(
                FIELD_PARSERS,
                new Supplier<ReportsAPIOpensResponseReader>() {
                    @Override
                    public ReportsAPIOpensResponseReader get() {
                        return new ReportsAPIOpensResponseReader();
                    }
                }
        );
    }

    @Override
    public ReportsAPIOpensResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
