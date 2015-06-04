/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.notification.wns.WNSToastData;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;

import java.io.IOException;

public class WNSToastReader implements JsonObjectReader<WNSToastData> {

    private final WNSToastData.Builder builder;
    private final WNSBindingDeserializer bindingDS;
    private final WNSAudioDeserializer audioDS;

    public WNSToastReader(WNSBindingDeserializer bindingDS, WNSAudioDeserializer audioDS) {
        this.builder = WNSToastData.newBuilder();
        this.bindingDS = bindingDS;
        this.audioDS = audioDS;
    }

    public void readBinding(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setBinding(bindingDS.deserialize(parser, context));
    }

    public void readDuration(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setDuration(WNSDurationDeserializer.INSTANCE.deserialize(parser, context));
    }

    public void readAudio(JsonParser parser, DeserializationContext context) throws IOException {
        builder.setAudio(audioDS.deserialize(parser, context));
    }

    @Override
    public WNSToastData validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException("Toast must contain a valid binding.");
        }
    }
}
