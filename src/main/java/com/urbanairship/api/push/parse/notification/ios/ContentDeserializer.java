package com.urbanairship.api.push.parse.notification.ios;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.ios.Content;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

/**
 * Created by devinsmythe on 10/13/16.
 */
public class ContentDeserializer extends JsonDeserializer<Content> {

    private static final FieldParserRegistry<Content, ContentReader> FIELD_PARSER = new MapFieldParserRegistry<Content, ContentReader>(
            ImmutableMap.<String, FieldParser<ContentReader>>builder()
            .put("body", new FieldParser<ContentReader>() {
                @Override
                public void parse(ContentReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readBody(json, context);
                }
            })
            .put("title", new FieldParser<ContentReader>() {
                @Override
                public void parse(ContentReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readTitle(json, context);
                }
            })
            .put("subtitle", new FieldParser<ContentReader>() {
                @Override
                public void parse(ContentReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readSubtitle(json, context);
                }
            })
            .build()
    );

    private final StandardObjectDeserializer<Content, ?> deserializer;

    public ContentDeserializer() {
        deserializer = new StandardObjectDeserializer<Content, ContentReader>(
                FIELD_PARSER,
                new Supplier<ContentReader>() {
                    @Override
                    public ContentReader get() {
                        return new ContentReader();
                    }
                }
        );
    }

    @Override
    public Content deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return unwrappingDeserializer().deserialize(jp, ctxt);
    }
}
