package com.urbanairship.api.experiments.parse;


import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.experiments.model.ExperimentResponse;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

public class ExperimentResponseReader implements JsonObjectReader<ExperimentResponse> {

    private final ExperimentResponse.Builder builder;

    public ExperimentResponseReader() {
        this.builder = ExperimentResponse.newBuilder();
    }

    public void readOperationId(JsonParser jsonParser) throws IOException {
        builder.setOperationId(jsonParser.readValueAs(String.class));
    }

    public void readPushIds(JsonParser jsonParser) throws IOException {
        List<String> list = jsonParser.readValueAs(new TypeReference<List<String>>() {});
        builder.addAllPushIds(list);
    }

    public void readOk(JsonParser jsonParser) throws IOException {
        builder.setOk(jsonParser.getBooleanValue());
    }

    public void readExperimentId(JsonParser jsonParser) throws IOException {
        builder.setExperimentId(jsonParser.readValueAs(String.class));
    }

    @Override
    public ExperimentResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
