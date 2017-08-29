package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.Display;
import com.urbanairship.api.push.model.InApp;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.actions.Actions;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Map;


public class InAppReader implements JsonObjectReader<InApp> {

    private final InApp.Builder builder;

    public InAppReader() {
        this.builder = InApp.newBuilder();
    }

    public void readAlert(JsonParser parser) throws IOException {
        builder.setAlert(parser.readValueAs(String.class));
    }

    public void readExpiry(JsonParser parser) throws IOException {
        builder.setExpiry(parser.readValueAs(DateTime.class));
    }

    public void readDisplay(JsonParser parser) throws IOException {
        builder.setDisplay(parser.readValueAs(Display.class));
    }

    public void readActions(JsonParser parser) throws IOException {
        builder.setActions(parser.readValueAs(Actions.class));
    }

    public void readInteractive(JsonParser parser) throws IOException {
        builder.setInteractive(parser.readValueAs(Interactive.class));
    }

    public void readAllExtras(JsonParser parser) throws IOException {
        Map<String, String> mutableExtras = parser.readValueAs(new TypeReference<Map<String, String>>() {});
        ImmutableMap<String, String> extras = immutableMapConverter(mutableExtras);
        builder.addAllExtras(extras);
    }

    @Override
    public InApp validateAndBuild() throws IOException {
        try {
            return builder.build();
        }
        catch (Exception e) {
            throw new APIParsingException(e.getMessage());
        }
    }

    private static ImmutableMap<String, String> immutableMapConverter(Map<String, String> map) {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.put(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }


}
