package com.urbanairship.api.push.parse.notification;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.common.parse.*;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.util.Map;

public class NotificationDeserializer extends JsonDeserializer<Notification> {

    private static final FieldParserRegistry<Notification, NotificationReader> FIELD_PARSERS =
        new MapFieldParserRegistry<Notification, NotificationReader>(
            ImmutableMap.<String, FieldParser<NotificationReader>>builder()
            .put("alert", new FieldParser<NotificationReader>() {
                    @Override
                    public void parse(NotificationReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                        reader.readAlert(jsonParser);
                    }
                })
            .put("wns", new FieldParser<NotificationReader>() {
                    @Override
                    public void parse(NotificationReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                        reader.readPlatformDevicePayloadOverride(DeviceType.WNS, jsonParser, deserializationContext);
                    }
                })
            .put("mpns", new FieldParser<NotificationReader>() {
                    @Override
                    public void parse(NotificationReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                        reader.readPlatformDevicePayloadOverride(DeviceType.MPNS, jsonParser, deserializationContext);
                    }
                })
            .put("ios", new FieldParser<NotificationReader>() {
                    @Override
                    public void parse(NotificationReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                        reader.readPlatformDevicePayloadOverride(DeviceType.IOS, jsonParser, deserializationContext);
                    }
                })
            .put("android", new FieldParser<NotificationReader>() {
                    @Override
                    public void parse(NotificationReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                        reader.readPlatformDevicePayloadOverride(DeviceType.ANDROID, jsonParser, deserializationContext);
                    }
                })
            .put("blackberry", new FieldParser<NotificationReader>() {
                    @Override
                    public void parse(NotificationReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                        reader.readPlatformDevicePayloadOverride(DeviceType.BLACKBERRY, jsonParser, deserializationContext);
                    }
                })
            .put("adm", new FieldParser<NotificationReader>() {
                    @Override
                    public void parse(NotificationReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                        reader.readPlatformDevicePayloadOverride(DeviceType.ADM, jsonParser, deserializationContext);
                    }
                })
            .build());

    private final StandardObjectDeserializer<Notification, ?> deserializer;

    public NotificationDeserializer(final Map<DeviceType, JsonDeserializer<? extends DevicePayloadOverride>> payloadOverridesDeserializersRegistry) {
        deserializer = new StandardObjectDeserializer<Notification, NotificationReader>(
            FIELD_PARSERS,
            new Supplier<NotificationReader>() {
                @Override
                public NotificationReader get() {
                    return new NotificationReader(payloadOverridesDeserializersRegistry);
                }
            }
        );
    }

    @Override
    public Notification deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(parser, deserializationContext);
    }
}
