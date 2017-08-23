package com.urbanairship.api.channel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import com.google.common.net.HttpHeaders;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.client.Request;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ChannelTagRequestTest {

    private static final ObjectMapper MAPPER = ChannelObjectMapper.getInstance();

    @Test
    public void testAddTagsBody() throws Exception {
        String iosChannel1 = UUID.randomUUID().toString();
        String iosChannel2 = UUID.randomUUID().toString();
        String androidChannel = UUID.randomUUID().toString();

        String expected = "{" +
              "\"audience\":{" +
                "\"ios_channel\":[\"" + iosChannel1+ "\",\"" + iosChannel2 +"\"]," +
                "\"android_channel\":[\"" + androidChannel + "\"]" +
              "}," +
              "\"add\":{" +
                "\"tag_group1\":[\"tag1\",\"tag2\",\"tag3\"]," +
                "\"tag_group3\":[\"tag1\",\"tag2\",\"tag3\"]," +
                "\"tag_group2\":[\"tag1\",\"tag2\",\"tag3\"]" +
              "}" +
            "}";

        Set<String> iosChannels = ImmutableSet.of(iosChannel1, iosChannel2);
        ChannelTagRequest request = ChannelTagRequest.newRequest()
            .addIOSChannels(iosChannels)
            .addAndroidChannel(androidChannel)
            .addTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .addTags("tag_group2", ImmutableSet.of("tag1", "tag2", "tag3"))
            .addTags("tag_group3", ImmutableSet.of("tag1", "tag2", "tag3"));

        JsonNode jsonFromObject = MAPPER.readTree(request.getRequestBody());
        JsonNode jsonFromString = MAPPER.readTree(expected);

        assertEquals(jsonFromObject, jsonFromString);
    }

    @Test
    public void testRemoveTags() throws Exception {
        String iosChannel1 = UUID.randomUUID().toString();
        String iosChannel2 = UUID.randomUUID().toString();
        String androidChannel = UUID.randomUUID().toString();

        String expected = "{" +
              "\"audience\":{" +
                "\"ios_channel\":[\"" + iosChannel1+ "\",\"" + iosChannel2 +"\"]," +
                "\"android_channel\":[\"" + androidChannel + "\"]" +
              "}," +
              "\"remove\":{" +
                "\"tag_group1\":[\"tag1\",\"tag2\",\"tag3\"]," +
                "\"tag_group3\":[\"tag1\",\"tag2\",\"tag3\"]," +
                "\"tag_group2\":[\"tag1\",\"tag2\",\"tag3\"]" +
              "}" +
            "}";

        Set<String> iosChannels = ImmutableSet.of(iosChannel1, iosChannel2);

        ChannelTagRequest request = ChannelTagRequest.newRequest()
            .addIOSChannels(iosChannels)
            .addAndroidChannel(androidChannel)
            .removeTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .removeTags("tag_group2", ImmutableSet.of("tag1", "tag2", "tag3"))
            .removeTags("tag_group3", ImmutableSet.of("tag1", "tag2", "tag3"));

        JsonNode jsonFromObject = MAPPER.readTree(request.getRequestBody());
        JsonNode jsonFromString = MAPPER.readTree(expected);

        assertEquals(jsonFromObject, jsonFromString);
    }

    @Test
    public void testSetTags() throws Exception {
        String iosChannel1 = UUID.randomUUID().toString();
        String iosChannel2 = UUID.randomUUID().toString();
        String androidChannel = UUID.randomUUID().toString();

        String expected = "{" +
              "\"audience\":{" +
                "\"ios_channel\":[\"" + iosChannel1+ "\",\"" + iosChannel2 +"\"]," +
                "\"android_channel\":[\"" + androidChannel + "\"]" +
              "}," +
              "\"set\":{" +
                "\"tag_group1\":[\"tag1\",\"tag2\",\"tag3\"]," +
                "\"tag_group3\":[\"tag1\",\"tag2\",\"tag3\"]," +
                "\"tag_group2\":[\"tag1\",\"tag2\",\"tag3\"]" +
              "}" +
            "}";

        Set<String> iosChannels = ImmutableSet.of(iosChannel1, iosChannel2);

        ChannelTagRequest request = ChannelTagRequest.newRequest()
            .addIOSChannels(iosChannels)
            .addAndroidChannel(androidChannel)
            .setTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .setTags("tag_group2", ImmutableSet.of("tag1", "tag2", "tag3"))
            .setTags("tag_group3", ImmutableSet.of("tag1", "tag2", "tag3"));

        JsonNode jsonFromObject = MAPPER.readTree(request.getRequestBody());
        JsonNode jsonFromString = MAPPER.readTree(expected);

        assertEquals(jsonFromObject, jsonFromString);
    }

    @Test
    public void testAddAndRemoveTags() throws Exception {
        String iosChannel1 = UUID.randomUUID().toString();
        String iosChannel2 = UUID.randomUUID().toString();
        String androidChannel = UUID.randomUUID().toString();

        String expected = "{" +
              "\"audience\":{" +
                "\"ios_channel\":[\"" + iosChannel1+ "\",\"" + iosChannel2 +"\"]," +
                "\"android_channel\":[\"" + androidChannel + "\"]" +
              "}," +
              "\"remove\":{" +
                "\"tag_group1\":[\"tag4\",\"tag5\",\"tag6\"]," +
                "\"tag_group3\":[\"tag4\",\"tag5\",\"tag6\"]," +
                "\"tag_group2\":[\"tag4\",\"tag5\",\"tag6\"]" +
              "}," +
              "\"add\":{" +
                "\"tag_group1\":[\"tag1\",\"tag2\",\"tag3\"]," +
                "\"tag_group3\":[\"tag1\",\"tag2\",\"tag3\"]," +
                "\"tag_group2\":[\"tag1\",\"tag2\",\"tag3\"]" +
              "}" +
            "}";

        Set<String> iosChannels = ImmutableSet.of(iosChannel1, iosChannel2);

        ChannelTagRequest request = ChannelTagRequest.newRequest()
            .addIOSChannels(iosChannels)
            .addAndroidChannel(androidChannel)
            .addTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .addTags("tag_group2", ImmutableSet.of("tag1", "tag2", "tag3"))
            .addTags("tag_group3", ImmutableSet.of("tag1", "tag2", "tag3"))
            .removeTags("tag_group1", ImmutableSet.of("tag4", "tag5", "tag6"))
            .removeTags("tag_group2", ImmutableSet.of("tag4", "tag5", "tag6"))
            .removeTags("tag_group3", ImmutableSet.of("tag4", "tag5", "tag6"));


        JsonNode jsonFromObject = MAPPER.readTree(request.getRequestBody());
        JsonNode jsonFromString = MAPPER.readTree(expected);

        assertEquals(jsonFromObject, jsonFromString);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddAndSetTags() throws Exception {
        ChannelTagRequest request = ChannelTagRequest.newRequest()
            .addIOSChannel(UUID.randomUUID().toString())
            .addTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .setTags("tag_group1", ImmutableSet.of("tag4", "tag5", "tag6"));
        request.getRequestBody();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testRemoveAndSetTags() throws Exception {
        ChannelTagRequest request = ChannelTagRequest.newRequest()
            .addIOSChannel(UUID.randomUUID().toString())
            .removeTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
            .setTags("tag_group1", ImmutableSet.of("tag4", "tag5", "tag6"));
        request.getRequestBody();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNoTagMutations() throws Exception {
        ChannelTagRequest request = ChannelTagRequest.newRequest()
            .addIOSChannel(UUID.randomUUID().toString());
        request.getRequestBody();
    }


    String iosChannel1 = UUID.randomUUID().toString();
    String iosChannel2 = UUID.randomUUID().toString();
    String androidChannel = UUID.randomUUID().toString();
    Set<String> iosChannels = ImmutableSet.of(iosChannel1, iosChannel2);
    ChannelTagRequest request = ChannelTagRequest.newRequest()
        .addIOSChannels(iosChannels)
        .addAndroidChannel(androidChannel)
        .addTags("tag_group1", ImmutableSet.of("tag1", "tag2", "tag3"))
        .addTags("tag_group2", ImmutableSet.of("tag1", "tag2", "tag3"))
        .addTags("tag_group3", ImmutableSet.of("tag1", "tag2", "tag3"));

    @Test
    public void testContentType() throws Exception {
        assertEquals(request.getContentType(), ContentType.APPLICATION_JSON);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(request.getHttpMethod(), Request.HttpMethod.POST);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_JSON);
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);

        assertEquals(request.getRequestHeaders(), headers);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expextedURI = URI.create("https://go.urbanairship.com/api/channels/tags/");
        assertEquals(request.getUri(baseURI), expextedURI);
    }

    @Test
    public void testPushParser() throws Exception {
        String response = "{\"ok\" : true}";
        assertEquals(response, request.getResponseParser().parse(response));
    }

}
