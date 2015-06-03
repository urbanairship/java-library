package com.urbanairship.api.push.parse.notification;

import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.actions.Actions;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.Map;

public class InteractiveReader implements JsonObjectReader<Interactive> {

    private final Interactive.Builder builder;

    public InteractiveReader() {
        this.builder = Interactive.newBuilder();
    }

    public void readType(JsonParser parser) throws IOException {
        builder.setType(parser.readValueAs(String.class));
    }

    public void readButtonActions(JsonParser parser) throws IOException {
        Map<String, Actions> actionsMap = parser.readValueAs(new TypeReference<Map<String, Actions>>() {});
        ImmutableMap<String, Actions> actionsImmutableMap = actionsMap == null ? null : ImmutableMap.copyOf(actionsMap);
        builder.setButtonActions(actionsImmutableMap);
    }

    @Override
    public Interactive validateAndBuild() throws IOException {
        try {
            return builder.build();
        }
        catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }
}
