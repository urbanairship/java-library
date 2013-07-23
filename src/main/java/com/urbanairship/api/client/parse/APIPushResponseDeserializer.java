package com.urbanairship.api.client.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.client.APIPushResponse;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

/*
Deserializers create a mapping between Jackson and an object. This abstracts all
the boilerplate necessary for Jackson stream parsing, which is essentially what
 we're doing. This will be a lot cleaner when lambda's come down.
 If you're using Intellij, be sure and toggle open the code that's
 been collapsed.
 */
class APIPushResponseDeserializer extends JsonDeserializer<APIPushResponse> {

    private static final FieldParserRegistry<APIPushResponse, APIPushResponseReader> FIELD_PARSERS =
            new MapFieldParserRegistry<APIPushResponse, APIPushResponseReader>(
            ImmutableMap.<String, FieldParser <APIPushResponseReader>>builder()
            .put("operation_id", new FieldParser<APIPushResponseReader>() {
                @Override
                public void parse(APIPushResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readOperationId(jsonParser);
                }
            })
            .put("push_ids", new FieldParser<APIPushResponseReader>() {
                @Override
                public void parse(APIPushResponseReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                    reader.readPushIds(jsonParser);
                }
            })
            .build()
    );

    private final StandardObjectDeserializer<APIPushResponse, ?> deserializer;

    // See Google Guava for Supplier details
    public APIPushResponseDeserializer(){
        deserializer = new StandardObjectDeserializer<APIPushResponse, APIPushResponseReader>(
                FIELD_PARSERS,
                new Supplier<APIPushResponseReader>() {
                    @Override
                    public APIPushResponseReader get() {
                        return new APIPushResponseReader();
                    }
                }

        );
    }

    @Override
    public APIPushResponse deserialize(JsonParser parser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
