/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.location.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.location.model.LocationView;

import java.io.IOException;
import java.util.List;

public class LocationViewReader implements JsonObjectReader<LocationView> {

    private final LocationView.Builder builder;

    public LocationViewReader() {
        this.builder = LocationView.newBuilder();
    }

    public void readBounds(JsonParser jsonParser) throws IOException {
        builder.setBounds((List<Double>) jsonParser.readValueAs(new TypeReference<List<Double>>() {
        }));
    }

    public void readCentroid(JsonParser jsonParser) throws IOException {
        builder.setCentroid((List<Double>) jsonParser.readValueAs(new TypeReference<List<Double>>() {
        }));
    }

    public void readId(JsonParser jsonParser) throws IOException {
        builder.setLocationId(jsonParser.readValueAs(String.class));
    }

    public void readPropertyJSONNode(JsonParser jsonParser) throws IOException {
        JsonNode jsonNode = jsonParser.readValueAsTree();
        builder.setPropertiesNode(jsonNode);
    }

    public void readType(JsonParser jsonParser) throws IOException {
        builder.setLocationType(jsonParser.readValueAs(String.class));
    }

    @Override
    public LocationView validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }

}
