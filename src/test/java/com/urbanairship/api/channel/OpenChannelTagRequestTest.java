package com.urbanairship.api.channel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class OpenChannelTagRequestTest {
    private static final ObjectMapper MAPPER = ChannelObjectMapper.getInstance();

    Set<String> testTagSet;

    public void setup(){
    testTagSet = new TreeSet<>();
        for(int i =1; i < 4 ; i++) {
            testTagSet.add(String.format("tag%s", i));
        }
    }

    @Test
    public void testAddOpenChannelTagRequest() throws IOException {

        setup();

        OpenChannelTagRequest openChannelTagRequest =  OpenChannelTagRequest.newRequest();
            openChannelTagRequest.addOpenChannel("Number Four","cylon")
            .addTags("my_fav_tag_group1", testTagSet )
            .addTags("my_fav_tag_group2", testTagSet )
            .addTags("my_fav_tag_group3", testTagSet );


        String jsonFromString = "{\n" +
                "   \"audience\": {\n" +
                "      \"address\": \"Number Four\",\n" +
                "      \"open_platform_name\": \"cylon\"\n" +
                "   },\n" +
                "   \"add\": {\n" +
                "      \"my_fav_tag_group1\": [\"tag1\", \"tag2\", \"tag3\"],\n" +
                "      \"my_fav_tag_group2\": [\"tag1\", \"tag2\", \"tag3\"],\n" +
                "      \"my_fav_tag_group3\": [\"tag1\", \"tag2\", \"tag3\"]\n" +
                "   }\n" +
                "}";

        JsonNode actual = MAPPER.readTree(openChannelTagRequest.getRequestBody());
        JsonNode expected = MAPPER.readTree(jsonFromString);

        assertEquals(expected, actual);
    }

    @Test
    public void testRemoveOpenChannelTagRequest() throws IOException {

        setup();

        OpenChannelTagRequest openChannelTagRequest =  OpenChannelTagRequest.newRequest();
            openChannelTagRequest.addOpenChannel("Number Four","cylon")
                .removeTags("my_fav_tag_group1", testTagSet )
                .removeTags("my_fav_tag_group2", testTagSet )
                .removeTags("my_fav_tag_group3", testTagSet );


        String jsonFromString = "{\n" +
                "   \"audience\": {\n" +
                "      \"address\": \"Number Four\",\n" +
                "      \"open_platform_name\": \"cylon\"\n" +
                "   },\n" +
                "   \"remove\": {\n" +
                "      \"my_fav_tag_group1\": [\"tag1\", \"tag2\", \"tag3\"],\n" +
                "      \"my_fav_tag_group2\": [\"tag1\", \"tag2\", \"tag3\"],\n" +
                "      \"my_fav_tag_group3\": [\"tag1\", \"tag2\", \"tag3\"]\n" +
                "   }\n" +
                "}";

        JsonNode actual = MAPPER.readTree(openChannelTagRequest.getRequestBody());
        JsonNode expected = MAPPER.readTree(jsonFromString);

        assertEquals(expected, actual);
    }

    @Test
    public void testSetOpenChannelTagRequest() throws IOException {
        setup();

        OpenChannelTagRequest openChannelTagRequest =  OpenChannelTagRequest.newRequest();
            openChannelTagRequest.addOpenChannel("Number Four","cylon")
                .setTags("my_fav_tag_group1", testTagSet )
                .setTags("my_fav_tag_group2", testTagSet )
                .setTags("my_fav_tag_group3", testTagSet );

        String jsonFromString = "{\n" +
                "   \"audience\": {\n" +
                "      \"address\": \"Number Four\",\n" +
                "      \"open_platform_name\": \"cylon\"\n" +
                "   },\n" +
                "   \"set\": {\n" +
                "      \"my_fav_tag_group1\": [\"tag1\", \"tag2\", \"tag3\"],\n" +
                "      \"my_fav_tag_group2\": [\"tag1\", \"tag2\", \"tag3\"],\n" +
                "      \"my_fav_tag_group3\": [\"tag1\", \"tag2\", \"tag3\"]\n" +
                "   }\n" +
                "}";

        JsonNode actual = MAPPER.readTree(openChannelTagRequest.getRequestBody());
        JsonNode expected = MAPPER.readTree(jsonFromString);

        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyEmailTagRequest() {

        OpenChannelTagRequest openChannelTagRequest =  OpenChannelTagRequest.newRequest();
        openChannelTagRequest.getRequestBody();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyAudienceTagRequest() {

        OpenChannelTagRequest openChannelTagRequest =  OpenChannelTagRequest.newRequest();
            openChannelTagRequest.addOpenChannel("Number Four","cylon");
            openChannelTagRequest.getRequestBody();
    }

    @Test(expected = IllegalArgumentException.class)
    public void tesSetTagAndAddRequest() {

        OpenChannelTagRequest openChannelTagRequest =  OpenChannelTagRequest.newRequest();
            openChannelTagRequest.addOpenChannel("Number Four","cylon")
                .setTags("my_fav_tag_group1", testTagSet )
                .addTags("my_fav_tag_group1", testTagSet );
            openChannelTagRequest.getRequestBody();
    }
}