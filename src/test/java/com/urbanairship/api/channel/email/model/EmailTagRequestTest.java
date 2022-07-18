package com.urbanairship.api.channel.email.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.channel.model.email.EmailTagRequest;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class EmailTagRequestTest {
    private static final ObjectMapper MAPPER = ChannelObjectMapper.getInstance();

    Set<String> testTagSet;

    public void setup(){
    testTagSet = new TreeSet<>();
        for(int i =1; i < 4 ; i++) {
            testTagSet.add(String.format("tag%s", i));
        }
    }

    @Test
    public void testAddEmailTagRequest() throws IOException {

        setup();

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

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRemoveEmailTagRequest() throws IOException {

        setup();

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

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSetEmailTagRequest() throws IOException {
        setup();

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

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyEmailTagRequest() {

        EmailTagRequest emailTagRequest =  EmailTagRequest.newRequest();
        emailTagRequest.getRequestBody();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyAudienceTagRequest() {

        EmailTagRequest emailTagRequest =  EmailTagRequest.newRequest();
        emailTagRequest.addEmailChannel("name@example.com");
        emailTagRequest.getRequestBody();
    }

    @Test(expected = IllegalArgumentException.class)
    public void tesSetTagAndAddRequest() {

        EmailTagRequest emailTagRequest =  EmailTagRequest.newRequest();
        emailTagRequest.addEmailChannel("name@example.com")
                .setTags("my_fav_tag_group1", testTagSet )
                .addTags("my_fav_tag_group1", testTagSet );
        emailTagRequest.getRequestBody();
    }
}