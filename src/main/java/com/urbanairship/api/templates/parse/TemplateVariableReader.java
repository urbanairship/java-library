/*
 * Copyright (c) 2013-2016. Urban Airship and Contributors
 */

package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.templates.model.TemplateVariable;

import java.io.IOException;

public class TemplateVariableReader implements JsonObjectReader<TemplateVariable> {
    private final TemplateVariable.Builder builder;

    public TemplateVariableReader() {
        this.builder = TemplateVariable.newBuilder();
    }

    public void readKey(JsonParser jsonParser) throws IOException {
        builder.setKey(jsonParser.readValueAs(String.class));
    }

    public void readName(JsonParser jsonParser) throws IOException {
        builder.setName(jsonParser.readValueAs(String.class));
    }

    public void readDescription(JsonParser jsonParser) throws IOException {
        builder.setDescription(jsonParser.readValueAs(String.class));
    }

    public void readDefaultValue(JsonParser jsonParser) throws IOException {
        builder.setDefaultValue(jsonParser.readValueAs(String.class));
    }

    @Override
    public TemplateVariable validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
