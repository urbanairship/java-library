package com.urbanairship.api.channel;

import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.channel.model.ChannelResponse;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.channel.model.ChannelView;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ChannelResponseTest {

    ObjectMapper MAPPER = ChannelObjectMapper.getInstance();

    @Test
    public void testAPIListAllChannelsResponse() {

        String sixresponse = "{\n" +
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
            "      ],\n" +
            "      \"tag_groups\": {\n" +
            "        \"testGroup01\" : [\n" +
            "          \"testGroup01Tag01\"\n" +
            "        ]\n" +
            "      }\n" +
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
            "      ],\n" +
            "      \"tag_groups\": {\n" +
            "        \"testGroup02\" : [\n" +
            "          \"testGroup02Tag01\",\n" +
            "          \"testGroup02Tag02\"\n" +
            "        ]\n" +
            "      }\n" +
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
            "      \"tag_groups\": {},\n" +
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
            "      \"tag_groups\": {\n" +
            "        \"testGroup03\": [\n" +
            "          \"testGroup03Tag01\",\n" +
            "          \"testGroup03Tag02\",\n" +
            "          \"testGroup03Tag03\"\n" +
            "        ],\n" +
            "        \"testGroup04\": [\n" +
            "          \"testGroup04Tag01\"\n" +
            "        ]\n" +
            "      },\n" +
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
            "      ],\n" +
            "      \"tag_groups\": {}\n" +
            "    },\n" +
            "    {\n" +
            "        \"channel_id\": \"f82d3723-09d0-4390-b0ff-690485685e3e\",\n" +
            "        \"device_type\": \"web\",\n" +
            "        \"installed\": true,\n" +
            "        \"push_address\": \"webpushaddress\",\n" +
            "        \"named_user_id\": null,\n" +
            "        \"alias\": null,\n" +
            "        \"tags\": [\n" +
            "            \"test1\"\n" +
            "        ],\n" +
            "        \"tag_groups\": {\n" +
            "            \"ua_channel_type\": [\n" +
            "                \"web\"\n" +
            "            ],\n" +
            "            \"ua_web_sdk_version\": [\n" +
            "                \"0.0.1\"\n" +
            "            ],\n" +
            "            \"ua_browser_name\": [\n" +
            "                \"chrome\"\n" +
            "            ],\n" +
            "            \"ua_browser_type\": [\n" +
            "                \"desktop\"\n" +
            "            ],\n" +
            "            \"ua_browser_version\": [\n" +
            "                \"chrome-56\"\n" +
            "            ],\n" +
            "            \"timezone\": [\n" +
            "                \"America/Los_Angeles\"\n" +
            "            ],\n" +
            "            \"ua_locale_country\": [\n" +
            "                \"US\"\n" +
            "            ],\n" +
            "            \"ua_locale_language\": [\n" +
            "                \"en\"\n" +
            "            ],\n" +
            "            \"ua_opt_in\": [\n" +
            "                \"true\"\n" +
            "            ]\n" +
            "        },\n" +
            "        \"created\": \"2013-01-25T00:55:05\",\n" +
            "        \"opt_in\": true,\n" +
            "        \"web\": {\n" +
            "            \"subscription\": {\n" +
            "                \"p256dh\": \"p256dhvalue\",\n" +
            "                \"auth\": \"authvalue\"\n" +
            "            }\n" +
            "        },\n" +
            "        \"last_registration\": \"2017-03-09T16:27:20\"\n" +
            "    }" +
            "  ],\n" +
            "  \"next_page\": \"https:\\/\\/go.urbanairship.com\\/api\\/channels?limit=5&start=0143e4d6-724c-4fc8-bbc6-ca647b8993bf\"\n" +
            "}";


        try {
            ChannelResponse response = MAPPER.readValue(sixresponse, ChannelResponse.class);

            assertTrue(response.getOk());
            assertEquals("https://go.urbanairship.com/api/channels?limit=5&start=0143e4d6-724c-4fc8-bbc6-ca647b8993bf", response.getNextPage().get());
            assertEquals(6, response.getChannelObjects().get().size());

            ChannelView one = response.getChannelObjects().get().get(0);
            assertNotNull(one);
            assertFalse(one.getAlias().isPresent());
            assertFalse(one.getBackground().isPresent());
            assertEquals("00000000-0000-0000-0000-000000000000", one.getChannelId());
            assertEquals(1338928657000L, one.getCreated().getMillis());
            assertEquals(ChannelType.ANDROID, one.getChannelType());
            assertFalse(one.getIosSettings().isPresent());
            assertFalse(one.getLastRegistration().isPresent());
            assertFalse(one.getPushAddress().isPresent());
            assertEquals("[test01]", one.getTags().toString());
            assertTrue(one.getTagGroups().containsKey("testGroup01"));
            assertEquals("[testGroup01Tag01]", one.getTagGroups().get("testGroup01").toString());
            assertFalse(one.isInstalled());
            assertFalse(one.isOptIn());

            ChannelView two = response.getChannelObjects().get().get(1);
            assertNotNull(two);
            assertEquals("aaron-device", two.getAlias().get());
            assertTrue(two.getBackground().get());
            assertEquals("00662346-9e39-4f5f-80e7-3f8fae58863c", two.getChannelId());
            assertEquals(1394131979000L, two.getCreated().getMillis());
            assertEquals(ChannelType.ANDROID, two.getChannelType());
            assertFalse(two.getIosSettings().isPresent());
            assertEquals(1412717315000L, two.getLastRegistration().get().getMillis());
            assertEquals("APA91bFPOUF6KNHXjoG0vaQSP4VLXirGDpy0_CRcb6Jhvnrya2bdRmlUoMiJ12JJevjONZzUwFETYa8uzyiE_9WaL3mzZrdjqOv2YuzYlQ_TrXVgo61JmIyw-M_pshIjVvkvtOuZ4MnRJJ_MiQDYwpB4ZhOTMlyqRw", two.getPushAddress().get());
            assertEquals("[aaron-tag, rhtgeg, tnrvrg]", two.getTags().toString());
            assertTrue(two.getTagGroups().containsKey("testGroup02"));
            assertEquals("[testGroup02Tag02, testGroup02Tag01]", two.getTagGroups().get("testGroup02").toString());
            assertTrue(two.isInstalled());
            assertTrue(two.isOptIn());

            ChannelView three = response.getChannelObjects().get().get(2);
            assertNotNull(three);
            assertFalse(three.getAlias().isPresent());
            assertTrue(three.getBackground().get());
            assertEquals("00d174cd-0a31-427e-95c9-52d5785bcd50", three.getChannelId());
            assertEquals(1404929317000L, three.getCreated().getMillis());
            assertEquals(ChannelType.IOS, three.getChannelType());
            assertEquals("IosSettings{badge=1, quiettime=Optional.of(QuietTime{start='17:00', end='9:00'}), timezone=Optional.of(America/Los_Angeles)}", three.getIosSettings().get().toString());
            assertEquals(1412214102000L, three.getLastRegistration().get().getMillis());
            assertEquals("E4EA0D96092A9213BB186BEF66E83EE226401F82B3A77A1AC8217A8FE8ED4614", three.getPushAddress().get());
            assertEquals("[version_1.5.0]", three.getTags().toString());
            assertTrue(three.getTagGroups().isEmpty());
            assertTrue(three.isInstalled());
            assertTrue(three.isOptIn());

            ChannelView four = response.getChannelObjects().get().get(3);
            assertNotNull(four);
            assertEquals("iPhone 7,1", four.getAlias().get());
            assertFalse(four.getBackground().isPresent());
            assertEquals("00d8cb94-eac9-49fb-bad0-29298a06730e", four.getChannelId());
            assertEquals(1393109317000L, four.getCreated().getMillis());
            assertEquals(ChannelType.IOS, four.getChannelType());
            assertEquals("IosSettings{badge=1, quiettime=Optional.of(QuietTime{start='null', end='null'}), timezone=Optional.absent()}", four.getIosSettings().get().toString());
            assertFalse(four.getLastRegistration().isPresent());
            assertEquals("21F34C9ED37EAF8D7DC43561C07AA398CA5C6F503196C9E8230C50C0959B8653", four.getPushAddress().get());
            assertEquals("[kablam, version_1.3]", four.getTags().toString());
            assertTrue(four.getTagGroups().containsKey("testGroup03"));

            ImmutableSet<String> expectedTags = ImmutableSet.of("testGroup03Tag01", "testGroup03Tag03", "testGroup03Tag02");
            assertEquals(expectedTags, four.getTagGroups().get("testGroup03"));
            assertTrue(four.getTagGroups().containsKey("testGroup04"));
            assertEquals("[testGroup04Tag01]", four.getTagGroups().get("testGroup04").toString());
            assertFalse(four.isInstalled());
            assertFalse(four.isOptIn());

            ChannelView five = response.getChannelObjects().get().get(4);
            assertNotNull(five);
            assertFalse(five.getAlias().isPresent());
            assertFalse(five.getBackground().isPresent());
            assertEquals("01257ecd-8182-41fe-a741-9fed91b993cb", five.getChannelId());
            assertEquals(1359075305000L, five.getCreated().getMillis());
            assertEquals(ChannelType.ANDROID, five.getChannelType());
            assertFalse(five.getIosSettings().isPresent());
            assertFalse(five.getLastRegistration().isPresent());
            assertFalse(five.getPushAddress().isPresent());
            assertEquals("[]", five.getTags().toString());
            assertTrue(five.getTagGroups().isEmpty());
            assertFalse(five.isInstalled());
            assertFalse(five.isOptIn());

            ChannelView six = response.getChannelObjects().get().get(5);
            assertNotNull(six);
            assertFalse(six.getAlias().isPresent());
            assertFalse(five.getBackground().isPresent());
            assertEquals("f82d3723-09d0-4390-b0ff-690485685e3e", six.getChannelId());
            assertEquals(1359075305000L, six.getCreated().getMillis());
            assertEquals(ChannelType.WEB, six.getChannelType());
            assertFalse(six.getIosSettings().isPresent());
            assertFalse(five.getLastRegistration().isPresent());
            assertTrue(six.getPushAddress().isPresent());
            assertTrue(six.isInstalled());
            assertTrue(six.isOptIn());
            assertTrue(six.getTags().contains("test1"));


        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception " + ex.getMessage());
        }
    }
}
