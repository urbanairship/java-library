package com.urbanairship.api.push.parse.notification.web;

import com.fasterxml.jackson.core.JsonParser;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.common.parse.StringFieldDeserializer;
import com.urbanairship.api.push.model.notification.web.WebImage;

import java.io.IOException;

public class WebImageReader implements JsonObjectReader<WebImage> {
    private WebImage.Builder builder = WebImage.newBuilder();

    public WebImageReader() { }

    public void readUrl(JsonParser json) throws IOException {
        builder.setUrl(StringFieldDeserializer.INSTANCE.deserialize(json, "url"));
    }

    @Override
    public WebImage validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
