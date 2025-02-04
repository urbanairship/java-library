/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.ChannelView;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;

import java.io.IOException;

public final class ChannelViewDeserializer extends JsonDeserializer<ChannelView> {

    private static final FieldParserRegistry<ChannelView, ChannelViewReader> FIELD_PARSERS = new MapFieldParserRegistry<ChannelView, ChannelViewReader>(
            ImmutableMap.<String, FieldParser<ChannelViewReader>>builder()
                    .put(Constants.CHANNEL_ID, (reader, jsonParser, deserializationContext) -> reader.readChannelId(jsonParser))
                    .put(Constants.DEVICE_TYPE, (reader, jsonParser, deserializationContext) -> reader.readDeviceType(jsonParser))
                    .put(Constants.INSTALLED, (reader, jsonParser, deserializationContext) -> reader.readInstalled(jsonParser))
                    .put(Constants.OPT_IN, (reader, jsonParser, deserializationContext) -> reader.readOptIn(jsonParser))
                    .put(Constants.BACKGROUND, (reader, jsonParser, deserializationContext) -> reader.readBackground(jsonParser))
                    .put(Constants.PUSH_ADDRESS, (reader, jsonParser, deserializationContext) -> reader.readPushAddress(jsonParser))
                    .put(Constants.CREATED, (reader, jsonParser, deserializationContext) -> reader.readCreated(jsonParser))
                    .put(Constants.LAST_REGISTRATION, (reader, jsonParser, deserializationContext) -> reader.readLastRegistration(jsonParser))
                    .put(Constants.ALIAS, (reader, jsonParser, deserializationContext) -> reader.readAlias(jsonParser))
                    .put(Constants.TAGS, (reader, jsonParser, deserializationContext) -> reader.readTags(jsonParser))
                    .put(Constants.TAG_GROUPS, (reader, jsonParser, deserializationContext) -> reader.readTagGroups(jsonParser))
                    .put(Constants.IOS, (reader, jsonParser, deserializationContext) -> reader.readIosSettings(jsonParser))
                    .put(Constants.WEB, (reader, jsonParser, deserializationContext) -> reader.readWeb(jsonParser))
                    .put(Constants.OPEN_CHANNEL, (reader, jsonParser, deserializationContext) -> reader.readOpenChannel(jsonParser))
                    .put(Constants.ADDRESS, (reader, jsonParser, deserializationContext) -> reader.readAddress(jsonParser))
                    .put(Constants.NAMED_USER, (reader, jsonParser, deserializationContext) -> reader.readNamedUser(jsonParser))
                    .put("attributes", (reader, jsonParser, deserializationContext) -> reader.readAttributes(jsonParser))
                    .put("device_attributes", (reader, jsonParser, deserializationContext) -> reader.readDeviceAttributes(jsonParser))
                    .put("commercial_opted_in", (reader, jsonParser, deserializationContext) -> reader.readCommercialOptedIn(jsonParser))
                    .put("commercial_opted_out", (reader, jsonParser, deserializationContext) -> reader.readCommercialOptedOut(jsonParser))
                    .put("transactional_opted_in", (reader, jsonParser, deserializationContext) -> reader.readTransactionalOptedIn(jsonParser))
                    .put("transactional_opted_out", (reader, jsonParser, deserializationContext) -> reader.readTransactionalOptedOut(jsonParser))
                    .put("email_address", (reader, jsonParser, deserializationContext) -> reader.readEmailAddress(jsonParser))
                    .put("suppression_state", (reader, jsonParser, deserializationContext) -> reader.readSuppressionState(jsonParser))
                    .build());

    private final StandardObjectDeserializer<ChannelView, ?> deserializer;

    public ChannelViewDeserializer() {
        deserializer = new StandardObjectDeserializer<ChannelView, ChannelViewReader>(
                FIELD_PARSERS,
                () -> new ChannelViewReader()
        );
    }

    @Override
    public ChannelView deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
