package com.urbanairship.api.channel.email.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.channel.model.email.EmailTagRequest;
import com.urbanairship.api.channel.model.email.UninstallEmailChannel;
import com.urbanairship.api.channel.model.email.UninstallEmailChannelRequest;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class EmailChannelUninstallRequestTest {

    private static final ObjectMapper MAPPER = ChannelObjectMapper.getInstance();

    @Test
    public void testUninstallEmailRequest() throws IOException {

        UninstallEmailChannel uninstallEmailChannel = UninstallEmailChannel.newBuilder()
                .setEmailAddress("name@example.com")
                .build();

        UninstallEmailChannelRequest uninstallRequest = UninstallEmailChannelRequest.newRequest(uninstallEmailChannel);

        String jsonFromString = "\n" +
                "{\n" +
                "    \"email_address\": \"name@example.com\"\n" +
                "}";

        JsonNode actual = MAPPER.readTree(uninstallEmailChannel.toJSON());
        System.out.println(actual);

        JsonNode expected = null;
        try {
            expected = MAPPER.readTree(jsonFromString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(expected, actual);
    }
}