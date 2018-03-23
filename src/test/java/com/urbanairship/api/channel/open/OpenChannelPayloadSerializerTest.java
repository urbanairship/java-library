package com.urbanairship.api.channel.open;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.channel.model.open.Channel;
import com.urbanairship.api.channel.model.open.OpenChannel;
import com.urbanairship.api.channel.model.open.OpenChannelPayload;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class OpenChannelPayloadSerializerTest {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testOpenChannelPayload() throws IOException {
        OpenChannel openChannel = OpenChannel.newBuilder()
                .setOpenPlatformName("email")
                .setOldAddress("old_email@example.come")
                .addIdentifier("com.example.external_id", "df6a6b50-9843-7894-1235-12aed4489489")
                .build();

        Channel channel = Channel.newBuilder()
                .setOpenChannel(openChannel)
                .setChannelType(ChannelType.OPEN)
                .setOptIn(true)
                .setAddress("new_email@example.com")
                .setTags(true)
                .addTag("asdf")
                .setTimeZone("America/Los_Angeles")
                .setLocaleCountry("US")
                .setLocaleLanguage("en")
                .build();

        OpenChannelPayload payload = new OpenChannelPayload(channel);


        String parsedJson = MAPPER.writeValueAsString(payload);
        String jsonString = "{\n" +
                "    \"channel\": {\n" +
                "        \"type\": \"open\",\n" +
                "        \"address\": \"new_email@example.com\",\n" +
                "        \"open\": {\n" +
                "            \"open_platform_name\": \"email\",\n" +
                "            \"old_address\": \"old_email@example.come\",\n" +
                "            \"identifiers\": {\n" +
                "                \"com.example.external_id\": \"df6a6b50-9843-7894-1235-12aed4489489\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"opt_in\": true,\n" +
                "        \"set_tags\": true,\n" +
                "        \"tags\": [\n" +
                "            \"asdf\"\n" +
                "        ],\n" +
                "        \"timezone\": \"America/Los_Angeles\",\n" +
                "        \"locale_country\": \"US\",\n" +
                "        \"locale_language\": \"en\"\n" +
                "    }\n" +
                "}";

        JsonNode actual = MAPPER.readTree(parsedJson);
        JsonNode expected = MAPPER.readTree(jsonString);

        assertEquals(actual, expected);
    }
}
