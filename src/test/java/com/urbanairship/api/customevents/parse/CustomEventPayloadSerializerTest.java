package com.urbanairship.api.customevents.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.customevents.model.*;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomEventPayloadSerializerTest {

    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testFullPayload() throws IOException {
        CustomEventUser customEventUser = CustomEventUser.newBuilder()
                .setCustomEventChannelType(CustomEventChannelType.ANDROID_CHANNEL)
                .setChannel("e393d28e-23b2-4a22-9ace-dc539a5b07a8")
                .build();

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("category", "mens shoes");
        properties.put("id", "pid-11046546");
        properties.put("description", "sky high");
        properties.put("brand", "victory");

        CustomEventBody customEventBody = CustomEventBody.newBuilder()
                .setName("purchased")
                .setValue(new BigDecimal(120.49))
                .setTransaction("886f53d4-3e0f-46d7-930e-c2792dac6e0a")
                .setInteractionId("your.store/us/en_us/pd/shoe/pid-11046546/pgid-10978234")
                .setInteractionType("url")
                .addAllPropertyEntries(properties)
                .setSessionId("22404b07-3f8f-4e42-a4ff-a996c18fa9f1")
                .build();

        CustomEventOccurred customEventOccurred = CustomEventOccurred.newBuilder()
                .setOccurred("2015-05-02T02:31:22")
                .build();

        CustomEventPayload customEventPayload = CustomEventPayload.newBuilder()
                .setCustomEventOccurred(customEventOccurred)
                .setCustomEventBody(customEventBody)
                .setCustomEventUser(customEventUser)
                .build();

        String json = MAPPER.writeValueAsString(customEventPayload);
        String expected = "{\"occurred\": \"2015-05-02T02:31:22\",\"user\": {\"android_channel\": \"e393d28e-23b2-4a22-9ace-dc539a5b07a8\"},\"body\": {\"name\": \"purchased\",\"value\": 120.49,\"transaction\": \"886f53d4-3e0f-46d7-930e-c2792dac6e0a\",\"interaction_id\": \"your.store/us/en_us/pd/shoe/pid-11046546/pgid-10978234\",\"interaction_type\": \"url\",\"properties\": {\"category\": \"mens shoes\",\"id\": \"pid-11046546\",\"description\": \"sky high\",\"brand\": \"victory\"},\"session_id\": \"22404b07-3f8f-4e42-a4ff-a996c18fa9f1\"}}";

        JsonNode jsonFromObject = MAPPER.readTree(json);
        JsonNode jsonFromString = MAPPER.readTree(expected);


        assertEquals(jsonFromString, jsonFromObject);
    }

    @Test
    public void testCustomEventWithChannel() throws IOException {

        CustomEventOccurred customEventOccurred = CustomEventOccurred.newBuilder()
                .setOccurred("2016-05-02T02:31:22")
                .build();

        CustomEventUser customEventUser = CustomEventUser.newBuilder()
                .setCustomEventChannelType(CustomEventChannelType.ANDROID_CHANNEL)
                .setChannel("e393d28e-23b2-4a22-9ace-dc539a5b07a8")
                .build();

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("category", "mens shoes");
        properties.put("id", "pid-11046546");
        properties.put("description", "sky high");
        properties.put("brand", "victory");

        CustomEventBody customEventBody = CustomEventBody.newBuilder()
                .setName("purchased")
                .setValue(new BigDecimal(120.49))
                .setTransaction("886f53d4-3e0f-46d7-930e-c2792dac6e0a")
                .setInteractionId("your.store/us/en_us/pd/shoe/pid-11046546/pgid-10978234")
                .setInteractionType("url")
                .addAllPropertyEntries(properties)
                .setSessionId("22404b07-3f8f-4e42-a4ff-a996c18fa9f1")
                .build();

        CustomEventPayload customEventPayload = CustomEventPayload.newBuilder()
                .setCustomEventOccurred(customEventOccurred)
                .setCustomEventUser(customEventUser)
                .setCustomEventBody(customEventBody)
                .build();

        String json = MAPPER.writeValueAsString(customEventPayload);

        String expected = "[\n" +
                "   {\n" +
                "      \"occurred\": \"2016-05-02T02:31:22\",\n" +
                "      \"user\": {\n" +
                "         \"channel\": \"e393d28e-23b2-4a22-9ace-dc539a5b07a8\"\n" +
                "      },\n" +
                "      \"body\": {\n" +
                "         \"name\": \"purchased\",\n" +
                "         \"value\": 239.85,\n" +
                "         \"transaction\": \"886f53d4-3e0f-46d7-930e-c2792dac6e0a\",\n" +
                "         \"interaction_id\": \"your.store/us/en_us/pd/shoe/pid-11046546/pgid-10978234\",\n" +
                "         \"interaction_type\": \"url\",\n" +
                "         \"properties\": {\n" +
                "            \"description\": \"Sneaker purchase\",\n" +
                "            \"brand\": \"Victory Sneakers\",\n" +
                "            \"colors\": [\n" +
                "             \"red\",\n" +
                "             \"blue\"\n" +
                "            ],\n" +
                "            \"items\": [\n" +
                "               {\n" +
                "                  \"text\": \"New Line Sneakers\",\n" +
                "                  \"price\": \"$ 79.95\"\n" +
                "               },\n" +
                "               {\n" +
                "                  \"text\": \"Old Line Sneakers\",\n" +
                "                  \"price\": \"$ 79.95\"\n" +
                "               },\n" +
                "               {\n" +
                "                  \"text\": \"Blue Line Sneakers\",\n" +
                "                  \"price\": \"$ 79.95\"\n" +
                "               }\n" +
                "            ],\n" +
                "            \"name\": \"Hugh Manbeing\",\n" +
                "            \"userLocation\": {\n" +
                "               \"state\": \"CO\",\n" +
                "               \"zip\": \"80202\"\n" +
                "            }\n" +
                "         },\n" +
                "         \"session_id\": \"22404b07-3f8f-4e42-a4ff-a996c18fa9f1\"\n" +
                "      }\n" +
                "   }\n" +
                "]";

        JsonNode jsonFromObject = MAPPER.readTree(json);
        JsonNode jsonFromString = MAPPER.readTree(expected);

        assertNotNull(jsonFromString);
        assertEquals(jsonFromString, jsonFromObject);
    }

    @Test
    public void testChannelIdCustomEventUserObject() throws IOException {
        CustomEventUser customEventUser = CustomEventUser.newBuilder()
                .setCustomEventChannelType(CustomEventChannelType.ANDROID_CHANNEL)
                .setChannel("e393d28e-23b2-4a22-9ace-dc539a5b07a8")
                .build();

        String user_json = MAPPER.writeValueAsString(customEventUser);

        String expected = "[\n" +
                "   {\"user\": {\n" +
                "         \"channel\": \"e393d28e-23b2-4a22-9ace-dc539a5b07a8\"\n" +
                "      },\n" +
                "      \"body\": {\n" +
                        "\n}" +
                    "\n}" +
                "\n]";

        JsonNode user_jsonFromObject = MAPPER.readTree(user_json);
        JsonNode user_jsonFromString = MAPPER.readTree(expected);

        assertEquals(user_jsonFromString, user_jsonFromObject);
    }

    @Test
    public void testNamedUserCustomEventUser() throws IOException {
        CustomEventUser customEventUser = CustomEventUser.newBuilder()
                .setCustomEventChannelType(CustomEventChannelType.ANDROID_CHANNEL)
                .setNamedUser("hugh.manbeing")
                .build();

        String user_json = MAPPER.writeValueAsString(customEventUser);

        String expected = "[\n" +
                "   {\n" +
                "      \"occurred\": \"2016-05-02T02:31:22\",\n" +
                "      \"user\": {\n" +
                "         \"named_user_id\": \"hugh.manbeing\"\n" +
                "      },\n" +
                "      \"body\": {\n" +
                "\n}" +
                "\n}" +
                "\n]";

        JsonNode user_jsonFromObject = MAPPER.readTree(user_json);
        JsonNode user_jsonFromString = MAPPER.readTree(expected);

        assertEquals(user_jsonFromString, user_jsonFromObject);
    }

    @Test
    public void testChannelIdFullPayload() throws IOException {
        CustomEventUser customEventUser = CustomEventUser.newBuilder()
                .setCustomEventChannelType(CustomEventChannelType.ANDROID_CHANNEL)
                .setChannel("e393d28e-23b2-4a22-9ace-dc539a5b07a8")
                .build();

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("category", "mens shoes");
        properties.put("id", "pid-11046546");
        properties.put("description", "sky high");
        properties.put("brand", "victory");

        CustomEventOccurred customEventOccurred = CustomEventOccurred.newBuilder()
                .setOccurred("2016-05-02T02:31:22")
                .build();

        CustomEventBody customEventBody = CustomEventBody.newBuilder()
                .setName("purchased")
                .setValue(new BigDecimal(120.49))
                .setTransaction("886f53d4-3e0f-46d7-930e-c2792dac6e0a")
                .setInteractionId("your.store/us/en_us/pd/shoe/pid-11046546/pgid-10978234")
                .setInteractionType("url")
                .addAllPropertyEntries(properties)
                .setSessionId("22404b07-3f8f-4e42-a4ff-a996c18fa9f1")
                .build();

        CustomEventPayload customEventPayload = CustomEventPayload.newBuilder()
                .setCustomEventBody(customEventBody)
                .setCustomEventUser(customEventUser)
                .setCustomEventOccurred(customEventOccurred)
                .build();

        String json = MAPPER.writeValueAsString(customEventPayload);
        String expected = "[\n" +
                "   {\n" +
                "      \"occurred\": \"2016-05-02T02:31:22\",\n" +
                "      \"user\": {\n" +
                "         \"channel\": \"e393d28e-23b2-4a22-9ace-dc539a5b07a8\"\n" +
                "      },\n" +
                "      \"body\": {\n" +
                "         \"name\": \"purchased\",\n" +
                "         \"value\": 239.85,\n" +
                "         \"transaction\": \"886f53d4-3e0f-46d7-930e-c2792dac6e0a\",\n" +
                "         \"interaction_id\": \"your.store/us/en_us/pd/shoe/pid-11046546/pgid-10978234\",\n" +
                "         \"interaction_type\": \"url\",\n" +
                "         \"properties\": {\n" +
                "            \"description\": \"Sneaker purchase\",\n" +
                "            \"brand\": \"Victory Sneakers\",\n" +
                "            \"colors\": [\n" +
                "             \"red\",\n" +
                "             \"blue\"\n" +
                "            ],\n" +
                "            \"items\": [\n" +
                "               {\n" +
                "                  \"text\": \"New Line Sneakers\",\n" +
                "                  \"price\": \"$ 79.95\"\n" +
                "               },\n" +
                "               {\n" +
                "                  \"text\": \"Old Line Sneakers\",\n" +
                "                  \"price\": \"$ 79.95\"\n" +
                "               },\n" +
                "               {\n" +
                "                  \"text\": \"Blue Line Sneakers\",\n" +
                "                  \"price\": \"$ 79.95\"\n" +
                "               }\n" +
                "            ],\n" +
                "            \"name\": \"Hugh Manbeing\",\n" +
                "            \"userLocation\": {\n" +
                "               \"state\": \"CO\",\n" +
                "               \"zip\": \"80202\"\n" +
                "            }\n" +
                "         },\n" +
                "         \"session_id\": \"22404b07-3f8f-4e42-a4ff-a996c18fa9f1\"\n" +
                "      }\n" +
                "   }\n" +
                "]";

        JsonNode jsonFromObject = MAPPER.readTree(json);
        JsonNode jsonFromString = MAPPER.readTree(expected);


        assertEquals(jsonFromString, jsonFromObject);
    }

    @Test
    public void testNamedUserFullPayload() throws IOException {
        CustomEventUser customEventUser = CustomEventUser.newBuilder()
                .setCustomEventChannelType(CustomEventChannelType.ANDROID_CHANNEL)
                .setChannel("e393d28e-23b2-4a22-9ace-dc539a5b07a8")
                .build();

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("category", "mens shoes");
        properties.put("id", "pid-11046546");
        properties.put("description", "sky high");
        properties.put("brand", "victory");

        CustomEventBody customEventBody = CustomEventBody.newBuilder()
                .setName("purchased")
                .setValue(new BigDecimal(120.49))
                .setTransaction("886f53d4-3e0f-46d7-930e-c2792dac6e0a")
                .setInteractionId("your.store/us/en_us/pd/shoe/pid-11046546/pgid-10978234")
                .setInteractionType("url")
                .addAllPropertyEntries(properties)
                .setSessionId("22404b07-3f8f-4e42-a4ff-a996c18fa9f1")
                .build();

        CustomEventOccurred customEventOccurred = CustomEventOccurred.newBuilder()
                .setOccurred("2016-05-02T02:31:22")
                .build();

        CustomEventPayload customEventPayload = CustomEventPayload.newBuilder()
                .setCustomEventBody(customEventBody)
                .setCustomEventUser(customEventUser)
                .setCustomEventOccurred(customEventOccurred)
                .build();

        String json = MAPPER.writeValueAsString(customEventPayload);
        String expected = "[\n" +
                "   {\n" +
                "      \"occurred\": \"2016-05-02T02:31:22\",\n" +
                "      \"user\": {\n" +
                "         \"named_user_id\": \"hugh.manbeing\"\n" +
                "      },\n" +
                "      \"body\": {\n" +
                "         \"name\": \"purchased\",\n" +
                "         \"value\": 239.85,\n" +
                "         \"transaction\": \"886f53d4-3e0f-46d7-930e-c2792dac6e0a\",\n" +
                "         \"interaction_id\": \"your.store/us/en_us/pd/shoe/pid-11046546/pgid-10978234\",\n" +
                "         \"interaction_type\": \"url\",\n" +
                "         \"properties\": {\n" +
                "            \"description\": \"Sneaker purchase\",\n" +
                "            \"brand\": \"Victory Sneakers\",\n" +
                "            \"colors\": [\n" +
                "             \"red\",\n" +
                "             \"blue\"\n" +
                "            ],\n" +
                "            \"items\": [\n" +
                "               {\n" +
                "                  \"text\": \"New Line Sneakers\",\n" +
                "                  \"price\": \"$ 79.95\"\n" +
                "               },\n" +
                "               {\n" +
                "                  \"text\": \"Old Line Sneakers\",\n" +
                "                  \"price\": \"$ 79.95\"\n" +
                "               },\n" +
                "               {\n" +
                "                  \"text\": \"Blue Line Sneakers\",\n" +
                "                  \"price\": \"$ 79.95\"\n" +
                "               }\n" +
                "            ],\n" +
                "            \"name\": \"Hugh Manbeing\",\n" +
                "            \"userLocation\": {\n" +
                "               \"state\": \"CO\",\n" +
                "               \"zip\": \"80202\"\n" +
                "            }\n" +
                "         },\n" +
                "         \"session_id\": \"22404b07-3f8f-4e42-a4ff-a996c18fa9f1\"\n" +
                "      }\n" +
                "   }\n" +
                "]";

        JsonNode jsonFromObject = MAPPER.readTree(json);
        JsonNode jsonFromString = MAPPER.readTree(expected);


        assertEquals(jsonFromString, jsonFromObject);
    }

}
