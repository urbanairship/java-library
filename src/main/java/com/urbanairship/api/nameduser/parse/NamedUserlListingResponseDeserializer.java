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
import com.urbanairship.api.nameduser.model.NamedUserListingResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class NamedUserlListingResponseDeserializer extends JsonDeserializer<NamedUserListingResponse> {

    private static final FieldParserRegistry<NamedUserListingResponse, NamedUserListingResponseReader> FIELD_PARSER =
        new MapFieldParserRegistry<NamedUserListingResponse, NamedUserListingResponseReader>(
            ImmutableMap.<String, FieldParser<NamedUserListingResponseReader>>builder()
                .put("ok", new FieldParser<NamedUserListingResponseReader>() {
                    @Override
                    public void parse(NamedUserListingResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                        reader.readOk(jsonParser);
                    }
                })
                .put("next_page", new FieldParser<NamedUserListingResponseReader>() {
                    @Override
                    public void parse(NamedUserListingResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                        reader.readNextPage(jsonParser);
                    }
                })
                .put("named_user", new FieldParser<NamedUserListingResponseReader>() {
                    @Override
                    public void parse(NamedUserListingResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                        reader.readNamedUser(jsonParser);
                    }
                })
                .put("named_users", new FieldParser<NamedUserListingResponseReader>() {
                    @Override
                    public void parse(NamedUserListingResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                        reader.readNamedUsers(jsonParser);
                    }
                })
                .build()
        );

    private final StandardObjectDeserializer<NamedUserListingResponse, ?> deserializer;

    public NamedUserlListingResponseDeserializer() {
        this.deserializer = new StandardObjectDeserializer<NamedUserListingResponse, NamedUserListingResponseReader>(
            FIELD_PARSER,
            new Supplier<NamedUserListingResponseReader>() {
                @Override
                public NamedUserListingResponseReader get() {
                    return new NamedUserListingResponseReader();
                }
            }
        );
    }

    @Override
    public NamedUserListingResponse deserialize(JsonParser jsonParser, DeserializationContext
        deserializationContext)
        throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
