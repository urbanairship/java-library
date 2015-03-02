package com.urbanairship.api.client.parse;

import com.urbanairship.api.channel.information.model.ChannelView;
import com.urbanairship.api.channel.information.model.DeviceType;
import com.urbanairship.api.client.model.APIListAllChannelsResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.*;

public class APIListAllChannelsResponseTest {

    @Test
    public void testAPIListAllChannelsResponse() {

        String fiveresponse = "{\n" +
                "  \"ok\": true,\n" +
                "  \"channels\": [\n" +
                "    {\n" +
                "      \"channel_id\": \"00000000-0000-0000-0000-000000000000\",\n" +
                "      \"device_type\": \"android\",\n" +
                "      \"installed\": false,\n" +
                "      \"opt_in\": false,\n" +
                "      \"push_address\": null,\n" +
                "      \"created\": \"2012-06-05T20:37:37\",\n" +
                "      \"last_registration\": null,\n" +
                "      \"alias\": null,\n" +
                "      \"tags\": [\n" +
                "        \"test01\"\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"channel_id\": \"00662346-9e39-4f5f-80e7-3f8fae58863c\",\n" +
                "      \"device_type\": \"android\",\n" +
                "      \"installed\": true,\n" +
                "      \"opt_in\": true,\n" +
                "      \"background\": true,\n" +
                "      \"push_address\": \"APA91bFPOUF6KNHXjoG0vaQSP4VLXirGDpy0_CRcb6Jhvnrya2bdRmlUoMiJ12JJevjONZzUwFETYa8uzyiE_9WaL3mzZrdjqOv2YuzYlQ_TrXVgo61JmIyw-M_pshIjVvkvtOuZ4MnRJJ_MiQDYwpB4ZhOTMlyqRw\",\n" +
                "      \"created\": \"2014-03-06T18:52:59\",\n" +
                "      \"last_registration\": \"2014-10-07T21:28:35\",\n" +
                "      \"alias\": \"aaron-device\",\n" +
                "      \"tags\": [\n" +
                "        \"aaron-tag\",\n" +
                "        \"rhtgeg\",\n" +
                "        \"tnrvrg\"\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"channel_id\": \"00d174cd-0a31-427e-95c9-52d5785bcd50\",\n" +
                "      \"device_type\": \"ios\",\n" +
                "      \"installed\": true,\n" +
                "      \"opt_in\": true,\n" +
                "      \"background\": true,\n" +
                "      \"push_address\": \"E4EA0D96092A9213BB186BEF66E83EE226401F82B3A77A1AC8217A8FE8ED4614\",\n" +
                "      \"created\": \"2014-07-09T18:08:37\",\n" +
                "      \"last_registration\": \"2014-10-02T01:41:42\",\n" +
                "      \"alias\": null,\n" +
                "      \"tags\": [\n" +
                "        \"version_1.5.0\"\n" +
                "      ],\n" +
                "      \"ios\": {\n" +
                "        \"badge\": 1,\n" +
                "        \"quiettime\": {\n" +
                "          \"start\": \"17:00\",\n" +
                "          \"end\": \"9:00\"\n" +
                "        },\n" +
                "        \"tz\": \"America\\/Los_Angeles\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"channel_id\": \"00d8cb94-eac9-49fb-bad0-29298a06730e\",\n" +
                "      \"device_type\": \"ios\",\n" +
                "      \"installed\": false,\n" +
                "      \"opt_in\": false,\n" +
                "      \"push_address\": \"21F34C9ED37EAF8D7DC43561C07AA398CA5C6F503196C9E8230C50C0959B8653\",\n" +
                "      \"created\": \"2014-02-22T22:48:37\",\n" +
                "      \"last_registration\": null,\n" +
                "      \"alias\": \"iPhone 7,1\",\n" +
                "      \"tags\": [\n" +
                "        \"kablam\",\n" +
                "        \"version_1.3\"\n" +
                "      ],\n" +
                "      \"ios\": {\n" +
                "        \"badge\": 1,\n" +
                "        \"quiettime\": {\n" +
                "          \"start\": null,\n" +
                "          \"end\": null\n" +
                "        },\n" +
                "        \"tz\": null\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"channel_id\": \"01257ecd-8182-41fe-a741-9fed91b993cb\",\n" +
                "      \"device_type\": \"android\",\n" +
                "      \"installed\": false,\n" +
                "      \"opt_in\": false,\n" +
                "      \"push_address\": null,\n" +
                "      \"created\": \"2013-01-25T00:55:05\",\n" +
                "      \"last_registration\": null,\n" +
                "      \"alias\": null,\n" +
                "      \"tags\": [\n" +
                "        \n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"next_page\": \"https:\\/\\/go.urbanairship.com\\/api\\/channels?limit=5&start=0143e4d6-724c-4fc8-bbc6-ca647b8993bf\"\n" +
                "}";


        ObjectMapper mapper = APIResponseObjectMapper.getInstance();

        try {
            APIListAllChannelsResponse response = mapper.readValue(fiveresponse, APIListAllChannelsResponse.class);
            assertTrue(response.getOk());
            assertEquals("https://go.urbanairship.com/api/channels?limit=5&start=0143e4d6-724c-4fc8-bbc6-ca647b8993bf", response.getNextPage().get());
            assertEquals(5, response.getChannelObjects().size());

            ChannelView one = response.getChannelObjects().get(0);
            assertNotNull(one);
            assertFalse(one.getAlias().isPresent());
            assertFalse(one.getBackground().isPresent());
            assertEquals("00000000-0000-0000-0000-000000000000", one.getChannelId());
            assertEquals(1338928657000L, one.getCreatedMillis());
            assertEquals(DeviceType.ANDROID, one.getDeviceType());
            assertFalse(one.getIosSettings().isPresent());
            assertFalse(one.getLastRegistrationMillis().isPresent());
            assertFalse(one.getPushAddress().isPresent());
            assertEquals("[test01]", one.getTags().toString());
            assertFalse(one.isInstalled());
            assertFalse(one.isOptedIn());

            ChannelView two = response.getChannelObjects().get(1);
            assertNotNull(two);
            assertEquals("aaron-device", two.getAlias().get());
            assertTrue(two.getBackground().get());
            assertEquals("00662346-9e39-4f5f-80e7-3f8fae58863c", two.getChannelId());
            assertEquals(1394131979000L, two.getCreatedMillis());
            assertEquals(DeviceType.ANDROID, two.getDeviceType());
            assertFalse(two.getIosSettings().isPresent());
            assertEquals(1412717315000L, two.getLastRegistrationMillis().get().longValue());
            assertEquals("APA91bFPOUF6KNHXjoG0vaQSP4VLXirGDpy0_CRcb6Jhvnrya2bdRmlUoMiJ12JJevjONZzUwFETYa8uzyiE_9WaL3mzZrdjqOv2YuzYlQ_TrXVgo61JmIyw-M_pshIjVvkvtOuZ4MnRJJ_MiQDYwpB4ZhOTMlyqRw", two.getPushAddress().get());
            assertEquals("[aaron-tag, rhtgeg, tnrvrg]", two.getTags().toString());
            assertTrue(two.isInstalled());
            assertTrue(two.isOptedIn());

            ChannelView three = response.getChannelObjects().get(2);
            assertNotNull(three);
            assertFalse(three.getAlias().isPresent());
            assertTrue(three.getBackground().get());
            assertEquals("00d174cd-0a31-427e-95c9-52d5785bcd50", three.getChannelId());
            assertEquals(1404929317000L, three.getCreatedMillis());
            assertEquals(DeviceType.IOS, three.getDeviceType());
            assertEquals("IosSettings{badge=1, quiettime=Optional.of(QuietTime{start='17:00', end='9:00'}), timezone=Optional.of(America/Los_Angeles)}", three.getIosSettings().get().toString());
            assertEquals(1412214102000L, three.getLastRegistrationMillis().get().longValue());
            assertEquals("E4EA0D96092A9213BB186BEF66E83EE226401F82B3A77A1AC8217A8FE8ED4614", three.getPushAddress().get());
            assertEquals("[version_1.5.0]", three.getTags().toString());
            assertTrue(three.isInstalled());
            assertTrue(three.isOptedIn());

            ChannelView four = response.getChannelObjects().get(3);
            assertNotNull(four);
            assertEquals("iPhone 7,1", four.getAlias().get());
            assertFalse(four.getBackground().isPresent());
            assertEquals("00d8cb94-eac9-49fb-bad0-29298a06730e", four.getChannelId());
            assertEquals(1393109317000L, four.getCreatedMillis());
            assertEquals(DeviceType.IOS, four.getDeviceType());
            assertEquals("IosSettings{badge=1, quiettime=Optional.of(QuietTime{start='null', end='null'}), timezone=Optional.absent()}", four.getIosSettings().get().toString());
            assertFalse(four.getLastRegistrationMillis().isPresent());
            assertEquals("21F34C9ED37EAF8D7DC43561C07AA398CA5C6F503196C9E8230C50C0959B8653", four.getPushAddress().get());
            assertEquals("[kablam, version_1.3]", four.getTags().toString());
            assertFalse(four.isInstalled());
            assertFalse(four.isOptedIn());

            ChannelView five = response.getChannelObjects().get(4);
            assertNotNull(five);
            assertFalse(five.getAlias().isPresent());
            assertFalse(five.getBackground().isPresent());
            assertEquals("01257ecd-8182-41fe-a741-9fed91b993cb", five.getChannelId());
            assertEquals(1359075305000L, five.getCreatedMillis());
            assertEquals(DeviceType.ANDROID, five.getDeviceType());
            assertFalse(five.getIosSettings().isPresent());
            assertFalse(five.getLastRegistrationMillis().isPresent());
            assertFalse(five.getPushAddress().isPresent());
            assertEquals("[]", five.getTags().toString());
            assertFalse(five.isInstalled());
            assertFalse(five.isOptedIn());

        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception " + ex.getMessage());
        }
    }
}
