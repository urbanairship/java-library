package com.urbanairship.api.nameduser;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.channel.model.ChannelView;
import com.urbanairship.api.nameduser.model.NamedUserView;
import com.urbanairship.api.nameduser.parse.NamedUserObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class NamedUserViewTest {

    private final ObjectMapper mapper = NamedUserObjectMapper.getInstance();

    @Test
    public void testMinimal() throws Exception {
        String json =
            "{" +
                "\"named_user_id\": \"user-id-1234\"," +
                "\"tags\": {" +
                    "\"crm\": [\"tag1\", \"tag2\"]" +
                "}," +
                "\"channels\": [" +
                    "{" +
                        "\"channel_id\" : \"abcdef\"," +
                        "\"device_type\" : \"ios\"," +
                        "\"installed\" : true," +
                        "\"opt_in\" : false," +
                        "\"created\" : \"2013-08-08T20:41:06.000Z\"" +
                    "}" +
                "]" +
            "}";

        NamedUserView namedUserView = mapper.readValue(json, NamedUserView.class);
        assertEquals(namedUserView.getNamedUserId(), "user-id-1234");
        assertTrue(namedUserView.getNamedUserTags().containsKey("crm"));
        Set<String> tags = new HashSet<String>();
        tags.add("tag1");
        tags.add("tag2");
        assertEquals(namedUserView.getNamedUserTags().get("crm"), tags);

        ChannelView channel = namedUserView.getChannelViews().iterator().next();
        Assert.assertFalse(channel.isOptIn());
        Assert.assertFalse(channel.getBackground().isPresent());
        assertEquals(ChannelType.IOS, channel.getChannelType());
        Assert.assertFalse(channel.getAlias().isPresent());
        Assert.assertFalse(channel.getIosSettings().isPresent());
        Assert.assertFalse(channel.getPushAddress().isPresent());
        Assert.assertTrue(channel.getTags().isEmpty());

    }

    @Test
    public void testMaximal() throws Exception {
        String json =
            "{" +
                "\"named_user_id\": \"user-id-1234\"," +
                "\"tags\": {" +
                    "\"crm\": [\"tag1\", \"tag2\"]" +
                "}," +
                "\"channels\": [" +
                    "{" +
                        "\"channel_id\" : \"abcdef\"," +
                        "\"device_type\" : \"ios\"," +
                        "\"installed\" : true," +
                        "\"opt_in\" : true," +
                        "\"background\" : true," +
                        "\"ios\" : {" +
                            "\"badge\": 0," +
                            "\"quiettime\": {" +
                                "\"start\": \"22:00\"," +
                                "\"end\": \"06:00\"" +
                            "}," +
                            "\"tz\": \"America/Los_Angeles\"" +
                        "}," +
                        "\"tags\" : [\"tag1\", \"tag2\"]," +
                        "\"tag_groups\" : {" +
                            "\"group1\" : [" +
                                "\"tag1OfGroup1\"," +
                                "\"tag2OfGroup1\"" +
                            "]," +
                            "\"group2\" : [" +
                                "\"tag1OfGroup2\"," +
                                "\"tag2OfGroup2\"" +
                            "]" +
                        "}," +
                        "\"alias\" : \"alias\"," +
                        "\"created\" : \"2013-08-08T20:41:06.000Z\"," +
                        "\"push_address\" : \"address\"" +
                    "}," +
                    "{" +
                        "\"channel_id\" : \"abcdef\"," +
                        "\"device_type\" : \"ios\"," +
                        "\"installed\" : true," +
                        "\"opt_in\" : false," +
                        "\"created\" : \"2013-08-08T20:41:06.000Z\"" +
                    "}" +
                "]" +
            "}";
        NamedUserView namedUserView =  mapper.readValue(json, NamedUserView.class);
        assertEquals(namedUserView.getNamedUserId(), "user-id-1234");
        assertTrue(namedUserView.getNamedUserTags().containsKey("crm"));
        Set<String> tags = new HashSet<String>();
        tags.add("tag1");
        tags.add("tag2");
        assertEquals(namedUserView.getNamedUserTags().get("crm"), tags);

        ChannelView firstChannel;
        ChannelView secondChannel;
        if (namedUserView.getChannelViews().asList().get(1).isOptIn()) {
            firstChannel = namedUserView.getChannelViews().asList().get(1);
            secondChannel = namedUserView.getChannelViews().asList().get(0);
        } else {
            firstChannel = namedUserView.getChannelViews().asList().get(0);
            secondChannel = namedUserView.getChannelViews().asList().get(1);
        }

        assertTrue(firstChannel.isOptIn());
        assertTrue(firstChannel.getBackground().isPresent());
        assertTrue(firstChannel.getBackground().get());
        assertEquals(ChannelType.IOS, firstChannel.getChannelType());
        assertTrue(firstChannel.getIosSettings().isPresent());
        assertEquals(0, firstChannel.getIosSettings().get().getBadge());
        assertEquals("22:00", firstChannel.getIosSettings().get().getQuietTime().get().getStart());
        assertEquals("06:00", firstChannel.getIosSettings().get().getQuietTime().get().getEnd());
        assertEquals("America/Los_Angeles", firstChannel.getIosSettings().get().getTimezone().get());
        assertEquals("address", firstChannel.getPushAddress().get());
        assertEquals("alias", firstChannel.getAlias().get());
        ImmutableSet<String> expectedTags = new ImmutableSet.Builder<String>()
            .addAll(Sets.newHashSet("tag1", "tag2")).build();
        assertEquals(expectedTags, firstChannel.getTags());
        ImmutableMap<String, ImmutableSet<String>> expectedTagGroups = new ImmutableMap.Builder<String, ImmutableSet<String>>()
            .put("group1", new ImmutableSet.Builder<String>()
                .addAll(Sets.newHashSet("tag1OfGroup1", "tag2OfGroup1")).build())
            .put("group2", new ImmutableSet.Builder<String>()
                .addAll(Sets.newHashSet("tag1OfGroup2", "tag2OfGroup2")).build())
            .build();
        assertEquals(expectedTagGroups, firstChannel.getTagGroups());
        Assert.assertFalse(secondChannel.isOptIn());
        Assert.assertFalse(secondChannel.getBackground().isPresent());
        assertEquals(ChannelType.IOS, secondChannel.getChannelType());
        Assert.assertFalse(secondChannel.getAlias().isPresent());
        Assert.assertFalse(secondChannel.getIosSettings().isPresent());
        Assert.assertFalse(secondChannel.getPushAddress().isPresent());
        Assert.assertTrue(secondChannel.getTags().isEmpty());

    }

}
