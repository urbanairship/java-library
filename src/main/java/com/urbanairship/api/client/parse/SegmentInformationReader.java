package com.urbanairship.api.client.parse;

import com.urbanairship.api.client.model.SegmentInformation;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class SegmentInformationReader implements JsonObjectReader<SegmentInformation> {

    private final SegmentInformation.Builder builder;

    public SegmentInformationReader() { this.builder = SegmentInformation.newBuilder(); }

    public void readCreationDate(JsonParser jsonParser) throws IOException { builder.setCreationDate(jsonParser.readValueAs(Number.class).longValue()); }
    public void readDisplayName(JsonParser jsonParser) throws IOException { builder.setDisplayName(jsonParser.readValueAs(String.class)); }
    public void readId(JsonParser jsonParser) throws IOException { builder.setId(jsonParser.readValueAs(String.class)); }
    public void readModificationDate(JsonParser jsonParser) throws IOException { builder.setModificationDate(jsonParser.readValueAs(Number.class).longValue()); }

    @Override
    public SegmentInformation validateAndBuild() throws IOException {
        try{
            return builder.build();
        }
        catch (Exception ex){
            throw new APIParsingException(ex.getMessage());
        }
    }
}
