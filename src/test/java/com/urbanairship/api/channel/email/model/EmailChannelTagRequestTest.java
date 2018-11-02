package com.urbanairship.api.channel.email.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.channel.model.email.EmailTagRequest;
import com.urbanairship.api.channel.model.email.OptInLevel;
import com.urbanairship.api.channel.model.email.RegisterEmailChannel;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EmailChannelTagRequestTest {
    private static final ObjectMapper MAPPER = ChannelObjectMapper.getInstance();

    @Test
    public void testAddEmailTagRequest() throws IOException {

        Set<String> testTagSet = new TreeSet<>();
        for(int i =1; i < 4 ; i++) {
            testTagSet.add(String.format("tag%s", i));
        }

        EmailTagRequest emailTagRequest =  EmailTagRequest.newRequest();
            emailTagRequest.addEmailChannel("name@example.com")
            .addTags("my_fav_tag_group1", testTagSet )
            .addTags("my_fav_tag_group2", testTagSet )
            .addTags("my_fav_tag_group3", testTagSet );


        String jsonFromString = "{\n" +
                "   \"audience\": {\n" +
                "      \"email_address\": \"name@example.com\"\n" +
                "   },\n" +
                "   \"add\": {\n" +
                "      \"my_fav_tag_group1\": [\"tag1\", \"tag2\", \"tag3\"],\n" +
                "      \"my_fav_tag_group2\": [\"tag1\", \"tag2\", \"tag3\"],\n" +
                "      \"my_fav_tag_group3\": [\"tag1\", \"tag2\", \"tag3\"]\n" +
                "   }\n" +
                "}";

        JsonNode actual = MAPPER.readTree(emailTagRequest.getRequestBody());
        JsonNode expected = MAPPER.readTree(jsonFromString);

        assertEquals(expected, actual);
    }

    @Test
    public void testRemoveEmailTagRequest() throws IOException {

        Set<String> testTagSet = new TreeSet<>();
        for(int i =1; i < 4 ; i++) {
            testTagSet.add(String.format("tag%s", i));
        }

        EmailTagRequest emailTagRequest =  EmailTagRequest.newRequest();
        emailTagRequest.addEmailChannel("name@example.com")
                .removeTags("my_fav_tag_group1", testTagSet )
                .removeTags("my_fav_tag_group2", testTagSet )
                .removeTags("my_fav_tag_group3", testTagSet );


        String jsonFromString = "{\n" +
                "   \"audience\": {\n" +
                "      \"email_address\": \"name@example.com\"\n" +
                "   },\n" +
                "   \"remove\": {\n" +
                "      \"my_fav_tag_group1\": [\"tag1\", \"tag2\", \"tag3\"],\n" +
                "      \"my_fav_tag_group2\": [\"tag1\", \"tag2\", \"tag3\"],\n" +
                "      \"my_fav_tag_group3\": [\"tag1\", \"tag2\", \"tag3\"]\n" +
                "   }\n" +
                "}";

        JsonNode actual = MAPPER.readTree(emailTagRequest.getRequestBody());
        JsonNode expected = MAPPER.readTree(jsonFromString);

        assertEquals(expected, actual);
    }

    @Test
    public void testSetEmailTagRequest() throws IOException {

        Set<String> testTagSet = new TreeSet<>();
        for(int i =1; i < 4 ; i++) {
            testTagSet.add(String.format("tag%s", i));
        }

        EmailTagRequest emailTagRequest =  EmailTagRequest.newRequest();
        emailTagRequest.addEmailChannel("name@example.com")
                .setTags("my_fav_tag_group1", testTagSet )
                .setTags("my_fav_tag_group2", testTagSet )
                .setTags("my_fav_tag_group3", testTagSet );


        String jsonFromString = "{\n" +
                "   \"audience\": {\n" +
                "      \"email_address\": \"name@example.com\"\n" +
                "   },\n" +
                "   \"set\": {\n" +
                "      \"my_fav_tag_group1\": [\"tag1\", \"tag2\", \"tag3\"],\n" +
                "      \"my_fav_tag_group2\": [\"tag1\", \"tag2\", \"tag3\"],\n" +
                "      \"my_fav_tag_group3\": [\"tag1\", \"tag2\", \"tag3\"]\n" +
                "   }\n" +
                "}";

        JsonNode actual = MAPPER.readTree(emailTagRequest.getRequestBody());
        JsonNode expected = MAPPER.readTree(jsonFromString);

        assertEquals(expected, actual);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Test
    public void testEmptyEmailTagRequest() throws IOException {

        EmailTagRequest emailTagRequest =  EmailTagRequest.newRequest();
        emailTagRequest.addEmailChannel("name@example.com");

        thrown.expect(IllegalArgumentException.class);
    }
}
