/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.information.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.information.model.ChannelView;
import com.urbanairship.api.channel.information.util.Constants;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public final class ChannelViewDeserializer extends JsonDeserializer<ChannelView> {

    private static final FieldParserRegistry<ChannelView, ChannelViewReader> FIELD_PARSERS = new MapFieldParserRegistry<ChannelView, ChannelViewReader>(
            ImmutableMap.<String, FieldParser<ChannelViewReader>>builder()
                    .put(Constants.CHANNEL_ID, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readChannelId(jsonParser);
                        }
                    })
                    .put(Constants.DEVICE_TYPE, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readDeviceType(jsonParser);
                        }
                    })
                    .put(Constants.INSTALLED, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readInstalled(jsonParser);
                        }
                    })
                    .put(Constants.OPT_IN, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readOptIn(jsonParser);
                        }
                    })
                    .put(Constants.BACKGROUND, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readBackground(jsonParser);
                        }
                    })
                    .put(Constants.PUSH_ADDRESS, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readPushAddress(jsonParser);
                        }
                    })
                    .put(Constants.CREATED, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readCreated(jsonParser);
                        }
                    })
                    .put(Constants.LAST_REGISTRATION, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readLastRegistration(jsonParser);
                        }
                    })
                    .put(Constants.ALIAS, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readAlias(jsonParser);
                        }
                    })
                    .put(Constants.TAGS, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readTags(jsonParser);
                        }
                    })
                    .put(Constants.IOS, new FieldParser<ChannelViewReader>() {
                        @Override
                        public void parse(ChannelViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readIosSettings(jsonParser);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<ChannelView, ?> deserializer;

    public ChannelViewDeserializer() {
        deserializer = new StandardObjectDeserializer<ChannelView, ChannelViewReader>(
                FIELD_PARSERS,
                new Supplier<ChannelViewReader>() {
                    @Override
                    public ChannelViewReader get() {
                        return new ChannelViewReader();
                    }
                }
        );
    }

    @Override
    public ChannelView deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
