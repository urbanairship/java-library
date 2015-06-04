/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.reports.model.ReportsAPITimeInAppResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class ReportsAPITimeInAppResponseDeserializer extends JsonDeserializer<ReportsAPITimeInAppResponse> {

    private static final FieldParserRegistry<ReportsAPITimeInAppResponse, ReportsAPITimeInAppResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<ReportsAPITimeInAppResponse, ReportsAPITimeInAppResponseReader>(
                    ImmutableMap.<String, FieldParser<ReportsAPITimeInAppResponseReader>>builder()
                            .put("timeinapp", new FieldParser<ReportsAPITimeInAppResponseReader>() {
                                @Override
                                public void parse(ReportsAPITimeInAppResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                                    reader.readTimeInApp(jsonParser);
                                }
                            })
                            .build()
            );

    private final StandardObjectDeserializer<ReportsAPITimeInAppResponse, ?> deserializer;

    public ReportsAPITimeInAppResponseDeserializer() {
        deserializer = new StandardObjectDeserializer<ReportsAPITimeInAppResponse, ReportsAPITimeInAppResponseReader>(
                FIELD_PARSERS,
                new Supplier<ReportsAPITimeInAppResponseReader>() {
                    @Override
                    public ReportsAPITimeInAppResponseReader get() {
                        return new ReportsAPITimeInAppResponseReader();
                    }
                }
        );
    }

    @Override
    public ReportsAPITimeInAppResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
