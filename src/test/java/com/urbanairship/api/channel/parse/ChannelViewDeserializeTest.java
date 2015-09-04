package com.urbanairship.api.channel.parse;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.urbanairship.api.channel.model.ChannelView;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.common.parse.APIParsingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ChannelViewDeserializeTest {


    private static final ObjectMapper mapper = ChannelObjectMapper.getInstance();

    @Test
    public void testMinimal() throws Exception {
        String json =
            "{" +
                "\"channel_id\" : \"abcdef\"," +
                "\"device_type\" : \"ios\"," +
                "\"installed\" : true," +
                "\"opt_in\" : false," +
                "\"created\" : \"2013-08-08T20:41:06.000Z\"" +
                "}";

        ChannelView channel = mapper.readValue(json, ChannelView.class);
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
                "\"channel_id\" : \"abcdef\"," +
                "\"device_type\" : \"ios\"," +
                "\"installed\" : true," +
                "\"opt_in\" : true," +
                "\"background\" : true," +
                "  \"ios\" : {" +
                "    \"badge\": 0," +
                "    \"quiettime\": {" +
                "      \"start\": \"22:00\"," +
                "      \"end\": \"06:00\"" +
                "    }," +
                "    \"tz\": \"America/Los_Angeles\"" +
                "  }," +
                "\"tags\" : [\"tag1\", \"tag2\"]," +
                "\"tag_groups\" : {" +
                "  \"group1\" : [" +
                "    \"tag1OfGroup1\"," +
                "    \"tag2OfGroup1\"" +
                "  ]," +
                "  \"group2\" : [" +
                "    \"tag1OfGroup2\"," +
                "    \"tag2OfGroup2\"" +
                "  ]" +
                "}," +
                "\"alias\" : \"alias\"," +
                "\"created\" : \"2013-08-08T20:41:06.000Z\"," +
                "\"push_address\" : \"address\"" +
                "}";

        ChannelView channel = mapper.readValue(json, ChannelView.class);

        assertTrue(channel.isOptIn());
        assertTrue(channel.getBackground().isPresent());
        assertTrue(channel.getBackground().get());
        assertEquals(ChannelType.IOS, channel.getChannelType());
        assertTrue(channel.getIosSettings().isPresent());
        assertEquals(0, channel.getIosSettings().get().getBadge());
        assertEquals("22:00", channel.getIosSettings().get().getQuietTime().get().getStart());
        assertEquals("06:00", channel.getIosSettings().get().getQuietTime().get().getEnd());
        assertEquals("America/Los_Angeles", channel.getIosSettings().get().getTimezone().get());
        assertEquals("address", channel.getPushAddress().get());
        assertEquals("alias", channel.getAlias().get());
        ImmutableSet<String> expectedTags = new ImmutableSet.Builder<String>()
            .addAll(Sets.newHashSet("tag1", "tag2")).build();
        assertEquals(expectedTags, channel.getTags());
        ImmutableMap<String, ImmutableSet<String>> expectedTagGroups = new ImmutableMap.Builder<String, ImmutableSet<String>>()
            .put("group1", new ImmutableSet.Builder<String>()
                .addAll(Sets.newHashSet("tag1OfGroup1", "tag2OfGroup1")).build())
            .put("group2", new ImmutableSet.Builder<String>()
                .addAll(Sets.newHashSet("tag1OfGroup2", "tag2OfGroup2")).build())
            .build();
        assertEquals(expectedTagGroups, channel.getTagGroups());
    }

    @Test
    public void testEmptyTags() throws Exception {
        String json =
            "{" +
                "\"channel_id\" : \"abcdef\"," +
                "\"device_type\" : \"ios\"," +
                "\"installed\" : true," +
                "\"opt_in\" : false," +
                "\"created\" : \"2013-08-08T20:41:06.000Z\"," +
                "\"tags\" : []" +
                "}";

        ChannelView channel = mapper.readValue(json, new TypeReference<ChannelView>() {
        });
        Assert.assertTrue(channel.getTags().isEmpty());
    }

    @Test
    public void testEmptyTagGroups() throws Exception {
        String json = "{" +
            "\"channel_id\" : \"abcdef\"," +
            "\"device_type\" : \"ios\"," +
            "\"installed\" : true," +
            "\"opt_in\" : false," +
            "\"created\" : \"2013-08-08T20:41:06.000Z\"," +
            "\"tag_groups\" : {}" +
            "}";

        ChannelView channel = mapper.readValue(json, new TypeReference<ChannelView>() {
        });
        Assert.assertTrue(channel.getTagGroups().isEmpty());
    }

    @Test
    public void testValidChannelDevices() throws Exception {
        for (ChannelType channelType : ChannelType.values()) {
            String json =
                "{" +
                    "\"channel_id\" : \"abcdef\"," +
                    "\"device_type\" : \"" + channelType.getIdentifier() + "\"," +
                    "\"installed\" : true," +
                    "\"created\" : \"2013-08-08T20:41:06.000Z\"," +
                    "\"opt_in\" : false" +
                    "}";

            ChannelView channel = mapper.readValue(json, new TypeReference<ChannelView>() {
            });
            Assert.assertFalse(channel.isOptIn());
            assertEquals(channelType, channel.getChannelType());
            Assert.assertFalse(channel.getAlias().isPresent());
            Assert.assertFalse(channel.getIosSettings().isPresent());
            Assert.assertFalse(channel.getPushAddress().isPresent());
            Assert.assertTrue(channel.getTags().isEmpty());
        }

    }

    @Test(expected = APIParsingException.class)
    public void testUnrecognizedKey() throws Exception {
        String json =
                "{" +
                        "\"device_type\" : \"ios\"," +
                        "\"opt_in\" : false," +
                        "\"badkey\" : \"nonsense\"" +
                        "}";

        mapper.readValue(json, new TypeReference<ChannelView>() {});
    }

    @Test(expected = APIParsingException.class)
    public void testDeviceTypesInvalid() throws Exception {
        String json =
                "{" +
                        "\"device_type\" : \"tizen lol\"," +
                        "\"opt_in\" : false" +
                        "}";

        mapper.readValue(json, new TypeReference<ChannelView>() {});
    }

    @Test(expected = APIParsingException.class)
    public void testDeviceTypesMissing() throws Exception {
        String json =
                "{" +
                        "\"opt_in\" : false" +
                        "}";

        mapper.readValue(json, new TypeReference<ChannelView>() {});
    }

    @Test(expected = APIParsingException.class)
    public void testOptInMissing() throws Exception {
        String json =
                "{" +
                        "\"device_type\" : \"ios\"" +
                        "}";

        mapper.readValue(json, new TypeReference<ChannelView>() {});
    }
}