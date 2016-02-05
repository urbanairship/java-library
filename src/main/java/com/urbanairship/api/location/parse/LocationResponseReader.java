/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.location.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.location.model.LocationView;
import com.urbanairship.api.location.model.LocationResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

public class LocationResponseReader implements JsonObjectReader<LocationResponse> {

    private final LocationResponse.Builder builder;

    public LocationResponseReader() {
        this.builder = LocationResponse.newBuilder();
    }

    public void readFeatures(JsonParser jsonParser) throws IOException {
        builder.addAllFeatures((List<LocationView>) jsonParser.readValueAs(new TypeReference<List<LocationView>>() {
        }));
    }

    @Override
    public LocationResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
