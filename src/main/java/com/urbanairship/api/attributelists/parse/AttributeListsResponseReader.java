/*
 * Copyright (c) 2013-2022. Airship and Contributors
 */

package com.urbanairship.api.attributelists.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.urbanairship.api.attributelists.model.AttributeListsListingResponse;
import com.urbanairship.api.attributelists.model.AttributeListsView;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;

import java.io.IOException;
import java.util.List;

public class AttributeListsResponseReader implements JsonObjectReader<AttributeListsListingResponse> {
    private final AttributeListsListingResponse.Builder builder;

    public AttributeListsResponseReader() {
        this.builder = AttributeListsListingResponse.newBuilder();
    }

    public void readOk(JsonParser jsonParser) throws IOException {
        builder.setOk(jsonParser.readValueAs(boolean.class));
    }

    public void readStaticListObjects(JsonParser jsonParser) throws IOException {
        builder.addAllAttributeLists((List<AttributeListsView>) jsonParser.readValueAs(new TypeReference<List<AttributeListsView>>() {
        }));
    }

    @Override
    public AttributeListsListingResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
