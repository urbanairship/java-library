/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.templates.model.TemplateVariable;

import java.io.IOException;

public class TemplateVariableDeserializer extends JsonDeserializer<TemplateVariable> {
    private static final FieldParserRegistry<TemplateVariable, TemplateVariableReader> FIELD_PARSER =
            new MapFieldParserRegistry<TemplateVariable, TemplateVariableReader>(
                    ImmutableMap.<String, FieldParser<TemplateVariableReader>>builder()
                            .put("key", (reader, jsonParser, deserializationContext) -> reader.readKey(jsonParser))
                            .put("name", (reader, jsonParser, deserializationContext) -> reader.readName(jsonParser))
                            .put("description", (reader, jsonParser, deserializationContext) -> reader.readDescription(jsonParser))
                            .put("default_value", (reader, jsonParser, deserializationContext) -> reader.readDefaultValue(jsonParser))
                            .build()
            );

    private final StandardObjectDeserializer<TemplateVariable, ?> deserializer;

    public TemplateVariableDeserializer() {
        this.deserializer = new StandardObjectDeserializer<TemplateVariable, TemplateVariableReader>(
                FIELD_PARSER,
                () -> new TemplateVariableReader()
        );
    }

    @Override
    public TemplateVariable deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }

}
