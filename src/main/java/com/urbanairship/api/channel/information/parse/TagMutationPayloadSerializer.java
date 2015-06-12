package com.urbanairship.api.channel.information.parse;

import com.urbanairship.api.channel.information.model.TagMutationPayload;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class TagMutationPayloadSerializer extends JsonSerializer<TagMutationPayload> {
    @Override
    public void serialize(TagMutationPayload value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

//        jgen.writeObjectFieldStart("audience");
//        for (String member : value.getAudience().keySet()) {
//            ImmutableSet<String> channels = value.getAudience().get(member);
//            jgen.writeObjectField(member, channels);
//        }
//        jgen.writeEndObject();
        jgen.writeObjectField("audience", value.getAudience());

        if (value.getSetTags().isPresent()) {
//            jgen.writeObjectFieldStart("set");
//            for (String tagGroup : value.getSetTags().get().keySet()) {
//                ImmutableSet<String> tags = value.getSetTags().get().get(tagGroup);
//                jgen.writeObjectField(tagGroup, tags);
//            }
//            jgen.writeEndObject();
            jgen.writeObjectField("set", value.getSetTags().get());
        }

        if (value.getAddedTags().isPresent()) {
//            jgen.writeObjectFieldStart("add");
//            for (String tagGroup : value.getAddedTags().get().keySet()) {
//                ImmutableSet<String> tags = value.getAddedTags().get().get(tagGroup);
//                jgen.writeObjectField(tagGroup, tags);
//            }
//            jgen.writeEndObject();
            jgen.writeObjectField("add", value.getAddedTags().get());
        }

        if (value.getRemovedTags().isPresent()) {
//            jgen.writeObjectFieldStart("remove");
//            for (String tagGroup : value.getRemovedTags().get().keySet()) {
//                ImmutableSet<String> tags = value.getRemovedTags().get().get(tagGroup);
//                jgen.writeObjectField(tagGroup, tags);
//            }
//            jgen.writeEndObject();
            jgen.writeObjectField("remove", value.getRemovedTags().get());
        }

        jgen.writeEndObject();
    }
}
