/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.common.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.google.common.base.Supplier;

import java.io.IOException;
import java.util.Optional;

public class StandardObjectDeserializer<T, R extends JsonObjectReader<T>> {

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
                APIParsingException.raise("Parsing of json failed.  Expected to be at field name token but was " + token.name(), jp);
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
