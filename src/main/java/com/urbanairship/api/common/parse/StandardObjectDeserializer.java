package com.urbanairship.api.common.parse;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

public class StandardObjectDeserializer<T, R extends JsonObjectReader<T>> {

    private static final Logger log = LogManager.getLogger(StandardObjectDeserializer.class);
    private final FieldParserRegistry<T, R> registry;
    private final Supplier<R> readerSupplier;

    public StandardObjectDeserializer(FieldParserRegistry<T, R> registry, Supplier<R> readerSupplier) {
        this.registry = registry;
        this.readerSupplier = readerSupplier;
    }

    public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken token = jp.getCurrentToken();
        if (token == JsonToken.START_OBJECT) {
            token = jp.nextToken();
        }

        R reader = readerSupplier.get();
        while (token != null && token != JsonToken.END_OBJECT) {
            if (token != JsonToken.FIELD_NAME) {
               throw new APIParsingException(String.format("Parsing of json failed. Expected to be at field name token but was %s", token.name()));
            }

            String name = jp.getCurrentName();
            jp.nextToken();

            Optional<FieldParser<R>> fieldParser = registry.getFieldParser(name);
            if (fieldParser.isPresent()) {
                fieldParser.get().parse(reader, jp, ctxt);
            }
            else {
                jp.skipChildren();
            }

            token = jp.nextToken();
        }

        return reader.validateAndBuild();
    }
}
