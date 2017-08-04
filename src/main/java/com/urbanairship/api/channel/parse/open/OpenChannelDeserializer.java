package com.urbanairship.api.channel.parse.open;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.Constants;
import com.urbanairship.api.channel.model.open.OpenChannel;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class OpenChannelDeserializer extends JsonDeserializer<OpenChannel> {

    private static final FieldParserRegistry<OpenChannel, OpenChannelReader> FIELD_PARSERS = new MapFieldParserRegistry<OpenChannel, OpenChannelReader>(
            ImmutableMap.<String, FieldParser<OpenChannelReader>>builder()
                    .put(Constants.OPEN_PLATFORM_NAME, new FieldParser<OpenChannelReader>() {
                        @Override
                        public void parse(OpenChannelReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                            reader.readPlatformName(parser);
                        }
                    })
                    .put(Constants.OLD_ADDRESS, new FieldParser<OpenChannelReader>() {
                        @Override
                        public void parse(OpenChannelReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                            reader.readOldAddress(parser);
                        }
                    })
                    .put(Constants.IDENTIFIERS, new FieldParser<OpenChannelReader>() {
                        @Override
                        public void parse(OpenChannelReader reader, JsonParser parser, DeserializationContext context) throws IOException {
                            reader.readIdentifiers(parser);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<OpenChannel, ?> deserializer;

    public OpenChannelDeserializer() {
        deserializer = new StandardObjectDeserializer<OpenChannel, OpenChannelReader>(
                FIELD_PARSERS,
                new Supplier<OpenChannelReader>() {
                    @Override
                    public OpenChannelReader get() {
                        return new OpenChannelReader();
                    }
                }
        );
    }

    @Override
    public OpenChannel deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return deserializer.deserialize(parser, context);
    }
}
