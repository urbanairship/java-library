package com.urbanairship.api.channel.information.parse;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.urbanairship.api.channel.information.model.ChannelView;
import com.urbanairship.api.channel.information.model.DeviceType;
import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.common.parse.APIParsingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class ChannelViewDeserializeTest {


    private static final ObjectMapper mapper = APIResponseObjectMapper.getInstance();

    @Test
    public void testMinimal() throws Exception {
        String json =
                "{" +
                        "\"channel_id\" : \"abcdef\"," +
                        "\"device_type\" : \"ios\"," +
                        "\"installed\" : true," +
                        "\"opt_in\" : false," +
                        "\"created\" : 12345" +
                        "}";

        ChannelView channel = mapper.readValue(json, new TypeReference<ChannelView>() {
        });
        assertFalse(channel.isOptedIn());
        assertFalse(channel.getBackground().isPresent());
        assertEquals(DeviceType.IOS, channel.getDeviceType());
        assertFalse(channel.getAlias().isPresent());
        assertFalse(channel.getIosSettings().isPresent());
        assertFalse(channel.getPushAddress().isPresent());
        assertTrue(channel.getTags().isEmpty());

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
                        "\"tag_groups\" : {\"group1\" : [\"tag1OfGroup1\", \"tag2OfGroup1\"], \"group2\" : [\"tag1OfGroup2\", \"tag2OfGroup2\"]}," +
                        "\"alias\" : \"alias\"," +
                        "\"created\" : 12345," +
                        "\"push_address\" : \"address\"" +
                        "}";

        ChannelView channel = mapper.readValue(json, new TypeReference<ChannelView>() {
        });
        assertTrue(channel.isOptedIn());
        assertTrue(channel.getBackground().isPresent());
        assertTrue(channel.getBackground().get());
        assertEquals(DeviceType.IOS, channel.getDeviceType());
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
                        "\"created\" : 12345," +
                        "\"tags\" : []" +
                        "}";

        ChannelView channel = mapper.readValue(json, new TypeReference<ChannelView>() {
        });
        assertTrue(channel.getTags().isEmpty());
    }

    @Test
    public void testEmptyTagGroups() throws Exception {
        String json = "{" +
            "\"channel_id\" : \"abcdef\"," +
            "\"device_type\" : \"ios\"," +
            "\"installed\" : true," +
            "\"opt_in\" : false," +
            "\"created\" : 12345," +
            "\"tag_groups\" : {}" +
            "}";

        ChannelView channel = mapper.readValue(json, new TypeReference<ChannelView>() {
        });
        assertTrue(channel.getTagGroups().isEmpty());
    }

    @Test
    public void testValidChannelDevices() throws Exception {
        for (DeviceType deviceType : DeviceType.values()) {
            String json =
                    "{" +
                            "\"channel_id\" : \"abcdef\"," +
                            "\"device_type\" : \"" + deviceType.getIdentifier() + "\"," +
                            "\"installed\" : true," +
                            "\"created\" : 12345," +
                            "\"opt_in\" : false" +
                            "}";

            ChannelView channel = mapper.readValue(json, new TypeReference<ChannelView>() {
            });
            assertFalse(channel.isOptedIn());
            assertEquals(deviceType, channel.getDeviceType());
            assertFalse(channel.getAlias().isPresent());
            assertFalse(channel.getIosSettings().isPresent());
            assertFalse(channel.getPushAddress().isPresent());
            assertTrue(channel.getTags().isEmpty());
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