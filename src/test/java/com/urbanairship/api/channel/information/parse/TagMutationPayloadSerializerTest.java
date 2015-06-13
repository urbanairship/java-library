package com.urbanairship.api.channel.information.parse;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.channel.information.model.TagMutationPayload;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TagMutationPayloadSerializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testAddTags() throws Exception {
        String iosChannel1 = UUID.randomUUID().toString();
        String iosChannel2 = UUID.randomUUID().toString();
        String androidChannel = UUID.randomUUID().toString();

        String expected = "{" +
              "\"audience\":{" +
                "\"ios_channel\":[\"" + iosChannel1+ "\",\"" + iosChannel2 +"\"]," +
                "\"android_channel\":[\"" + androidChannel + "\"]" +
              "}," +
              "\"add\":{" +
                "\"tag_group1\":[\"tag1\",\"tag2\",\"tag3\"]," +
                "\"tag_group2\":[\"tag1\",\"tag2\",\"tag3\"]," +
                "\"tag_group3\":[\"tag1\",\"tag2\",\"tag3\"]" +
              "}" +
            "}";

        ImmutableMap<String, ImmutableSet<String>> audience = ImmutableMap.<String, ImmutableSet<String>>builder()
            .put("ios_channel", ImmutableSet.of(iosChannel1, iosChannel2))
            .put("android_channel", ImmutableSet.of(androidChannel))
            .build();

        TagMutationPayload payload = TagMutationPayload.newBuilder()
            .setAudience(audience)
            .addTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .addTags("tag_group2", ImmutableSet.of("tag1", "tag2", "tag3"))
            .addTags("tag_group3", ImmutableSet.of("tag1", "tag2", "tag3"))
            .build();

        String json = mapper.writeValueAsString(payload);
        assertEquals(expected, json);
    }

    @Test
    public void testRemoveTags() throws Exception {
        String iosChannel1 = UUID.randomUUID().toString();
        String iosChannel2 = UUID.randomUUID().toString();
        String androidChannel = UUID.randomUUID().toString();

        String expected = "{" +
              "\"audience\":{" +
                "\"ios_channel\":[\"" + iosChannel1+ "\",\"" + iosChannel2 +"\"]," +
                "\"android_channel\":[\"" + androidChannel + "\"]" +
              "}," +
              "\"remove\":{" +
                "\"tag_group1\":[\"tag1\",\"tag2\",\"tag3\"]," +
                "\"tag_group2\":[\"tag1\",\"tag2\",\"tag3\"]," +
                "\"tag_group3\":[\"tag1\",\"tag2\",\"tag3\"]" +
              "}" +
            "}";

        ImmutableMap<String, ImmutableSet<String>> audience = ImmutableMap.<String, ImmutableSet<String>>builder()
            .put("ios_channel", ImmutableSet.of(iosChannel1, iosChannel2))
            .put("android_channel", ImmutableSet.of(androidChannel))
            .build();

        TagMutationPayload payload = TagMutationPayload.newBuilder()
            .setAudience(audience)
            .removeTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .removeTags("tag_group2", ImmutableSet.of("tag1", "tag2", "tag3"))
            .removeTags("tag_group3", ImmutableSet.of("tag1", "tag2", "tag3"))
            .build();

        String json = mapper.writeValueAsString(payload);
        assertEquals(expected, json);
    }

    @Test
    public void testSetTags() throws Exception {
        String iosChannel1 = UUID.randomUUID().toString();
        String iosChannel2 = UUID.randomUUID().toString();
        String androidChannel = UUID.randomUUID().toString();

        String expected = "{" +
              "\"audience\":{" +
                "\"ios_channel\":[\"" + iosChannel1+ "\",\"" + iosChannel2 +"\"]," +
                "\"android_channel\":[\"" + androidChannel + "\"]" +
              "}," +
              "\"set\":{" +
                "\"tag_group1\":[\"tag1\",\"tag2\",\"tag3\"]," +
                "\"tag_group2\":[\"tag1\",\"tag2\",\"tag3\"]," +
                "\"tag_group3\":[\"tag1\",\"tag2\",\"tag3\"]" +
              "}" +
            "}";

        ImmutableMap<String, ImmutableSet<String>> audience = ImmutableMap.<String, ImmutableSet<String>>builder()
            .put("ios_channel", ImmutableSet.of(iosChannel1, iosChannel2))
            .put("android_channel", ImmutableSet.of(androidChannel))
            .build();

        TagMutationPayload payload = TagMutationPayload.newBuilder()
            .setAudience(audience)
            .setTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .setTags("tag_group2", ImmutableSet.of("tag1", "tag2", "tag3"))
            .setTags("tag_group3", ImmutableSet.of("tag1", "tag2", "tag3"))
            .build();

        String json = mapper.writeValueAsString(payload);
        assertEquals(expected, json);
    }

    @Test
    public void testAddAndRemoveTags() throws Exception {
        String iosChannel1 = UUID.randomUUID().toString();
        String iosChannel2 = UUID.randomUUID().toString();
        String androidChannel = UUID.randomUUID().toString();

        String expected = "{" +
              "\"audience\":{" +
                "\"ios_channel\":[\"" + iosChannel1+ "\",\"" + iosChannel2 +"\"]," +
                "\"android_channel\":[\"" + androidChannel + "\"]" +
              "}," +
              "\"add\":{" +
                "\"tag_group1\":[\"tag1\",\"tag2\",\"tag3\"]," +
                "\"tag_group2\":[\"tag1\",\"tag2\",\"tag3\"]," +
                "\"tag_group3\":[\"tag1\",\"tag2\",\"tag3\"]" +
              "}," +
              "\"remove\":{" +
                "\"tag_group1\":[\"tag4\",\"tag5\",\"tag6\"]," +
                "\"tag_group2\":[\"tag4\",\"tag5\",\"tag6\"]," +
                "\"tag_group3\":[\"tag4\",\"tag5\",\"tag6\"]" +
              "}" +
            "}";

        ImmutableMap<String, ImmutableSet<String>> audience = ImmutableMap.<String, ImmutableSet<String>>builder()
            .put("ios_channel", ImmutableSet.of(iosChannel1, iosChannel2))
            .put("android_channel", ImmutableSet.of(androidChannel))
            .build();

        TagMutationPayload payload = TagMutationPayload.newBuilder()
            .setAudience(audience)
            .addTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .addTags("tag_group2", ImmutableSet.of("tag1", "tag2", "tag3"))
            .addTags("tag_group3", ImmutableSet.of("tag1", "tag2", "tag3"))
            .removeTags("tag_group1", ImmutableSet.of("tag4", "tag5", "tag6"))
            .removeTags("tag_group2", ImmutableSet.of("tag4", "tag5", "tag6"))
            .removeTags("tag_group3", ImmutableSet.of("tag4", "tag5", "tag6"))
            .build();

        String json = mapper.writeValueAsString(payload);
        assertEquals(expected, json);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddAndSetTags() throws Exception {
        String iosChannel1 = UUID.randomUUID().toString();

        ImmutableMap<String, ImmutableSet<String>> audience = ImmutableMap.<String, ImmutableSet<String>>builder()
            .put("ios_channel", ImmutableSet.of(iosChannel1))
            .build();

        TagMutationPayload.newBuilder()
            .setAudience(audience)
            .addTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .setTags("tag_group1", ImmutableSet.of("tag4", "tag5", "tag6"))
            .build();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testRemoveAndSetTags() throws Exception {
        String iosChannel1 = UUID.randomUUID().toString();

        ImmutableMap<String, ImmutableSet<String>> audience = ImmutableMap.<String, ImmutableSet<String>>builder()
            .put("ios_channel", ImmutableSet.of(iosChannel1))
            .build();

        TagMutationPayload.newBuilder()
            .setAudience(audience)
            .removeTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .setTags("tag_group1", ImmutableSet.of("tag4", "tag5", "tag6"))
            .build();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNoTagMutations() throws Exception {
        String iosChannel1 = UUID.randomUUID().toString();

        ImmutableMap<String, ImmutableSet<String>> audience = ImmutableMap.<String, ImmutableSet<String>>builder()
            .put("ios_channel", ImmutableSet.of(iosChannel1))
            .build();

        TagMutationPayload.newBuilder()
            .setAudience(audience)
            .build();
    }
}
