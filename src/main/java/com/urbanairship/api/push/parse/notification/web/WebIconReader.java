package com.urbanairship.api.push.parse.notification.web;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.notification.web.WebIcon;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;

public class WebIconReader implements JsonObjectReader<WebIcon> {

    private final WebIcon.Builder builder;

    public WebIconReader() {
        this.builder = WebIcon.newBuilder();
    }

    public WebIcon validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }

    public void readUrl(JsonParser parser) throws IOException {
        builder.setUrl(StringFieldDeserializer.INSTANCE.deserialize(parser, "url"));
    }
}
