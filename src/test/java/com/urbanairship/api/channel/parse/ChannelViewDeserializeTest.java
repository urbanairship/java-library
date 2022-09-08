package com.urbanairship.api.channel.parse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.channel.model.ChannelView;
import com.urbanairship.api.channel.model.web.Subscription;
import com.urbanairship.api.common.parse.APIParsingException;
import org.junit.Assert;
import org.junit.Test;

public class ChannelViewDeserializeTest {


    private static final ObjectMapper mapper = ChannelObjectMapper.getInstance();

    @Test
    public void testMinimal() throws Exception {
        String json =
            "{" +
                "\"channel_id\" : \"251d3318-b3cb-4e9f-876a-ea3bfa6e47bd\"," +
                "\"device_type\" : \"ios\"," +
                "\"installed\" : true," +
                "\"opt_in\" : false," +
                "\"created\" : \"2013-08-08T20:41:06.000Z\"" +
                "}";

        ChannelView channel = mapper.readValue(json, ChannelView.class);
        Assert.assertFalse(channel.isOptIn());
        Assert.assertFalse(channel.getBackground().isPresent());
        Assert.assertEquals(ChannelType.IOS.getIdentifier(), channel.getChannelType());
        Assert.assertFalse(channel.getAlias().isPresent());
        Assert.assertFalse(channel.getIosSettings().isPresent());
        Assert.assertFalse(channel.getPushAddress().isPresent());
        Assert.assertTrue(channel.getTags().isEmpty());

    }

    @Test
    public void testMaximal() throws Exception {
        String json =
            "{" +
                "\"channel_id\" : \"251d3318-b3cb-4e9f-876a-ea3bfa6e47bd\"," +
                "\"device_type\" : \"ios\"," +
                "\"installed\" : true," +
                "\"opt_in\" : true," +
                "\"background\" : true," +
                    "\"attributes\": {\n" +
                    "      \"customerid\": 20,\n" +
                    "      \"email\": \"email@test.com\"\n" +
                    "    }," +
                    "\"device_attributes\": {\n" +
                    "      \"customerid\": 10,\n" +
                    "      \"email\": \"email2@test.com\"\n" +
                    "    }," +
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
                "\"named_user_id\" : \"namedUser\"," +
                "\"created\" : \"2013-08-08T20:41:06.000Z\"," +
                "\"push_address\" : \"address\"" +
                "}";

        ChannelView channel = mapper.readValue(json, ChannelView.class);

        Assert.assertTrue(channel.isOptIn());
        Assert.assertTrue(channel.getBackground().isPresent());
        Assert.assertTrue(channel.getBackground().get());
        Assert.assertEquals(ChannelType.IOS.getIdentifier(), channel.getChannelType());
        Assert.assertTrue(channel.getIosSettings().isPresent());
        Assert.assertEquals("email@test.com", channel.getAttributes().get("email"));
        Assert.assertEquals("20", channel.getAttributes().get("customerid"));
        Assert.assertEquals("email2@test.com", channel.getDeviceAttributes().get("email"));
        Assert.assertEquals("10", channel.getDeviceAttributes().get("customerid"));
        Assert.assertEquals(0, channel.getIosSettings().get().getBadge());
        Assert.assertEquals("22:00", channel.getIosSettings().get().getQuietTime().get().getStart());
        Assert.assertEquals("06:00", channel.getIosSettings().get().getQuietTime().get().getEnd());
        Assert.assertEquals("America/Los_Angeles", channel.getIosSettings().get().getTimezone().get());
        Assert.assertEquals("address", channel.getPushAddress().get());
        Assert.assertEquals("alias", channel.getAlias().get());
        Assert.assertEquals("namedUser", channel.getNamedUser().get());
        ImmutableSet<String> expectedTags = new ImmutableSet.Builder<String>()
            .addAll(Sets.newHashSet("tag1", "tag2")).build();
            Assert.assertEquals(expectedTags, channel.getTags());
        ImmutableMap<String, ImmutableSet<String>> expectedTagGroups = new ImmutableMap.Builder<String, ImmutableSet<String>>()
            .put("group1", new ImmutableSet.Builder<String>()
                .addAll(Sets.newHashSet("tag1OfGroup1", "tag2OfGroup1")).build())
            .put("group2", new ImmutableSet.Builder<String>()
                .addAll(Sets.newHashSet("tag1OfGroup2", "tag2OfGroup2")).build())
            .build();
            Assert.assertEquals(expectedTagGroups, channel.getTagGroups());
    }

