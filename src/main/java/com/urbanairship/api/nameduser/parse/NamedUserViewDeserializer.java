/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.nameduser.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.nameduser.model.NamedUserView;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class NamedUserViewDeserializer extends JsonDeserializer<NamedUserView> {

    private static final FieldParserRegistry<NamedUserView, NamedUserViewReader> FIELD_PARSERS = new MapFieldParserRegistry<NamedUserView, NamedUserViewReader>(
        ImmutableMap.<String, FieldParser<NamedUserViewReader>>builder()
            .put("named_user_id", new FieldParser<NamedUserViewReader>() {
                @Override
                public void parse(NamedUserViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readNamedUserId(jsonParser);
                }
            })
            .put("tags", new FieldParser<NamedUserViewReader>() {
                @Override
                public void parse(NamedUserViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readNamedUserTags(jsonParser);
                }
            })
            .put("channels", new FieldParser<NamedUserViewReader>() {
                @Override
                public void parse(NamedUserViewReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readChannelView(jsonParser);
                }
            })
            .build()
    );

    private final StandardObjectDeserializer<NamedUserView, ?> deserializer;

    public NamedUserViewDeserializer() {
        deserializer = new StandardObjectDeserializer<NamedUserView, NamedUserViewReader>(
            FIELD_PARSERS,
            new Supplier<NamedUserViewReader>() {
                @Override
                public NamedUserViewReader get() {
                    return new NamedUserViewReader();
                }
            }
        );
    }

    @Override
    public NamedUserView deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
