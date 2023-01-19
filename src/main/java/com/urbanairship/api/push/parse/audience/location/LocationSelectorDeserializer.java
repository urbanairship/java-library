/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.audience.location;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.audience.location.LocationSelector;

import java.io.IOException;

public class LocationSelectorDeserializer extends JsonDeserializer<LocationSelector> {

    private static final FieldParserRegistry<LocationSelector, LocationSelectorReader> FIELD_PARSERS = new MapFieldParserRegistry<LocationSelector, LocationSelectorReader>(
        ImmutableMap.<String, FieldParser<LocationSelectorReader>>builder()
            .put("id", (reader, parser, context) -> reader.readId(parser))
            .put("date", (reader, parser, context) -> reader.readDateRange(parser))
        .build(),
        /* default field parser, for aliases */
            (reader, parser, context) -> {
                // Might be a bug in StandardObjectDeserializer that
                // it's called with the parent field name...
                if (!parser.getCurrentName().equals("location")) {
                    reader.readAlias(parser);
                }
            }
    );

    private final StandardObjectDeserializer<LocationSelector, ?> deserializer;

    public LocationSelectorDeserializer() {
        deserializer = new StandardObjectDeserializer<LocationSelector, LocationSelectorReader>(
            FIELD_PARSERS,
                () -> new LocationSelectorReader()
        );
    }

    @Override
    public LocationSelector deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
