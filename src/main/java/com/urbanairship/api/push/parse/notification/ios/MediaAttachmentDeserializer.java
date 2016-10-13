package com.urbanairship.api.push.parse.notification.ios;


import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.FieldParser;
import com.urbanairship.api.common.parse.FieldParserRegistry;
import com.urbanairship.api.common.parse.MapFieldParserRegistry;
import com.urbanairship.api.common.parse.StandardObjectDeserializer;
import com.urbanairship.api.push.model.notification.ios.MediaAttachment;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class MediaAttachmentDeserializer extends JsonDeserializer<MediaAttachment> {

    private static final FieldParserRegistry<MediaAttachment, MediaAttachmentReader> FIELD_PARSER = new MapFieldParserRegistry<MediaAttachment, MediaAttachmentReader>(
            ImmutableMap.<String, FieldParser<MediaAttachmentReader>>builder()
            .put("options", new FieldParser<MediaAttachmentReader>() {
                @Override
                public void parse(MediaAttachmentReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readOptions(json, context);
                }
            })
            .put("url", new FieldParser<MediaAttachmentReader>() {
                @Override
                public void parse(MediaAttachmentReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readUrl(json);
                }
            })
            .put("content", new FieldParser<MediaAttachmentReader>() {
                @Override
                public void parse(MediaAttachmentReader reader, JsonParser json, DeserializationContext context) throws IOException {
                    reader.readContent(json, context);
                }
            })
            .build()
    );

    private final StandardObjectDeserializer<MediaAttachment, ?> deserializer;

    public MediaAttachmentDeserializer() {
        deserializer = new StandardObjectDeserializer<MediaAttachment, MediaAttachmentReader>(
                FIELD_PARSER,
                new Supplier<MediaAttachmentReader>() {
                    @Override
                    public MediaAttachmentReader get() {
                        return new MediaAttachmentReader();
                    }
                }
        );
    }

    @Override
    public MediaAttachment deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return deserializer.deserialize(jp, ctxt);
    }
}
