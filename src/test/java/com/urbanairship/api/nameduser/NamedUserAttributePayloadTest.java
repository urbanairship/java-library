package com.urbanairship.api.nameduser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.channel.model.attributes.Attribute;
import com.urbanairship.api.channel.model.attributes.AttributeAction;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.nameduser.model.NamedUserAttributePayload;
import com.urbanairship.api.nameduser.parse.NamedUserObjectMapper;
import org.joda.time.DateTime;
import org.junit.Test;

public class NamedUserAttributePayloadTest {
    private static final ObjectMapper MAPPER = NamedUserObjectMapper.getInstance();

    @Test
    public void testPayload() throws Exception {
        DateTime now = DateTime.now();

        Attribute attribute = Attribute.newBuilder()
                .setAction(AttributeAction.SET)
                .setKey("birthday")
                .setValue(now)
                .setTimeStamp(now)
                .build();

        Attribute attribute1 = Attribute.newBuilder()
                .setAction(AttributeAction.SET)
                .setKey("items")
                .setValue(20)
                .setTimeStamp(now)
                .build();

        Attribute removeAttr = Attribute.newBuilder()
                .setAction(AttributeAction.REMOVE)
                .setKey("item")
                .build();

        NamedUserAttributePayload payload = NamedUserAttributePayload.newBuilder()
                .addAttribute(attribute)
                .addAttribute(attribute1)
                .addAttribute(removeAttr)
                .build();

        String expectedStr = "{\n" +
                "  \"attributes\": [\n" +
                "    {\n" +
                "      \"key\": \"birthday\",\n" +
                "      \"action\": \"set\",\n" +
                "      \"timestamp\": \"" + DateFormats.DATE_FORMATTER.print(now) + "\",\n" +
                "      \"value\": \"" + DateFormats.DATE_FORMATTER.print(now) + "\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"items\",\n" +
                "      \"action\": \"set\",\n" +
                "      \"timestamp\": \"" + DateFormats.DATE_FORMATTER.print(now) + "\",\n" +
                "      \"value\": \"20\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"item\",\n" +
                "      \"action\": \"remove\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        String jsonStr = payload.toJSON();

        JsonNode actualJson = MAPPER.readTree(jsonStr);
        JsonNode expectedJson = MAPPER.readTree(expectedStr);

        org.junit.Assert.assertEquals(expectedJson, actualJson);
    }
}
