package com.urbanairship.api.channel.sms.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.channel.model.sms.UpdateSmsChannel;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.IOException;

public class UpdateSmsChannelPayloadSerializerTest {
    private static final ObjectMapper MAPPER = ChannelObjectMapper.getInstance();

    @Test
    public void testUpdateSmsChannelOptInPayload() throws IOException {
        
            UpdateSmsChannel updateSmsChannel = UpdateSmsChannel.newBuilder()
                    .setMsisdn("13609048615")
                    .setSender("17372004196")
                    .setOptedIn(DateTime.parse("2021-10-11T02:03:03.000Z"))
                    .setLocaleCountry("US")
                    .setLocaleLanguage("en")
                    .setTimeZone("America/Los_Angeles")
                    .build();

            String parsedJson = MAPPER.writeValueAsString(updateSmsChannel);
            String jsonString = " {\n" +
                    "        \"msisdn\": \"13609048615\",\n" +
                    "        \"sender\": \"17372004196\",\n" +
                    "        \"opted_in\": \"2021-10-11T02:03:03\",\n" +
                    "        \"timezone\" : \"America/Los_Angeles\",\n" +
                    "        \"locale_country\" : \"US\",\n" +
                    "        \"locale_language\" : \"en\"\n" +
                    "     }\n";

            JsonNode actual = MAPPER.readTree(parsedJson);

            JsonNode expected = MAPPER.readTree(jsonString);

            org.junit.Assert.assertEquals(expected, actual);

    }
}
