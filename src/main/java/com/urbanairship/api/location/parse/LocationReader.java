package com.urbanairship.api.location.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.location.model.Location;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

public class LocationReader implements JsonObjectReader<Location> {

    private final Location.Builder builder;

    public LocationReader() {
        this.builder = Location.newBuilder();
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
    public Location validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }

}
