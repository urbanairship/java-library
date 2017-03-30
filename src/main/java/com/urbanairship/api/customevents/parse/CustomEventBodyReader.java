package com.urbanairship.api.customevents.parse;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.MapOfStringsDeserializer;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.customevents.model.CustomEventBody;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class CustomEventBodyReader implements JsonObjectReader<CustomEventBody> {
    private final CustomEventBody.Builder builder;

    public CustomEventBodyReader() {
        this.builder = CustomEventBody.newBuilder();
    }

    public void readName(JsonParser parser) throws IOException {
        builder.setName(StringFieldDeserializer.INSTANCE.deserialize(parser, "name"));
    }

    public void readValue(JsonParser parser) throws IOException {
        builder.setValue(parser.getDecimalValue());
    }

    public void readTransaction(JsonParser parser) throws IOException {
        builder.setTransaction(StringFieldDeserializer.INSTANCE.deserialize(parser, "transaction"));
    }

    public void readInteractionId(JsonParser parser) throws IOException {
        builder.setInteractionId(StringFieldDeserializer.INSTANCE.deserialize(parser, "interaction_id"));
    }

    public void readInteractionType(JsonParser parser) throws IOException {
        builder.setInteractionType(StringFieldDeserializer.INSTANCE.deserialize(parser, "interaction_type"));
    }

    public void readProperties(JsonParser parser) throws IOException {
        builder.addAllPropertyEntries(MapOfStringsDeserializer.INSTANCE.deserialize(parser, "properties"));
    }

    public void readSessionId(JsonParser parser) throws IOException {
        builder.setSessionId(StringFieldDeserializer.INSTANCE.deserialize(parser, "session_id"));
    }

    @Override
    public CustomEventBody validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
