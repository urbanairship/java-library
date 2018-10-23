package com.urbanairship.api.channel.email;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.channel.model.email.OptInLevel;
import com.urbanairship.api.channel.model.email.RegisterEmailChannel;

import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class EmailRegisterChannelPayloadSerializerTest {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testRegisterEmailChannellPayload() throws IOException {

        RegisterEmailChannel channel = RegisterEmailChannel.newBuilder()
                .setEmailOptInLevel(OptInLevel.COMMERCIAL)
                .setAddress("name@example.com")
                .setTimeZone("America/Los_Angeles")
                .setLocaleCountry("US")
                .setLocaleLanguage("en")
                .build();


        String parsedJson = MAPPER.writeValueAsString(channel);
        String jsonString = " {\n" +
                "     \"channel\" : {\n" +
                "        \"type\": \"email\",\n" +
                "        \"email_opt_in_level\": \"commercial\",\n" +
                "        \"address\": \"name@example.com\",\n" +
                "        \"timezone\" : \"America/Los_Angeles\",\n" +
                "        \"locale_country\" : \"US\",\n" +
                "        \"locale_language\" : \"en\"\n" +
                "     }\n" +
                "  }";

        JsonNode actual = MAPPER.readTree(parsedJson);
        JsonNode expected = MAPPER.readTree(jsonString);

        assertEquals(actual, expected);
    }
}
