package com.urbanairship.api.push.parse.notification.ios;

import com.urbanairship.api.push.model.notification.ios.IOSBadgeData;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import java.util.Map;
import java.io.IOException;

public class IOSBadgeDataSerializer extends JsonSerializer<IOSBadgeData> {
    @Override
    public void serialize(IOSBadgeData badge, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        switch (badge.getType()) {
          case VALUE:
              jgen.writeNumber(badge.getValue().get());
              break;
          case AUTO:
              jgen.writeString("auto");
              break;
          case INCREMENT:
              jgen.writeString("+" + badge.getValue().get().toString());
              break;
          case DECREMENT:
              jgen.writeString("-" + badge.getValue().get().toString());
              break;
        }
    }
}
