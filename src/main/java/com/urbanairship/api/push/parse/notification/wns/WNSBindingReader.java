package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.model.notification.wns.WNSBinding;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.Platform;
import com.urbanairship.api.push.parse.*;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Set;

public class WNSBindingReader implements JsonObjectReader<WNSBinding> {

    private final WNSBinding.Builder builder;

    public WNSBindingReader() {
        this.builder = WNSBinding.newBuilder();
    }

    public void readTemplate(JsonParser parser) throws IOException {
        builder.setTemplate(StringFieldDeserializer.INSTANCE.deserialize(parser, "template"));
    }

    public void readVersion(JsonParser parser) throws IOException {
        builder.setVersion(IntFieldDeserializer.INSTANCE.deserialize(parser, "version"));
    }

    public void readFallback(JsonParser parser) throws IOException {
        builder.setFallback(StringFieldDeserializer.INSTANCE.deserialize(parser, "fallback"));
    }

    public void readLang(JsonParser parser) throws IOException {
        builder.setLang(StringFieldDeserializer.INSTANCE.deserialize(parser, "lang"));
    }

    public void readBaseUri(JsonParser parser) throws IOException {
        builder.setBaseUri(StringFieldDeserializer.INSTANCE.deserialize(parser, "base_uri"));
    }

    public void readAddImageQuery(JsonParser parser) throws IOException {
        builder.setAddImageQuery(BooleanFieldDeserializer.INSTANCE.deserialize(parser, "add_image_query"));
    }

    public void readImage(JsonParser parser) throws IOException {
        builder.addAllImages(ListOfStringsDeserializer.INSTANCE.deserialize(parser, "image"));
    }

    public void readText(JsonParser parser) throws IOException {
        builder.addAllText(ListOfStringsDeserializer.INSTANCE.deserialize(parser, "text"));
    }

    @Override
    public WNSBinding validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException(e.getMessage(), e);
        }
    }
}
