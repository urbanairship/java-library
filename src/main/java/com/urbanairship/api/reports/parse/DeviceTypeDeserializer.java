package com.urbanairship.api.reports.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.reports.model.Android;
import com.urbanairship.api.reports.model.PlatformStats;

import java.io.IOException;
import java.rmi.registry.Registry;

public final class DeviceTypeDeserializer extends JsonDeserializer<Android> {

   private static final FieldParserRegistry<Android, DeviceTypeReader> FIELD_PARSERS = new MapFieldParserRegistry<Android, DeviceTypeReader>(ImmutableMap.<String, FieldParser<DeviceTypeReader>>builder()
      .put("direct", new FieldParser<DeviceTypeReader>() {
         @Override
         public void parse(DeviceTypeReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            reader.readDirect(jsonParser);
         }
      })
       .put("influenced", new FieldParser<DeviceTypeReader>() {
           @Override
           public void parse(DeviceTypeReader reader, JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
               reader.readInfluenced(jsonParser);
           }
       })
       .build()
   );

    private final StandardObjectDeserializer<Android, ?> deserializer;


    public DeviceTypeDeserializer() {
        deserializer = new StandardObjectDeserializer<Android, DeviceTypeReader>(
                FIELD_PARSERS,
                new Supplier<DeviceTypeReader>() {
                    @Override
                    public DeviceTypeReader get() {
                        return new DeviceTypeReader();
                    }
                }
        );
    }

    @Override
    public Android deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserializer.deserialize(jsonParser, deserializationContext);
    }
}
