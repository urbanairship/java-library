package com.urbanairship.api.nameduser.parse;

import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.nameduser.model.NamedUserListingResponse;
import com.urbanairship.api.nameduser.model.NamedUserView;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.Set;

public class NamedUserListingResponseReader implements JsonObjectReader<NamedUserListingResponse> {

    private final NamedUserListingResponse.Builder builder;

    public NamedUserListingResponseReader() {
        this.builder = NamedUserListingResponse.newBuilder();
    }

    public void readOk(JsonParser jsonParser) throws IOException {
        builder.setOk(jsonParser.getBooleanValue());
    }

    public void readNextPage(JsonParser jsonParser) throws IOException {
        builder.setNextPage(jsonParser.readValueAs(String.class));
    }

    public void readNamedUser(JsonParser jsonParser) throws IOException {
        builder.setNamedUserView(jsonParser.readValueAs(NamedUserView.class));
    }

    public void readNamedUsers(JsonParser jsonParser) throws IOException {
        builder.setNamedUserViews(ImmutableSet.copyOf((Set<NamedUserView>) jsonParser.readValueAs(new TypeReference<Set<NamedUserView>>() {
        })));
    }


    @Override
    public NamedUserListingResponse validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception ex) {
            throw new APIParsingException(ex.getMessage());
        }
    }
}
