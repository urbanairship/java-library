package com.urbanairship.api.customevents.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomEventPayloadTest {

    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testBuilder() {
        CustomEventUser customEventUser = CustomEventUser.newBuilder()
                .setCustomEventChannelType(CustomEventChannelType.ANDROID_CHANNEL)
                .setChannel("e393d28e-23b2-4a22-9ace-dc539a5b07a8")
                .build();

        Map<String, CustomEventPropertyValue> properties = new HashMap<String, CustomEventPropertyValue>();
        properties.put("category", CustomEventPropertyValue.of("mens shoes"));
        properties.put("id", CustomEventPropertyValue.of("pid-11046546"));
        properties.put("description", CustomEventPropertyValue.of("sky high"));
        properties.put("brand", CustomEventPropertyValue.of("victory"));

        CustomEventBody customEventBody = CustomEventBody.newBuilder()
                .setName("purchased")
                .setValue(new BigDecimal(120.49))
                .setTransaction("886f53d4-3e0f-46d7-930e-c2792dac6e0a")
                .setInteractionId("your.store/us/en_us/pd/shoe/pid-11046546/pgid-10978234")
                .setInteractionType("url")
                .addAllPropertyEntries(properties)
                .setSessionId("22404b07-3f8f-4e42-a4ff-a996c18fa9f1")
                .build();

        DateTime occurred = new DateTime(2016, 5, 2, 2, 31, 22, DateTimeZone.UTC);

        CustomEventPayload customEventPayload = CustomEventPayload.newBuilder()
                .setOccurred(occurred)
                .setCustomEventUser(customEventUser)
                .setCustomEventBody(customEventBody)
                .build();

        assertTrue(customEventPayload.getCustomEventBody().getProperties().isPresent());
        assertEquals(4, customEventPayload.getCustomEventBody().getProperties().get().size());
        assertTrue(customEventPayload.getCustomEventBody().getProperties().get().containsKey("description"));
        assertEquals("victory", customEventPayload.getCustomEventBody().getProperties().get().get("brand").getAsString());

        assertEquals("purchased", customEventPayload.getCustomEventBody().getName());
        assertEquals(new BigDecimal(120.49), customEventPayload.getCustomEventBody().getValue().get());
        assertEquals("886f53d4-3e0f-46d7-930e-c2792dac6e0a", customEventPayload.getCustomEventBody().getTransaction().get());
        assertEquals("your.store/us/en_us/pd/shoe/pid-11046546/pgid-10978234", customEventPayload.getCustomEventBody().getInteractionId().get());
        assertEquals("url", customEventPayload.getCustomEventBody().getInteractionType().get());
        assertEquals("22404b07-3f8f-4e42-a4ff-a996c18fa9f1", customEventPayload.getCustomEventBody().getSessionId());

        assertEquals("e393d28e-23b2-4a22-9ace-dc539a5b07a8", customEventPayload.getCustomEventUser().getChannel().get());
        assertEquals(CustomEventChannelType.ANDROID_CHANNEL, customEventPayload.getCustomEventUser().getChannelType().get());

        assertEquals(occurred, customEventPayload.getOccurred());
   }

    @Test
    public void testScalarProperties() throws JsonProcessingException {
        CustomEventUser customEventUser = CustomEventUser.newBuilder()
                .setChannel("e393d28e-23b2-4a22-9ace-dc539a5b07a8")
                .setCustomEventChannelType(CustomEventChannelType.ANDROID_CHANNEL)
                .build();

        DateTime occurred = new DateTime(2015, 5, 2, 2, 31, 22, DateTimeZone.UTC);

        Map<String,CustomEventPropertyValue> properties = new HashMap<>();

        properties.put("amount", CustomEventPropertyValue.of(51));
        properties.put("isThisTrue", CustomEventPropertyValue.of(true));
        properties.put("name", CustomEventPropertyValue.of("Sally"));

        CustomEventBody customEventBody = CustomEventBody.newBuilder()
                .setName("purchased")
                .setSessionId("22404b07-3f8f-4e42-a4ff-a996c18fa9f1")
                .addAllPropertyEntries(properties)
                .build();

        CustomEventPayload customEventPayload = CustomEventPayload.newBuilder()
                .setOccurred(occurred)
                .setCustomEventBody(customEventBody)
                .setCustomEventUser(customEventUser)
                .build();

        assertEquals(customEventPayload.getCustomEventBody().getProperties().get().size(), 3);
        assertTrue(customEventPayload.getCustomEventBody().getProperties().get().get("amount").isNumber());
        assertEquals(51, customEventPayload.getCustomEventBody().getProperties().get().get("amount").getAsNumber());
        assertEquals(true, customEventPayload.getCustomEventBody().getProperties().get().get("isThisTrue").getAsBoolean());
        assertEquals("Sally", customEventPayload.getCustomEventBody().getProperties().get().get("name").getAsString());
        assertTrue(customEventPayload.getCustomEventBody().getProperties().get().get("isThisTrue").isBoolean());
        assertTrue(customEventPayload.getCustomEventBody().getProperties().get().get("name").isString());

        String json = MAPPER.writeValueAsString(customEventPayload);
        String expected = "{\"body\":{\"name\":\"purchased\",\"session_id\":\"22404b07-3f8f-4e42-a4ff-a996c18fa9f1\",\"properties\":{\"amount\":51,\"isThisTrue\":true,\"name\":\"Sally\"}},\"occurred\":\"2015-05-02T02:31:22\",\"user\":{\"android_channel\":\"e393d28e-23b2-4a22-9ace-dc539a5b07a8\"}}";

        JsonNode jsonFromObject = MAPPER.readTree(json);
        JsonNode jsonFromString = MAPPER.readTree(expected);

        assertEquals(jsonFromString, jsonFromObject);
    }

    @Test
    public void testArrayProperties() throws JsonProcessingException {
        CustomEventUser customEventUser = CustomEventUser.newBuilder()
                .setChannel("e393d28e-23b2-4a22-9ace-dc539a5b07a8")
                .setCustomEventChannelType(CustomEventChannelType.ANDROID_CHANNEL)
                .build();

        DateTime occurred = new DateTime(2015, 5, 2, 2, 31, 22, DateTimeZone.UTC);

        Map<String,CustomEventPropertyValue> properties = new HashMap<>();

        List<CustomEventPropertyValue> items = new ArrayList<>();
        items.add(CustomEventPropertyValue.of("la croix"));
        items.add(CustomEventPropertyValue.of("more la croix"));

        List<CustomEventPropertyValue> numbers = new ArrayList<>();
        numbers.add(CustomEventPropertyValue.of(1));
        numbers.add(CustomEventPropertyValue.of(22.23));
        numbers.add(CustomEventPropertyValue.of(0));

        Map<String, CustomEventPropertyValue> bootsMap = new HashMap<>();

        bootsMap.put("price", CustomEventPropertyValue.of(40));
        bootsMap.put("color", CustomEventPropertyValue.of("black"));
        bootsMap.put("waterproof", CustomEventPropertyValue.of(false));

        CustomEventPropertyValue boots = CustomEventPropertyValue.of(bootsMap);

        Map<String, CustomEventPropertyValue> sneakersMap = new HashMap<>();

        sneakersMap.put("price", CustomEventPropertyValue.of(10));
        sneakersMap.put("color", CustomEventPropertyValue.of("red"));
        sneakersMap.put("clearance", CustomEventPropertyValue.of(true));

        CustomEventPropertyValue sneakers = CustomEventPropertyValue.of(sneakersMap);

        List<CustomEventPropertyValue> shoes = new ArrayList<>();

        shoes.add(boots);
        shoes.add(sneakers);

        properties.put("items", CustomEventPropertyValue.of(items));
        properties.put("numbers", CustomEventPropertyValue.of(numbers));
        properties.put("shoes", CustomEventPropertyValue.of(shoes));

        CustomEventBody customEventBody = CustomEventBody.newBuilder()
                .setName("purchased")
                .setSessionId("22404b07-3f8f-4e42-a4ff-a996c18fa9f1")
                .addAllPropertyEntries(properties)
                .build();

        CustomEventPayload customEventPayload = CustomEventPayload.newBuilder()
                .setOccurred(occurred)
                .setCustomEventBody(customEventBody)
                .setCustomEventUser(customEventUser)
                .build();

        assertEquals(customEventPayload.getCustomEventBody().getProperties().get().size(), 3);
        customEventPayload.getCustomEventBody().getProperties().get().forEach(
                (property, propertyValue) -> assertTrue(propertyValue.isArray())
        );

        customEventPayload.getCustomEventBody().getProperties().get().get("items").getAsList().forEach(
                 propertyValue-> assertTrue(propertyValue.isString())
        );
        customEventPayload.getCustomEventBody().getProperties().get().get("numbers").getAsList().forEach(
                propertyValue -> assertTrue(propertyValue.isNumber())
        );
        customEventPayload.getCustomEventBody().getProperties().get().get("shoes").getAsList().forEach(
                propertyValue -> assertTrue(propertyValue.isObject())
        );

        customEventPayload.getCustomEventBody().getProperties().get().get("items").getAsList().forEach(
                propertyValue -> assertTrue(propertyValue.getAsString().equals("la croix") ||
                        propertyValue.getAsString().equals("more la croix")));

        customEventPayload.getCustomEventBody().getProperties().get().get("numbers").getAsList().forEach(
                propertyValue -> assertTrue(propertyValue.getAsNumber().equals(1) ||
                        propertyValue.getAsNumber().equals(22.23) ||
                        propertyValue.getAsNumber().equals(0)));

        customEventPayload.getCustomEventBody().getProperties().get().get("shoes").getAsList().forEach(
                propertyValue ->
                        assertTrue(propertyValue.getAsMap().get("color").getAsString().equals("black") ||
                                propertyValue.getAsMap().get("price").getAsNumber().equals(40) ||
                                (propertyValue.getAsMap().get("clearance").getAsBoolean() == false) ||
                                propertyValue.getAsMap().get("color").getAsString().equals("red") ||
                                propertyValue.getAsMap().get("price").getAsNumber().equals(10) ||
                                (propertyValue.getAsMap().get("clearance").getAsBoolean() == true)
                        )
        );

        String json = MAPPER.writeValueAsString(customEventPayload);
        String expected = "{\"body\":{\"name\":\"purchased\",\"session_id\":\"22404b07-3f8f-4e42-a4ff-a996c18fa9f1\",\"properties\":{\"numbers\":[1,22.23,0],\"items\":[\"la croix\",\"more la croix\"],\"shoes\":[{\"color\":\"black\",\"price\":40,\"waterproof\":false},{\"color\":\"red\",\"price\":10,\"clearance\":true}]}},\"occurred\":\"2015-05-02T02:31:22\",\"user\":{\"android_channel\":\"e393d28e-23b2-4a22-9ace-dc539a5b07a8\"}}";

        JsonNode jsonFromObject = MAPPER.readTree(json);
        JsonNode jsonFromString = MAPPER.readTree(expected);

        assertEquals(jsonFromString, jsonFromObject);
    }

    @Test
    public void testObjectProperties() throws JsonProcessingException {

        CustomEventUser customEventUser = CustomEventUser.newBuilder()
                .setChannel("e393d28e-23b2-4a22-9ace-dc539a5b07a8")
                .setCustomEventChannelType(CustomEventChannelType.ANDROID_CHANNEL)
                .build();

        DateTime occurred = new DateTime(2015, 5, 2, 2, 31, 22, DateTimeZone.UTC);

        Map<String, CustomEventPropertyValue> properties = new HashMap<>();

        Map<String, CustomEventPropertyValue> something = new HashMap<>();

        something.put("aThing", CustomEventPropertyValue.of("thing"));
        something.put("somethingElse", CustomEventPropertyValue.of("this thing"));

        Map<String, CustomEventPropertyValue> numbers = new HashMap<>();

        numbers.put("high", CustomEventPropertyValue.of(3));
        numbers.put("low", CustomEventPropertyValue.of(0));

        Map<String, CustomEventPropertyValue> booleans = new HashMap<>();

        booleans.put("testing", CustomEventPropertyValue.of(true));
        booleans.put("passing", CustomEventPropertyValue.of(false));

        properties.put("something", CustomEventPropertyValue.of(something));
        properties.put("numbers", CustomEventPropertyValue.of(numbers));
        properties.put("booleans", CustomEventPropertyValue.of(booleans));

        CustomEventBody customEventBody = CustomEventBody.newBuilder()
                .setName("purchased")
                .setSessionId("22404b07-3f8f-4e42-a4ff-a996c18fa9f1")
                .addAllPropertyEntries(properties)
                .build();

        CustomEventPayload customEventPayload = CustomEventPayload.newBuilder()
                .setOccurred(occurred)
                .setCustomEventBody(customEventBody)
                .setCustomEventUser(customEventUser)
                .build();

        assertEquals(customEventPayload.getCustomEventBody().getProperties().get().size(), 3);
        customEventPayload.getCustomEventBody().getProperties().get().forEach(
                (property, propertyValue) -> assertTrue(propertyValue.isObject())
        );
        customEventPayload.getCustomEventBody().getProperties().get().get("something").getAsMap().values().forEach(
                propertyValue -> assertTrue(propertyValue.isString())
        );
        assertEquals("thing", customEventPayload.getCustomEventBody().getProperties().get().get("something").getAsMap().get("aThing").getAsString()
        );
        assertEquals("this thing", customEventPayload.getCustomEventBody().getProperties().get().get("something").getAsMap().get("somethingElse").getAsString()
        );
        customEventPayload.getCustomEventBody().getProperties().get().get("numbers").getAsMap().values().forEach(
                propertyValue -> assertTrue(propertyValue.isNumber())
        );
        assertEquals(3, customEventPayload.getCustomEventBody().getProperties().get().get("numbers").getAsMap().get("high").getAsNumber()
        );
        assertEquals(0 , customEventPayload.getCustomEventBody().getProperties().get().get("numbers").getAsMap().get("low").getAsNumber()
        );
        assertEquals(false, customEventPayload.getCustomEventBody().getProperties().get().get("booleans").getAsMap().get("passing").getAsBoolean()
        );
        customEventPayload.getCustomEventBody().getProperties().get().get("booleans").getAsMap().values().forEach(
                propertyValue -> assertTrue(propertyValue.isBoolean())
        );
        assertEquals(true , customEventPayload.getCustomEventBody().getProperties().get().get("booleans").getAsMap().get("testing").getAsBoolean()
        );
        assertEquals(false, customEventPayload.getCustomEventBody().getProperties().get().get("booleans").getAsMap().get("passing").getAsBoolean()
        );

        String json = MAPPER.writeValueAsString(customEventPayload);
        String expected = "{\"body\":{\"name\":\"purchased\",\"session_id\":\"22404b07-3f8f-4e42-a4ff-a996c18fa9f1\",\"properties\":{\"numbers\":{\"high\":3,\"low\":0},\"booleans\":{\"testing\":true,\"passing\":false},\"something\":{\"aThing\":\"thing\",\"somethingElse\":\"this thing\"}}},\"occurred\":\"2015-05-02T02:31:22\",\"user\":{\"android_channel\":\"e393d28e-23b2-4a22-9ace-dc539a5b07a8\"}}";

        JsonNode jsonFromObject = MAPPER.readTree(json);
        JsonNode jsonFromString = MAPPER.readTree(expected);

        assertEquals(jsonFromString, jsonFromObject);
    }
}
