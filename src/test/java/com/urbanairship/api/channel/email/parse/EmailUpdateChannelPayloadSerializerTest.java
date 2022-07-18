package com.urbanairship.api.channel.email.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.channel.model.email.OptInLevel;
import com.urbanairship.api.channel.model.email.UpdateEmailChannel;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import java.io.IOException;

public class EmailUpdateChannelPayloadSerializerTest {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testUpdateEmailChannelOptInPayload() throws IOException {

        for (OptInLevel level : OptInLevel.values()
        ) {

            UpdateEmailChannel registerEmailChannel = UpdateEmailChannel.newBuilder()
                    .setEmailOptInLevel(level, "2018-02-13T11:58:59")
                    .setAddress("name@example.com")
                    .setTimeZone("America/Los_Angeles")
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
                    "  }",level.getIdentifier());

            JsonNode actual = MAPPER.readTree(parsedJson);

            JsonNode expected = MAPPER.readTree(jsonString);

            org.junit.Assert.assertEquals(expected, actual);
        }

    }

    @Test
    public void testCreateAndSendUpdateEmailChannelPayload() throws IOException {

        for (OptInLevel level : OptInLevel.values()) {
            UpdateEmailChannel registerEmailChannel = UpdateEmailChannel.newBuilder()
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
                    "  }",level.getIdentifier());

            JsonNode actual = MAPPER.readTree(parsedJson);
            JsonNode expected = MAPPER.readTree(jsonString);

            org.junit.Assert.assertEquals(expected, actual);
        }
    }
}
