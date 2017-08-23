/*
 * Copyright (c) 2013-2016. Urban Airship and Contributors
 */

package com.urbanairship.api.templates.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.templates.model.PartialPushPayload;
import com.urbanairship.api.templates.model.TemplateVariable;
import com.urbanairship.api.templates.model.TemplateView;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

public class TemplateViewReader implements JsonObjectReader<TemplateView> {

    private final TemplateView.Builder builder;

    public TemplateViewReader() {
        this.builder = TemplateView.newBuilder();
    }

    public void readId(JsonParser jsonParser) throws IOException {
        builder.setId(jsonParser.readValueAs(String.class));
    }

    public void readCreatedAt(JsonParser jsonParser) throws IOException {
        builder.setCreatedAt(jsonParser.readValueAs(DateTime.class));
    }

    public void readModifiedAt(JsonParser jsonParser) throws IOException {
        builder.setModifiedAt(jsonParser.readValueAs(DateTime.class));
    }

    public void readLastUsed(JsonParser jsonParser) throws IOException {
        builder.setLastUsed(jsonParser.readValueAs(DateTime.class));
    }

    public void readName(JsonParser jsonParser) throws IOException {
        builder.setName(jsonParser.readValueAs(String.class));
    }

    public void readDescription(JsonParser jsonParser) throws IOException {
        builder.setDescription(jsonParser.readValueAs(String.class));
    }

    public void readVariables(JsonParser jsonParser) throws IOException {
        builder.addAllVariables((List<TemplateVariable>) jsonParser.readValueAs(new TypeReference<List<TemplateVariable>>() {
        }));
    }

    public void readPartialPush(JsonParser jsonParser) throws IOException {
        builder.setPushPayload(jsonParser.readValueAs(PartialPushPayload.class));
    }

    @Override
    public TemplateView validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
