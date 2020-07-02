package com.urbanairship.api.customevents.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.customevents.model.CustomEventChannelType;
import com.urbanairship.api.customevents.model.CustomEventUser;

import java.io.IOException;

public class CustomEventUserDeserializer {

    private static final FieldParserRegistry<CustomEventUser, CustomEventUserReader> FIELD_PARSERS = new MapFieldParserRegistry<CustomEventUser, CustomEventUserReader>(
            ImmutableMap.<String, FieldParser<CustomEventUserReader>>builder()
            .put("amazon_channel", new FieldParser<CustomEventUserReader>() {
                @Override
                public void parse(CustomEventUserReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readChannel(CustomEventChannelType.AMAZON_CHANNEL, json);
                }
            })
            .put("android_channel", new FieldParser<CustomEventUserReader>() {
                @Override
                public void parse(CustomEventUserReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readChannel(CustomEventChannelType.ANDROID_CHANNEL, json);
                }
            })
            .put("ios_channel", new FieldParser<CustomEventUserReader>() {
                @Override
                public void parse(CustomEventUserReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readChannel(CustomEventChannelType.IOS_CHANNEL, json);
                }
            })
            .put("named_user_channel", new FieldParser<CustomEventUserReader>() {
                @Override
                public void parse(CustomEventUserReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readChannel(CustomEventChannelType.NAMED_USER_CHANNEL, json);
                }
            })

            .build()
    );
}
