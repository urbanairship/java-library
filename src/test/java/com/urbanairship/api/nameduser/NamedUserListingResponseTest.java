package com.urbanairship.api.nameduser;

import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.channel.model.ChannelView;
import com.urbanairship.api.nameduser.model.NamedUserListingResponse;
import com.urbanairship.api.nameduser.model.NamedUserView;
import com.urbanairship.api.nameduser.parse.NamedUserObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NamedUserListingResponseTest {
    private final ObjectMapper mapper = NamedUserObjectMapper.getInstance();

    @Test
    public void testListNamedUserResponse() throws Exception {
        String json = "{" +
            "\"ok\": true," +
            "\"named_user\": {" +
            "\"named_user_id\": \"user-id-1234\"," +
            "\"tags\": {" +
            "\"my_fav_tag_group\": [\"tag1\", \"tag2\"]" +
            "}," +
            "\"channels\": [" +
            "{" +
            "\"channel_id\": \"ABCD\"," +
            "\"device_type\": \"ios\"," +
            "\"installed\": true," +
            "\"opt_in\": true," +
            "\"push_address\": \"FFFF\"," +
            "\"created\": \"2013-08-08T20:41:06\"," +
            "\"last_registration\": \"2014-05-01T18:00:27\"," +
            "\"alias\": \"xxxx\"," +
            "\"tags\": [\"asdf\"]," +
            "\"ios\": {" +
            "\"badge\": 0," +
            "\"quiettime\": {" +
            "\"start\": \"22:00\"," +
            "\"end\": \"06:00\"" +
            "}," +
            "\"tz\": \"America/Los_Angeles\"" +
            "}" +
            "}" +
            "]" +
            "}" +
            "}";

        NamedUserListingResponse namedUserResponse = mapper.readValue(json, NamedUserListingResponse.class);
        assertTrue(namedUserResponse.getOk());

        NamedUserView namedUserView = namedUserResponse.getNamedUserView().get();
        assertEquals("user-id-1234", namedUserView.getNamedUserId());
        assertEquals(ImmutableSet.of("my_fav_tag_group"), namedUserView.getNamedUserTags().keySet());
        assertEquals(ImmutableSet.of("tag1", "tag2"), namedUserView.getNamedUserTags().get("my_fav_tag_group"));

        ChannelView channel = namedUserView.getChannelViews().iterator().next();
        assertTrue(channel.isOptIn());
        assertFalse(channel.getBackground().isPresent());
        assertEquals(ChannelType.IOS, channel.getChannelType());
        assertTrue(channel.getIosSettings().isPresent());
        assertEquals(DateTime.parse("2013-08-08T20:41:06.000Z"), channel.getCreated());
        assertTrue(channel.getLastRegistration().isPresent());
        assertEquals(DateTime.parse("2014-05-01T18:00:27.000Z"), channel.getLastRegistration().get());
        assertEquals(0, channel.getIosSettings().get().getBadge());
        assertEquals("22:00", channel.getIosSettings().get().getQuietTime().get().getStart());
        assertEquals("06:00", channel.getIosSettings().get().getQuietTime().get().getEnd());
        assertEquals("America/Los_Angeles", channel.getIosSettings().get().getTimezone().get());
        assertEquals("FFFF", channel.getPushAddress().get());
        assertEquals("xxxx", channel.getAlias().get());
        ImmutableSet<String> expectedTags = new ImmutableSet.Builder<String>()
            .add("asdf").build();
        assertEquals(expectedTags, channel.getTags());
    }

    @Test
    public void testListAllNamedUsersResponse() throws Exception {
        String json = "{" +
            "\"ok\": true," +
            "\"next_page\": \"https://go.urbanairship.com/api/named_users?start=user-1234\"," +
            "\"named_users\": [" +
            "{" +
            "\"named_user_id\": \"user-id-1234\"," +
            "\"tags\": {" +
            "\"my_fav_tag_group\": [\"tag1\", \"tag2\"]" +
            "}," +
            "\"channels\": [" +
            "{" +
            "\"channel_id\": \"ABCD\"," +
            "\"device_type\": \"ios\"," +
            "\"installed\": true," +
            "\"opt_in\": true," +
            "\"push_address\": \"FFFF\"," +
            "\"created\": \"2013-08-08T20:41:06\"," +
            "\"last_registration\": \"2014-05-01T18:00:27\"," +
            "\"alias\": \"xxxx\"," +
            "\"tags\": [\"asdf\"]," +
            "\"ios\": {" +
            "\"badge\": 0," +
            "\"quiettime\": {" +
            "\"start\": \"22:00\"," +
            "\"end\": \"06:00\"" +
            "}," +
            "\"tz\": \"America/Los_Angeles\"" +
            "}" +
            "}" +
            "]" +
            "}," +
            "{" +
            "\"named_user_id\": \"user-id-5678\"," +
            "\"tags\": {}," +
            "\"channels\": []" +
            "}" +
            "]" +
            "}";

        NamedUserListingResponse namedUserResponse = mapper.readValue(json, NamedUserListingResponse.class);
        assertTrue(namedUserResponse.getOk());
        assertEquals("https://go.urbanairship.com/api/named_users?start=user-1234", namedUserResponse.getNextPage().get());

        NamedUserView namedUserView1 = namedUserResponse.getNamedUserViews().get().get(0);
        assertEquals("user-id-1234", namedUserView1.getNamedUserId());
        assertEquals(ImmutableSet.of("my_fav_tag_group"), namedUserView1.getNamedUserTags().keySet());
        assertEquals(ImmutableSet.of("tag1", "tag2"), namedUserView1.getNamedUserTags().get("my_fav_tag_group"));

        ChannelView channel = namedUserView1.getChannelViews().iterator().next();
        assertTrue(channel.isOptIn());
        assertFalse(channel.getBackground().isPresent());
        assertEquals(ChannelType.IOS, channel.getChannelType());
        assertTrue(channel.getIosSettings().isPresent());
        assertEquals(DateTime.parse("2013-08-08T20:41:06.000Z"), channel.getCreated());
        assertTrue(channel.getLastRegistration().isPresent());
        assertEquals(DateTime.parse("2014-05-01T18:00:27.000Z"), channel.getLastRegistration().get());
        assertEquals(0, channel.getIosSettings().get().getBadge());
        assertEquals("22:00", channel.getIosSettings().get().getQuietTime().get().getStart());
        assertEquals("06:00", channel.getIosSettings().get().getQuietTime().get().getEnd());
        assertEquals("America/Los_Angeles", channel.getIosSettings().get().getTimezone().get());
        assertEquals("FFFF", channel.getPushAddress().get());
        assertEquals("xxxx", channel.getAlias().get());
        ImmutableSet<String> expectedTags = new ImmutableSet.Builder<String>()
            .add("asdf").build();
        assertEquals(expectedTags, channel.getTags());

        NamedUserView namedUserView2 = namedUserResponse.getNamedUserViews().get().get(1);
        assertEquals("user-id-5678", namedUserView2.getNamedUserId());
        assertTrue(namedUserView2.getNamedUserTags().isEmpty());
        assertTrue(namedUserView2.getChannelViews().isEmpty());
    }
}
