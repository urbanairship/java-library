package com.urbanairship.api.channel.email.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.channel.model.email.OptInLevel;
import com.urbanairship.api.channel.model.email.RegisterEmailChannel;
import com.urbanairship.api.push.parse.PushObjectMapper;

import org.junit.Test;

import java.io.IOException;

public class EmailRegisterChannelPayloadSerializerTest {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testRegisterEmailChannellOptInPayload() throws IOException {

        for (OptInLevel level : OptInLevel.values()
        ) {

            RegisterEmailChannel registerEmailChannel = RegisterEmailChannel.newBuilder()
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
    public void testCreateAndSendRegisterEmailChannelPayload() throws IOException {

        for (OptInLevel level : OptInLevel.values()
        ) {

            RegisterEmailChannel registerEmailChannel = RegisterEmailChannel.newBuilder()
                    .setEmailOptInLevel(level, "2018-02-13T11:58:59")
                    .setUaAddress("name@example.com")
                    .setTimeZone("America/Los_Angeles")
                    .setLocaleCountry("US")
                    .setLocaleLanguage("en")
                    .build();

            String parsedJson = MAPPER.writeValueAsString(registerEmailChannel);
            String jsonString = String.format(" {\n" +
                    "     \"channel\" : {\n" +
                    "        \"type\": \"email\",\n" +
                    "        \"%s\": \"2018-02-13T11:58:59\",\n" +
                    "        \"ua_address\": \"name@example.com\",\n" +
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
