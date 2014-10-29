package com.urbanairship.api.channel.information.parse.ios;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.channel.information.model.ios.IosSettings;
import com.urbanairship.api.channel.information.util.Constants;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public final class IosSettingsDeserializer extends JsonDeserializer<IosSettings> {

    private static final FieldParserRegistry<IosSettings, IosSettingsReader> FIELD_PARSERS = new MapFieldParserRegistry<IosSettings, IosSettingsReader>(
            ImmutableMap.<String, FieldParser<IosSettingsReader>>builder()
                    .put(Constants.BADGE, new FieldParser<IosSettingsReader>() {
                        @Override
                        public void parse(IosSettingsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readBadge(jsonParser);
                        }
                    })
                    .put(Constants.QUIETTIME, new FieldParser<IosSettingsReader>() {
                        @Override
                        public void parse(IosSettingsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readQuietTime(jsonParser);
                        }
                    })
                    .put(Constants.TZ, new FieldParser<IosSettingsReader>() {
                        @Override
                        public void parse(IosSettingsReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            reader.readTimeZone(jsonParser);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<IosSettings, ?> deserializer;

    public IosSettingsDeserializer() {
        deserializer = new StandardObjectDeserializer<IosSettings, IosSettingsReader>(
                FIELD_PARSERS,
                new Supplier<IosSettingsReader>() {
                    @Override
                    public IosSettingsReader get() {
                        return new IosSettingsReader();
                    }
                }
        );
    }

    @Override
    public IosSettings deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}