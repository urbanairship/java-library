/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.parse.notification.wns;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.common.parse.JsonObjectReader;
import com.urbanairship.api.push.model.notification.wns.WNSBinding;
import com.urbanairship.api.push.model.notification.wns.WNSTileData;

import java.io.IOException;
import java.util.List;

public class WNSTileReader implements JsonObjectReader<WNSTileData> {

    private final WNSTileData.Builder builder;
    private final WNSBindingDeserializer bindingDS;

    public WNSTileReader(WNSBindingDeserializer bindingDS) {
        this.builder = WNSTileData.newBuilder();
        this.bindingDS = bindingDS;
    }

    public void readBinding(JsonParser parser, DeserializationContext context) throws IOException {
        List<WNSBinding> bindings = parser.readValueAs(new TypeReference<List<WNSBinding>>() {});
        builder.addAllBindings(bindings);
    }

    @Override
    public WNSTileData validateAndBuild() throws IOException {
        try {
            return builder.build();
        } catch (Exception e) {
            throw new APIParsingException("Tile must contain a valid binding.");
        }
    }
}
