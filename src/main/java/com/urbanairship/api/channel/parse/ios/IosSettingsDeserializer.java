/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.parse.ios;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.ios.IosSettings;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;

import java.io.IOException;

public final class IosSettingsDeserializer extends JsonDeserializer<IosSettings> {

    private static final FieldParserRegistry<IosSettings, IosSettingsReader> FIELD_PARSERS = new MapFieldParserRegistry<IosSettings, IosSettingsReader>(
            ImmutableMap.<String, FieldParser<IosSettingsReader>>builder()
                    .put(Constants.BADGE, (reader, jsonParser, deserializationContext) -> reader.readBadge(jsonParser))
                    .put(Constants.QUIETTIME, (reader, jsonParser, deserializationContext) -> reader.readQuietTime(jsonParser))
                    .put(Constants.TZ, (reader, jsonParser, deserializationContext) -> reader.readTimeZone(jsonParser))
                    .build()
    );

    private final StandardObjectDeserializer<IosSettings, ?> deserializer;

    public IosSettingsDeserializer() {
        deserializer = new StandardObjectDeserializer<IosSettings, IosSettingsReader>(
                FIELD_PARSERS,
                () -> new IosSettingsReader()
        );
    }

    @Override
    public IosSettings deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
