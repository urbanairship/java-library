/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.staticlists.parse;

import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.staticlists.model.StaticListListingResponse;
import com.urbanairship.api.staticlists.model.StaticListView;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StaticListListingResponseReader implements JsonObjectReader<StaticListListingResponse> {
    private final StaticListListingResponse.Builder builder;

    public StaticListListingResponseReader() {
        this.builder = StaticListListingResponse.newBuilder();
    }

    public void readOk(JsonParser jsonParser) throws IOException {
        builder.setOk(jsonParser.readValueAs(boolean.class));
    }

    public void readStaticListObjects(JsonParser jsonParser) throws IOException {
        builder.addAllStaticLists((List<StaticListView>) jsonParser.readValueAs(new TypeReference<List<StaticListView>>() {
        }));
    }

    @Override
    public StaticListListingResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
