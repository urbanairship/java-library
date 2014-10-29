package com.urbanairship.api.client.parse;

import com.google.common.collect.ImmutableList;
import com.urbanairship.api.client.model.APIListAllSegmentsResponse;
import com.urbanairship.api.client.model.SegmentInformation;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

public class APIListAllSegmentsResponseReader implements JsonObjectReader<APIListAllSegmentsResponse> {


    private final APIListAllSegmentsResponse.Builder builder;

    public APIListAllSegmentsResponseReader() { this.builder = APIListAllSegmentsResponse.newBuilder(); }

    public void readNextPage(JsonParser jsonParser) throws IOException { builder.setNextPage(jsonParser.readValueAs(String.class)); }
    public void readSegments(JsonParser jsonParser) throws IOException { builder.setSegments( (List<SegmentInformation>) jsonParser.readValueAs(new TypeReference<List<SegmentInformation>>() { })); }

    @Override
    public APIListAllSegmentsResponse validateAndBuild() throws IOException {
        try{
            return builder.build();
        }
        catch (Exception ex){
            throw new APIParsingException(ex.getMessage());
        }
    }
}
