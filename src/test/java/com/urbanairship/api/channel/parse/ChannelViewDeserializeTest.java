package com.urbanairship.api.channel.parse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.channel.model.ChannelView;
import com.urbanairship.api.channel.model.ChannelResponse;
import com.urbanairship.api.channel.model.web.Subscription;
import com.urbanairship.api.common.parse.APIParsingException;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

public class ChannelViewDeserializeTest {

    private static final ObjectMapper mapper = ChannelObjectMapper.getInstance();

    @Test
    public void testMinimal() throws Exception {
        String json = "{" +
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
    public void testEmailChannelWithTracking() throws Exception {
        String json = "{" +
                "\"ok\": true," +
                "\"channel\": {" +
                "\"channel_id\": \"01234567-890a-bcde-f012-3456789abc0\"," +
                "\"device_type\": \"email\"," +
                "\"installed\": true," +
                "\"created\": \"2020-08-08T20:41:06\"," +
                "\"named_user_id\": \"some_id_that_maps_to_your_systems\"," +
                "\"email_address\": \"name@example.com\"," +
                "\"tag_groups\": {" +
                "\"tag_group_1\": [\"tag1\", \"tag2\"]," +
                "\"tag_group_2\": [\"tag1\", \"tag2\"]" +
                "}," +
                "\"address\": null," +
                "\"opt_in\": true," +
                "\"commercial_opted_in\": \"2020-10-28T10:34:22\"," +
                "\"commercial_opted_out\": \"2020-06-03T09:15:00\"," +
                "\"transactional_opted_in\": \"2020-10-28T10:34:22\"," +
                "\"open_tracking_opted_in\": \"2022-12-11T00:00:00\"," +
                "\"click_tracking_opted_in\": \"2022-12-11T00:00:00\"," +
                "\"open_tracking_opted_out\": \"2022-12-12T00:00:00\"," +
                "\"click_tracking_opted_out\": \"2022-12-12T00:00:00\"," +
                "\"last_registration\": \"2020-05-01T18:00:27\"" +
                "}" +
                "}";

        ChannelResponse response = mapper.readValue(json, ChannelResponse.class);
        Assert.assertTrue(response.getOk());
        Assert.assertTrue(response.getChannelView().isPresent());
        ChannelView channel = response.getChannelView().get();

        Assert.assertEquals("email", channel.getChannelType());
        Assert.assertTrue(channel.isInstalled());
        Assert.assertTrue(channel.isOptIn());
        Assert.assertEquals("name@example.com", channel.getEmailAddress().get());
        Assert.assertEquals("some_id_that_maps_to_your_systems", channel.getNamedUser().get());
        Assert.assertFalse(channel.getAddress().isPresent());

        // Tag groups
        Assert.assertTrue(channel.getTagGroups().containsKey("tag_group_1"));
        Assert.assertTrue(channel.getTagGroups().containsKey("tag_group_2"));

        // Dates
        Assert.assertEquals(DateTime.parse("2020-08-08T20:41:06Z").toInstant(), channel.getCreated().toInstant());
        Assert.assertEquals(DateTime.parse("2020-05-01T18:00:27Z").toInstant(),
                channel.getLastRegistration().get().toInstant());
        Assert.assertEquals(DateTime.parse("2020-10-28T10:34:22Z").toInstant(),
                channel.getCommercialOptedIn().get().toInstant());
        Assert.assertEquals(DateTime.parse("2020-06-03T09:15:00Z").toInstant(),
                channel.getCommercialOptedOut().get().toInstant());
        Assert.assertEquals(DateTime.parse("2020-10-28T10:34:22Z").toInstant(),
                channel.getTransactionalOptedIn().get().toInstant());

        Assert.assertEquals(DateTime.parse("2022-12-11T00:00:00Z").toInstant(),
                channel.getOpenTrackingOptedIn().get().toInstant());
        Assert.assertEquals(DateTime.parse("2022-12-12T00:00:00Z").toInstant(),
                channel.getOpenTrackingOptedOut().get().toInstant());
        Assert.assertEquals(DateTime.parse("2022-12-11T00:00:00Z").toInstant(),
                channel.getClickTrackingOptedIn().get().toInstant());
        Assert.assertEquals(DateTime.parse("2022-12-12T00:00:00Z").toInstant(),
                channel.getClickTrackingOptedOut().get().toInstant());
    }

    @Test
    public void testMaximal() throws Exception {
        String json = "{" +
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
                "\"push_address\" : \"address\"," +
                "\"suppression_state\" : \"suppression_state\"" +
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
        Assert.assertEquals("suppression_state", channel.getSuppressionState().get());
    }

    @Test
    public void testWebChannel() throws Exception {
        String json = "{" +
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
        String json = "{" +
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
            String json = "{" +
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
        String json = "{" +
                "\"device_type\" : \"ios\"," +
                "\"opt_in\" : false," +
                "\"badkey\" : \"nonsense\"" +
                "}";

        mapper.readValue(json, new TypeReference<ChannelView>() {
        });
    }

    @Test(expected = APIParsingException.class)
    public void testDeviceTypesInvalid() throws Exception {
        String json = "{" +
                "\"device_type\" : \"tizen lol\"," +
                "\"opt_in\" : false" +
                "}";

        mapper.readValue(json, new TypeReference<ChannelView>() {
        });
    }

    @Test(expected = APIParsingException.class)
    public void testDeviceTypesMissing() throws Exception {
        String json = "{" +
                "\"opt_in\" : false" +
                "}";

        mapper.readValue(json, new TypeReference<ChannelView>() {
        });
    }

    @Test(expected = APIParsingException.class)
    public void testOptInMissing() throws Exception {
        String json = "{" +
                "\"device_type\" : \"ios\"" +
                "}";

        mapper.readValue(json, new TypeReference<ChannelView>() {
        });
    }
}