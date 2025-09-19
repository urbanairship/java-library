package com.urbanairship.api.channel.email.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.channel.model.email.OptInLevel;
import com.urbanairship.api.channel.model.email.OptInMode;
import com.urbanairship.api.channel.model.email.RegisterEmailChannel;
import com.urbanairship.api.channel.model.email.TrackingOptInLevel;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailRegisterChannelPayloadSerializerTest {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testRegisterEmailChannelOptInPayload() throws IOException {

        for (OptInLevel level : OptInLevel.values()) {

            List<String> tags = new ArrayList<String>();
            tags.add("tag1");
            tags.add("tag2");
            tags.add("tag3");

            Map<String, List<String>> tagsMap = new HashMap<String, List<String>>();
            tagsMap.put("my_fav_tag_group3", tags);

            RegisterEmailChannel registerEmailChannel = RegisterEmailChannel.newBuilder()
                    .setEmailOptInLevel(level, "2018-02-13T11:58:59")
                    .setAddress("name@example.com")
                    .setTimeZone("America/Los_Angeles")
                    .setLocaleCountry("US")
                    .setLocaleLanguage("en")
                    .setEmailOptInMode(OptInMode.CLASSIC)
                    .addProperty("interests", "newsletter")
                    .addTag("my_fav_tag_group1", "tag1")
                    .addTag("my_fav_tag_group1", "tag2")
                    .addTag("my_fav_tag_group2", "tag3")
                    .addAllTags(tagsMap)
                    .addAttribute("my_fav_attribute1", "attribute1")
                    .addAttribute("my_fav_attribute2", "attribute2")
                    .build();

            String parsedJson = MAPPER.writeValueAsString(registerEmailChannel);
            String jsonString = String.format(" {\n" +
                    "     \"channel\" : {\n" +
                    "        \"type\": \"email\",\n" +
                    "        \"address\": \"name@example.com\",\n" +
                    "        \"%s\": \"2018-02-13T11:58:59\",\n" +
                    "        \"timezone\" : \"America/Los_Angeles\",\n" +
                    "        \"locale_country\" : \"US\",\n" +
                    "        \"locale_language\" : \"en\"\n" +
                    "        },\n" +
                    "        \"opt_in_mode\" : \"classic\",\n" +
                    "        \"properties\" : {\n" +
                    "           \"interests\" : \"newsletter\"\n" +
                    "        }\n" +
                    "        ,\n" +
                    "        \"tags\" : {\n" +
                    "           \"my_fav_tag_group1\" : [\"tag1\",\"tag2\"],\n" +
                    "           \"my_fav_tag_group2\" : [\"tag3\"],\n" +
                    "           \"my_fav_tag_group3\" : [\"tag1\",\"tag2\",\"tag3\"]\n" +
                    "        },\n" +
                    "        \"attributes\" : {\n" +
                    "           \"my_fav_attribute1\" : \"attribute1\",\n" +
                    "           \"my_fav_attribute2\" : \"attribute2\"\n" +
                    "        }\n" +
                    "  }", level.getIdentifier());

            JsonNode actual = MAPPER.readTree(parsedJson);

            JsonNode expected = MAPPER.readTree(jsonString);

            org.junit.Assert.assertEquals(expected, actual);
        }

    }

    @Test
    public void testCreateAndSendRegisterEmailChannelPayload() throws IOException {

        for (OptInLevel level : OptInLevel.values()) {
            RegisterEmailChannel registerEmailChannel = RegisterEmailChannel.newBuilder()
                    .setEmailOptInLevel(level, "2018-02-13T11:58:59")
                    .setTimeZone("America/Los_Angeles")
                    .setAddress("name@example.com")
                    .setLocaleCountry("US")
                    .setLocaleLanguage("en")
                    .build();

            String parsedJson = MAPPER.writeValueAsString(registerEmailChannel);
            String jsonString = String.format(" {\n" +
                    "     \"channel\" : {\n" +
                    "        \"type\": \"email\",\n" +
                    "        \"%s\": \"2018-02-13T11:58:59\",\n" +
                    "        \"address\": \"name@example.com\",\n" +
                    "        \"timezone\" : \"America/Los_Angeles\",\n" +
                    "        \"locale_country\" : \"US\",\n" +
                    "        \"locale_language\" : \"en\"\n" +
                    "     }\n" +
                    "  }", level.getIdentifier());

            JsonNode actual = MAPPER.readTree(parsedJson);
            JsonNode expected = MAPPER.readTree(jsonString);

            org.junit.Assert.assertEquals(expected, actual);
        }
    }

    @Test
    public void testRegisterEmailChannelTrackingOptInPayload() throws IOException {

        for (TrackingOptInLevel level : TrackingOptInLevel.values()) {

            RegisterEmailChannel registerEmailChannel = RegisterEmailChannel.newBuilder()
                    .setTrackingOptInLevel(level, "2018-02-13T11:58:59")
                    .setAddress("name@example.com")
                    .setTimeZone("America/Los_Angeles")
                    .setLocaleCountry("US")
                    .setLocaleLanguage("en")
                    .setEmailOptInMode(OptInMode.CLASSIC)
                    .addProperty("interests", "newsletter")
                    .build();

            String parsedJson = MAPPER.writeValueAsString(registerEmailChannel);
            String jsonString = String.format(" {\n" +
                    "     \"channel\" : {\n" +
                    "        \"type\": \"email\",\n" +
                    "        \"address\": \"name@example.com\",\n" +
                    "        \"%s\": \"2018-02-13T11:58:59\",\n" +
                    "        \"timezone\" : \"America/Los_Angeles\",\n" +
                    "        \"locale_country\" : \"US\",\n" +
                    "        \"locale_language\" : \"en\"\n" +
                    "        },\n" +
                    "        \"opt_in_mode\" : \"classic\",\n" +
                    "        \"properties\" : {\n" +
                    "           \"interests\" : \"newsletter\"\n" +
                    "        }\n" +
                    "  }", level.getIdentifier());

            JsonNode actual = MAPPER.readTree(parsedJson);
            JsonNode expected = MAPPER.readTree(jsonString);

            org.junit.Assert.assertEquals(expected, actual);
        }
    }

    @Test
    public void testCreateAndSendRegisterEmailChannelTrackingPayload() throws IOException {

        for (TrackingOptInLevel level : TrackingOptInLevel.values()) {
            RegisterEmailChannel registerEmailChannel = RegisterEmailChannel.newBuilder()
                    .setTrackingOptInLevel(level, "2018-02-13T11:58:59")
                    .setTimeZone("America/Los_Angeles")
                    .setAddress("name@example.com")
                    .setLocaleCountry("US")
                    .setLocaleLanguage("en")
                    .build();

            String parsedJson = MAPPER.writeValueAsString(registerEmailChannel);
            String jsonString = String.format(" {\n" +
                    "     \"channel\" : {\n" +
                    "        \"type\": \"email\",\n" +
                    "        \"%s\": \"2018-02-13T11:58:59\",\n" +
                    "        \"address\": \"name@example.com\",\n" +
                    "        \"timezone\" : \"America/Los_Angeles\",\n" +
                    "        \"locale_country\" : \"US\",\n" +
                    "        \"locale_language\" : \"en\"\n" +
                    "     }\n" +
                    "  }", level.getIdentifier());

            JsonNode actual = MAPPER.readTree(parsedJson);
            JsonNode expected = MAPPER.readTree(jsonString);

            org.junit.Assert.assertEquals(expected, actual);
        }
    }
}