    @Test
    public void testWebChannel() throws Exception {
        String json =
                "{" +
                    "\"channel_id\": \"f82d3723-09d0-4390-b0ff-690485685e3e\"," +
                    "\"device_type\": \"web\"," +
                    "\"installed\": true," +
                    "\"push_address\": \"address\"," +
                    "\"named_user_id\": null," +
                    "\"alias\": null," +
                    "\"tags\": [" +
                        "\"tag1\"," +
                        "\"tag2\"" +
                    "]," +
                    "\"tag_groups\": {" +
                        "\"ua_channel_type\": [" +
                            "\"web\"" +
                        "]," +
                        "\"ua_web_sdk_version\": [" +
                            "\"0.0.1\"" +
                        "]," +
                        "\"ua_browser_name\": [" +
                            "\"chrome\"" +
                        "]," +
                        "\"ua_browser_type\": [" +
                            "\"desktop\"" +
                        "]," +
                        "\"ua_browser_version\": [" +
                            "\"chrome-56\"" +
                        "]," +
                        "\"timezone\": [" +
                            "\"America/Los_Angeles\"" +
                        "]," +
                        "\"ua_locale_country\": [" +
                            "\"US\"" +
                        "]," +
                        "\"ua_locale_language\": [" +
                            "\"en\"" +
                        "]," +
                        "\"ua_opt_in\": [" +
                            "\"true\"" +
                        "]" +
                    "}," +
                    "\"created\": \"2017-03-07T22:05:38\"," +
                    "\"opt_in\": true," +
                    "\"web\": {" +
                        "\"subscription\": {" +
                            "\"p256dh\": \"p256dhvalue\"," +
                            "\"auth\": \"authvalue\"" +
                        "}" +
                    "}," +
                    "\"last_registration\": \"2017-03-09T16:27:20\"" +
                "}";

        ChannelView channel = mapper.readValue(json, ChannelView.class);

        Assert.assertTrue(channel.isOptIn());
        Assert.assertEquals(ChannelType.WEB.getIdentifier(), channel.getChannelType());

        Assert.assertTrue(channel.getWebSettings().isPresent());
        Assert.assertTrue(channel.getWebSettings().get().getSubscription().isPresent());

        Subscription subscription = channel.getWebSettings().get().getSubscription().get();

        Assert.assertEquals("p256dhvalue", subscription.getP256dh().get());
        Assert.assertEquals("authvalue", subscription.getAuth().get());

        Assert.assertEquals("address", channel.getPushAddress().get());
        ImmutableSet<String> expectedTags = new ImmutableSet.Builder<String>()
                .addAll(Sets.newHashSet("tag1", "tag2")).build();
                Assert.assertEquals(expectedTags, channel.getTags());
    }

    @Test
    public void testEmptyTags() throws Exception {
        String json =
            "{" +
                "\"channel_id\" : \"251d3318-b3cb-4e9f-876a-ea3bfa6e47bd\"," +
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
            "\"channel_id\" : \"251d3318-b3cb-4e9f-876a-ea3bfa6e47bd\"," +
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
                    "\"channel_id\" : \"251d3318-b3cb-4e9f-876a-ea3bfa6e47bd\"," +
                    "\"device_type\" : \"" + channelType.getIdentifier() + "\"," +
                    "\"installed\" : true," +
                    "\"created\" : \"2013-08-08T20:41:06.000Z\"," +
                    "\"opt_in\" : false" +
                    "}";

            ChannelView channel = mapper.readValue(json, new TypeReference<ChannelView>() {
            });
            Assert.assertFalse(channel.isOptIn());
            Assert.assertEquals(channelType.getIdentifier(), channel.getChannelType());
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