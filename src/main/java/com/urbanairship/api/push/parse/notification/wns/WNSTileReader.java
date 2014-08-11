package com.urbanairship.api.push.parse.notification.wns;

import com.urbanairship.api.push.model.notification.wns.WNSTileData;
import com.urbanairship.api.push.model.notification.wns.WNSBinding;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.Platform;
import com.urbanairship.api.push.parse.*;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jackson.map.DeserializationContext;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Set;
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
