package com.urbanairship.api.channel.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.channel.model.attributes.Attribute;
import com.urbanairship.api.channel.model.attributes.AttributeAction;
import com.urbanairship.api.common.parse.DateFormats;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AttributeSerializationTest {

    private static ObjectMapper MAPPER;

    @BeforeClass
    public static void setup() {
        MAPPER = ChannelObjectMapper.getInstance();
    }

    @Test
    public void testSerializeStringAttribute() throws Exception {
        Attribute attr = Attribute.newBuilder()
                .setAction(AttributeAction.SET)
                .setKey("position")
                .setValue("LF")
                .build();

        String expected = "{\n" +
                "  \"key\": \"position\",\n" +
                "  \"action\": \"set\",\n" +
                "  \"value\": \"LF\"\n" +
                "}";
        JsonNode expectedJson = MAPPER.readTree(expected);
        JsonNode actualJson = MAPPER.readTree(MAPPER.writeValueAsString(attr));

        assertEquals(expectedJson, actualJson);
    }

    @Test
    public void testSerializeNumberAndTimestampAttribute() throws Exception {
        DateTime now = DateTime.now();
        Attribute attr = Attribute.newBuilder()
                .setAction(AttributeAction.SET)
                .setKey("items")
                .setValue(20)
                .setTimeStamp(now)
                .build();

        String expected = "{\n" +
                "  \"key\": \"items\",\n" +
                "  \"action\": \"set\",\n" +
                "  \"timestamp\": \"" + DateFormats.DATE_FORMATTER.print(now) + "\",\n" +
                "  \"value\": 20\n" +
                "}";
        JsonNode expectedJson = MAPPER.readTree(expected);
        JsonNode actualJson = MAPPER.readTree(MAPPER.writeValueAsString(attr));

        assertEquals(expectedJson, actualJson);
    }

    @Test
    public void testSerializeJsonObjectAttribute() throws Exception {
        JsonNode obj = MAPPER.createObjectNode()
                .put("exp", 1731531110)
                .put("name", "LeftField")
                .put("rank", 2)
                .put("active", true)
                .set("extras", MAPPER.createArrayNode().add("rookie").add("mvp"));

        Attribute attr = Attribute.newBuilder()
                .setAction(AttributeAction.SET)
                .setKey("position#instance_42")
                .setValue(obj)
                .build();

        String expected = "{\n" +
                "  \"key\": \"position#instance_42\",\n" +
                "  \"action\": \"set\",\n" +
                "  \"value\": {\n" +
                "    \"exp\": 1731531110,\n" +
                "    \"name\": \"LeftField\",\n" +
                "    \"rank\": 2,\n" +
                "    \"active\": true,\n" +
                "    \"extras\": [\"rookie\",\"mvp\"]\n" +
                "  }\n" +
                "}";
        JsonNode expectedJson = MAPPER.readTree(expected);
        JsonNode actualJson = MAPPER.readTree(MAPPER.writeValueAsString(attr));

        assertEquals(expectedJson, actualJson);
    }
}
