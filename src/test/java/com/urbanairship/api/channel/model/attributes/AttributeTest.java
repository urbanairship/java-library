package com.urbanairship.api.channel.model.attributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.urbanairship.api.channel.parse.ChannelObjectMapper;
import com.urbanairship.api.common.parse.DateFormats;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.*;

public class AttributeTest {

    private static final ObjectMapper MAPPER = ChannelObjectMapper.getInstance();

    @Test
    public void testStringValueAttribute() throws Exception {
        Attribute attr = Attribute.newBuilder()
                .setAction(AttributeAction.SET)
                .setKey("position")
                .setValue("LF")
                .build();

        String json = MAPPER.writeValueAsString(attr);
        JsonNode node = MAPPER.readTree(json);

        assertEquals("position", node.get("key").asText());
        assertEquals("set",      node.get("action").asText());
        assertTrue (node.has("value"));
        assertEquals("LF",       node.get("value").asText());
        assertFalse(node.has("timestamp"));
    }

    @Test
    public void testNumberValueAttribute() throws Exception {
        Attribute attr = Attribute.newBuilder()
                .setAction(AttributeAction.SET)
                .setKey("items")
                .setValue(20)
                .setTimeStamp(DateTime.now())
                .build();

        String json = MAPPER.writeValueAsString(attr);
        JsonNode node = MAPPER.readTree(json);

        assertEquals("items", node.get("key").asText());
        assertEquals("set",   node.get("action").asText());
        // number
        assertTrue(node.get("value").isInt());
        assertEquals(20, node.get("value").intValue());
        // timestamp present
        assertTrue(node.has("timestamp"));
        String ts = node.get("timestamp").asText();
        // doit être au format de DateFormats.DATE_FORMATTER
        DateFormats.DATE_FORMATTER.parseDateTime(ts);
    }

    @Test
    public void testJsonObjectValueAttribute() throws Exception {
        // on construit un ObjectNode avec exp, name, active, extras
        ObjectNode obj = MAPPER.createObjectNode();
        obj.put("exp",    1731531110);
        obj.put("name",   "LeftField");
        obj.put("rank",    2);
        obj.put("active", true);
        ArrayNode extras = obj.putArray("extras");
        extras.add("rookie");
        extras.add("mvp");

        Attribute attr = Attribute.newBuilder()
                .setAction(AttributeAction.SET)
                .setKey("position#instance_42")
                .setValue(obj)
                .build();

        String json = MAPPER.writeValueAsString(attr);
        JsonNode node = MAPPER.readTree(json);

        assertEquals("position#instance_42", node.get("key").asText());
        assertEquals("set",                   node.get("action").asText());

        JsonNode v = node.get("value");
        assertTrue(v.isObject());
        assertEquals(1731531110, v.get("exp").intValue());
        assertEquals("LeftField",   v.get("name").asText());
        assertEquals(2,              v.get("rank").intValue());
        assertTrue(v.get("active").booleanValue());
        // tableau extras
        JsonNode arr = v.get("extras");
        assertTrue(arr.isArray());
        assertEquals("rookie", arr.get(0).asText());
        assertEquals("mvp",    arr.get(1).asText());
    }

    @Test
    public void testRemoveAttribute() throws Exception {
        Attribute attr = Attribute.newBuilder()
                .setAction(AttributeAction.REMOVE)
                .setKey("old_key")
                .build();

        String json = MAPPER.writeValueAsString(attr);
        JsonNode node = MAPPER.readTree(json);

        assertEquals("old_key", node.get("key").asText());
        assertEquals("remove",  node.get("action").asText());
        assertFalse(node.has("value"));
        assertFalse(node.has("timestamp"));
    }
}
