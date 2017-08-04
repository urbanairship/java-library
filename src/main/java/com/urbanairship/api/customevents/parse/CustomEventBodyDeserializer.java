package com.urbanairship.api.customevents.parse;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.customevents.model.CustomEventBody;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class CustomEventBodyDeserializer extends JsonDeserializer<CustomEventBody> {
    private static final FieldParserRegistry<CustomEventBody, CustomEventBodyReader> FIELD_PARSERS = new MapFieldParserRegistry<CustomEventBody, CustomEventBodyReader>(
            ImmutableMap.<String, FieldParser<CustomEventBodyReader>>builder()
                    .put("name", new FieldParser<CustomEventBodyReader>() {
                        public void parse(CustomEventBodyReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readName(json);
                        }
                    })
                    .put("value", new FieldParser<CustomEventBodyReader>() {
                        public void parse(CustomEventBodyReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readValue(json);
                        }
                    })
                    .put("transaction", new FieldParser<CustomEventBodyReader>() {
                        public void parse(CustomEventBodyReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readTransaction(json);
                        }
                    })
                    .put("interaction_id", new FieldParser<CustomEventBodyReader>() {
                        public void parse(CustomEventBodyReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readInteractionId(json);
                        }
                    })
                    .put("interaction_type", new FieldParser<CustomEventBodyReader>() {
                        public void parse(CustomEventBodyReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readInteractionType(json);
                        }
                    })
                    .put("properties", new FieldParser<CustomEventBodyReader>() {
                        public void parse(CustomEventBodyReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readProperties(json);
                        }
                    })
                    .put("session_id", new FieldParser<CustomEventBodyReader>() {
                        public void parse(CustomEventBodyReader reader, JsonParser json, DeserializationContext context) throws IOException {
                            reader.readSessionId(json);
                        }
                    })
                    .build()
    );

    private final StandardObjectDeserializer<CustomEventBody, ?> deserializer;

    public CustomEventBodyDeserializer() {
        deserializer = new StandardObjectDeserializer<CustomEventBody, CustomEventBodyReader>(
                FIELD_PARSERS,
                new Supplier<CustomEventBodyReader>() {
                    @Override
                    public CustomEventBodyReader get() {
                        return new CustomEventBodyReader();
                    }
                }
        );
    }

    @Override
    public CustomEventBody deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return null;
    }
}
