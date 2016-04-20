package com.urbanairship.api.tag;

import com.google.common.collect.Lists;
import com.urbanairship.api.push.parse.PushObjectMapper;
import com.urbanairship.api.tag.model.AddRemoveNamedUserFromTagPayload;
import com.urbanairship.api.tag.model.AddRemoveSet;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class AddRemoveNamedUserFromTagPayloadSerializerTest {

    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testSerialization() throws Exception {

        AddRemoveSet ar = AddRemoveSet.newBuilder()
                .add("user-1")
                .add("user-2")
                .remove("device3")
                .build();

        AddRemoveNamedUserFromTagPayload addNamedUserTags = AddRemoveNamedUserFromTagPayload.newBuilder()
                .setAudienceName("named_user_id")
                .addAudience(Lists.newArrayList("user-1", "user-2", "user-3"))
                .addTagsToTagGroup("crm", Lists.newArrayList("tag1", "tag2", "tag3"))
                .addTagsToTagGroup("loyalty", Lists.newArrayList("tag1", "tag4", "tag5"))
                .removeTagsFromTagGroup("loyalty", Lists.newArrayList("tag6", "tag7"))
                .build();

        String json = MAPPER.writeValueAsString(addNamedUserTags);
        String expectedJson = "{" +
                "\"audience\":{" +
                "\"named_user_id\":[\"user-1\",\"user-2\",\"user-3\"]" +
                "}," +
                "\"add\":{" +
                "\"loyalty\":[\"tag1\",\"tag4\",\"tag5\"]," +
                "\"crm\":[\"tag1\",\"tag2\",\"tag3\"]" +
                "}," +
                "\"remove\":{" +
                "\"loyalty\":[\"tag6\",\"tag7\"]" +
                "}" +
                "}";

        assertEquals(expectedJson, json);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoAudienceNamePrecondtion() throws Exception {


        AddRemoveNamedUserFromTagPayload addNamedUserTags = AddRemoveNamedUserFromTagPayload.newBuilder()

                .addAudience(Lists.newArrayList("user-1", "user-2", "user-3"))
                .addTagsToTagGroup("crm", Lists.newArrayList("tag1", "tag2", "tag3"))
                .addTagsToTagGroup("loyalty", Lists.newArrayList("tag1", "tag4", "tag5"))
                .removeTagsFromTagGroup("loyalty", Lists.newArrayList("tag6", "tag7"))
                .build();

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoAudiencePrecondtion() throws Exception {

        AddRemoveNamedUserFromTagPayload addNamedUserTags = AddRemoveNamedUserFromTagPayload.newBuilder()
                .setAudienceName("named_user_id")
                .addTagsToTagGroup("crm", Lists.newArrayList("tag1", "tag2", "tag3"))
                .addTagsToTagGroup("loyalty", Lists.newArrayList("tag1", "tag4", "tag5"))
                .removeTagsFromTagGroup("loyalty", Lists.newArrayList("tag6", "tag7"))
                .build();

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoAddedOrRemovedTags() throws Exception {

        AddRemoveNamedUserFromTagPayload addNamedUserTags = AddRemoveNamedUserFromTagPayload.newBuilder()
                .setAudienceName("named_user_id")

                .build();

    }
}
