package com.urbanairship.api.client.parse;

import com.urbanairship.api.client.model.APILocationResponse;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.location.model.Location;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

public class APILocationResponseReader implements JsonObjectReader<APILocationResponse> {

    private final APILocationResponse.Builder builder;

    public APILocationResponseReader() {
        this.builder = APILocationResponse.newBuilder();
    }

    public void readFeatures(JsonParser jsonParser) throws IOException {
        builder.addAllFeatures( (List<Location>) jsonParser.readValueAs(new TypeReference<List<Location>>() {  }));
    }

    @Override
    public APILocationResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
