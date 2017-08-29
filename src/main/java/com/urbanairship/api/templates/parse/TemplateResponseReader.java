/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.templates.model.TemplateResponse;

import java.io.IOException;
import java.util.List;

public class TemplateResponseReader implements JsonObjectReader<TemplateResponse> {
    private final TemplateResponse.Builder builder;

    public TemplateResponseReader() {
        this.builder = TemplateResponse.newBuilder();
    }

    public void readOk(JsonParser jsonParser) throws IOException {
        builder.setOk(jsonParser.readValueAs(boolean.class));
    }

    public void readOperationId(JsonParser jsonParser) throws IOException {
        builder.setOperationId(jsonParser.readValueAs(String.class));
    }

    public void readTemplateId(JsonParser jsonParser) throws IOException {
        builder.setTemplateId(jsonParser.readValueAs(String.class));
    }

    public void addAllPushIds(JsonParser jsonParser) throws IOException {
        builder.addAllPushIds((List<String>)jsonParser.readValueAs(new TypeReference<List<String>>() {
        }));
    }

    @Override
    public TemplateResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
