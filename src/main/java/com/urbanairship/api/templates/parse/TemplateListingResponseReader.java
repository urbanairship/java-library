/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.templates.model.TemplateListingResponse;
import com.urbanairship.api.templates.model.TemplateView;

import java.io.IOException;
import java.util.List;

public class TemplateListingResponseReader implements JsonObjectReader<TemplateListingResponse> {
    private final TemplateListingResponse.Builder builder;

    public TemplateListingResponseReader() {
        this.builder = TemplateListingResponse.newBuilder();
    }

    public void readOk(JsonParser jsonParser) throws IOException {
        builder.setOk(jsonParser.readValueAs(boolean.class));
    }

    public void readTemplate(JsonParser jsonParser) throws IOException {
        builder.setTemplate(jsonParser.readValueAs(TemplateView.class));
    }

    public void readAllTemplates(JsonParser jsonParser) throws IOException {
        builder.setTemplates((List<TemplateView>)jsonParser.readValueAs(new TypeReference<List<TemplateView>>() {
        }));
    }

    public void readCount(JsonParser jsonParser) throws IOException {
        builder.setCount(jsonParser.readValueAs(Integer.class));
    }

    public void readTotalCount(JsonParser jsonParser) throws IOException {
        builder.setTotalCount(jsonParser.readValueAs(Integer.class));
    }

    public void readNextPage(JsonParser jsonParser) throws IOException {
        builder.setNextPage(jsonParser.readValueAs(String.class));
    }

    public void readPrevPage(JsonParser jsonParser) throws IOException {
        builder.setPrevPage(jsonParser.readValueAs(String.class));
    }

    @Override
    public TemplateListingResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
