package com.urbanairship.api.channel.information.parse;

import com.google.common.collect.Sets;
import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.channel.information.model.ChannelView;
import com.urbanairship.api.channel.information.model.DeviceType;
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

        ChannelView channel = mapper.readValue(json, new TypeReference<ChannelView>() {});
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
                        "\"ios\" : { }," +
                        "\"tags\" : [\"derper\", \"derpest\"]," +
                        "\"set_tags\" : true," +
                        "\"alias\" : \"herp\"," +
                        "\"created\" : 12345," +
                        "\"push_address\" : \"TWERK\"" +
                        "}";

        ChannelView channel = mapper.readValue(json, new TypeReference<ChannelView>() {});
        assertTrue(channel.isOptedIn());
        assertTrue(channel.getBackground().isPresent());
        assertTrue(channel.getBackground().get());
        assertEquals(DeviceType.IOS, channel.getDeviceType());
        assertTrue(channel.getIosSettings().isPresent());
        assertEquals("TWERK", channel.getPushAddress().get());
        assertEquals("herp", channel.getAlias().get());
        assertEquals(Sets.newHashSet("derper", "derpest"), channel.getTags());
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

        ChannelView channel = mapper.readValue(json, new TypeReference<ChannelView>() {});
        assertTrue(channel.getTags().isEmpty());
    }

    @Test
    public void testValidChannelDevices() throws Exception {
        for (DeviceType deviceType: DeviceType.values()) {
            String json =
                    "{" +
                            "\"channel_id\" : \"abcdef\"," +
                            "\"device_type\" : \"" + deviceType.getIdentifier() + "\"," +
                            "\"installed\" : true," +
                            "\"created\" : 12345," +
                            "\"opt_in\" : false" +
                            "}";

            ChannelView channel = mapper.readValue(json, new TypeReference<ChannelView>() {});
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

        ChannelView channel = mapper.readValue(json, new TypeReference<ChannelView>() {});
    }

    @Test(expected=APIParsingException.class)
    public void testDeviceTypesInvalid() throws Exception {
        String json =
                "{" +
                        "\"device_type\" : \"tizen lol\"," +
                        "\"opt_in\" : false" +
                        "}";

        ChannelView channel = mapper.readValue(json, new TypeReference<ChannelView>() {});
    }

    @Test(expected=APIParsingException.class)
    public void testDeviceTypesMissing() throws Exception {
        String json =
                "{" +
                        "\"opt_in\" : false" +
                        "}";

        ChannelView channel = mapper.readValue(json, new TypeReference<ChannelView>() {});
    }

    @Test(expected=APIParsingException.class)
    public void testOptInMissing() throws Exception {
        String json =
                "{" +
                        "\"device_type\" : \"ios\"" +
                        "}";

        ChannelView channel = mapper.readValue(json, new TypeReference<ChannelView>() {});
    }
}