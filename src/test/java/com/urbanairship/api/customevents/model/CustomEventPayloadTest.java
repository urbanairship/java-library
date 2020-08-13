package com.urbanairship.api.customevents.model;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomEventPayloadTest {

    @Test
    public void testBuilder() {
        CustomEventUser customEventUser = CustomEventUser.newBuilder()
                .setCustomEventChannelType(CustomEventChannelType.ANDROID_CHANNEL)
                .setChannel("e393d28e-23b2-4a22-9ace-dc539a5b07a8")
                .build();

        Map<String, CustomEventPropValue> properties = new HashMap<String, CustomEventPropValue>();
        properties.put("category", CustomEventPropValue.of("mens shoes"));
        properties.put("id", CustomEventPropValue.of("pid-11046546"));
        properties.put("description", CustomEventPropValue.of("sky high"));
        properties.put("brand", CustomEventPropValue.of("victory"));

        CustomEventBody customEventBody = CustomEventBody.newBuilder()
                .setName("purchased")
                .setValue(new BigDecimal(120.49))
                .setTransaction("886f53d4-3e0f-46d7-930e-c2792dac6e0a")
                .setInteractionId("your.store/us/en_us/pd/shoe/pid-11046546/pgid-10978234")
                .setInteractionType("url")
                .addAllPropertyEntries(properties)
                .setSessionId("22404b07-3f8f-4e42-a4ff-a996c18fa9f1")
                .build();

        DateTime occurred = new DateTime(2015, 5, 2, 2, 31, 22, DateTimeZone.UTC);

        CustomEventPayload customEventPayload = CustomEventPayload.newBuilder()
                .setCustomEventBody(customEventBody)
                .setCustomEventUser(customEventUser)
                .setOccurred(occurred)
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

        assertEquals("e393d28e-23b2-4a22-9ace-dc539a5b07a8", customEventPayload.getCustomEventUser().getChannel());
        assertEquals(CustomEventChannelType.ANDROID_CHANNEL, customEventPayload.getCustomEventUser().getChannelType());

        assertEquals(occurred, customEventPayload.getOccurred());
   }

    @Test
    public void testScalarProperties() {

        CustomEventUser customEventUser = CustomEventUser.newBuilder()
                .setChannel("e393d28e-23b2-4a22-9ace-dc539a5b07a8")
                .setCustomEventChannelType(CustomEventChannelType.ANDROID_CHANNEL)
                .build();

        DateTime occurred = new DateTime(2015, 5, 2, 2, 31, 22, DateTimeZone.UTC);


        Map<String,CustomEventPropValue> properties = new HashMap<>();

        properties.put("amount", CustomEventPropValue.of(51));
        properties.put("isThisTrue", CustomEventPropValue.of(true));
        properties.put("name", CustomEventPropValue.of("Sally"));

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
        customEventPayload.getCustomEventBody().getProperties().get().forEach(
                (property, propValue) -> assertTrue(propValue.isScalar())
        );
        customEventPayload.getCustomEventBody().getProperties().get().forEach(
                (property, propValue) -> assertTrue(propValue.isPrimitive())
        );
    }

    @Test
    public void testArrayProperties() {
        CustomEventUser customEventUser = CustomEventUser.newBuilder()
                .setChannel("e393d28e-23b2-4a22-9ace-dc539a5b07a8")
                .setCustomEventChannelType(CustomEventChannelType.ANDROID_CHANNEL)
                .build();

        DateTime occurred = new DateTime(2015, 5, 2, 2, 31, 22, DateTimeZone.UTC);


        Map<String,CustomEventPropValue> properties = new HashMap<>();


        List<CustomEventPropValue> items = new ArrayList<>();
        items.add(CustomEventPropValue.of("la croix"));
        items.add(CustomEventPropValue.of("more la croix"));

        List<CustomEventPropValue> numbers = new ArrayList<>();
        numbers.add(CustomEventPropValue.of(1));
        numbers.add(CustomEventPropValue.of(22.23));
        numbers.add(CustomEventPropValue.of(0));

        Map<String, CustomEventPropValue> bootsMap = new HashMap<>();

        bootsMap.put("price", CustomEventPropValue.of(40));
        bootsMap.put("color", CustomEventPropValue.of("black"));
        bootsMap.put("waterproof", CustomEventPropValue.of(false));

        CustomEventPropValue boots = CustomEventPropValue.of(bootsMap);

        Map<String, CustomEventPropValue> sneakersMap = new HashMap<>();

        sneakersMap.put("price", CustomEventPropValue.of(10));
        sneakersMap.put("color", CustomEventPropValue.of("red"));
        sneakersMap.put("clearance", CustomEventPropValue.of(true));

        CustomEventPropValue sneakers = CustomEventPropValue.of(sneakersMap);

        List<CustomEventPropValue> shoes = new ArrayList<>();

        shoes.add(boots);
        shoes.add(sneakers);

        properties.put("items", CustomEventPropValue.of(items));
        properties.put("numbers", CustomEventPropValue.of(numbers));
        properties.put("shoes", CustomEventPropValue.of(shoes));

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
                (property, propValue) -> assertTrue(propValue.isArray())
        );

        customEventPayload.getCustomEventBody().getProperties().get().get("items").getAsList().forEach(
                 propValue-> assertTrue(propValue.isString())
        );
        customEventPayload.getCustomEventBody().getProperties().get().get("numbers").getAsList().forEach(
                propValue -> assertTrue(propValue.isNumber())
        );
        customEventPayload.getCustomEventBody().getProperties().get().get("shoes").getAsList().forEach(
                propValue -> assertTrue(propValue.isObject())
        );

        customEventPayload.getCustomEventBody().getProperties().get().get("items").getAsList().forEach(
                propValue -> assertTrue(propValue.getAsString().equals("la croix") ||
                        propValue.getAsString().equals("more la croix")));

        customEventPayload.getCustomEventBody().getProperties().get().get("numbers").getAsList().forEach(
                propValue -> assertTrue(propValue.getAsNumber().equals(1) ||
                        propValue.getAsNumber().equals(22.23) ||
                        propValue.getAsNumber().equals(0)));

       customEventPayload.getCustomEventBody().getProperties().get().get("shoes").getAsList().forEach(
                propValue ->
                        assertTrue(propValue.getAsMap().get("color").getAsString().equals("black") ||
                        propValue.getAsMap().get("price").getAsString().equals(40) ||
                        propValue.getAsMap().get("clearance").getAsString().equals(false) ||
                        propValue.getAsMap().get("color").getAsString().equals("red") ||
                        propValue.getAsMap().get("price").getAsString().equals(10) ||
                        propValue.getAsMap().get("clearance").getAsString().equals(true)));
    }

    @Test
    public void testObjectProperties() {

        CustomEventUser customEventUser = CustomEventUser.newBuilder()
                .setChannel("e393d28e-23b2-4a22-9ace-dc539a5b07a8")
                .setCustomEventChannelType(CustomEventChannelType.ANDROID_CHANNEL)
                .build();

        DateTime occurred = new DateTime(2015, 5, 2, 2, 31, 22, DateTimeZone.UTC);


        Map<String, CustomEventPropValue> properties = new HashMap<>();

        Map<String, CustomEventPropValue> something = new HashMap<>();

        something.put("aThing", CustomEventPropValue.of("thing"));
        something.put("somethingElse", CustomEventPropValue.of("this thing"));

        Map<String, CustomEventPropValue> numbers = new HashMap<>();

        numbers.put("high", CustomEventPropValue.of(3));
        numbers.put("low", CustomEventPropValue.of(0));

        Map<String, CustomEventPropValue> booleans = new HashMap<>();

        booleans.put("testing", CustomEventPropValue.of(true));
        booleans.put("passing", CustomEventPropValue.of(false));

        properties.put("something", CustomEventPropValue.of(something));
        properties.put("numbers", CustomEventPropValue.of(numbers));
        properties.put("booleans", CustomEventPropValue.of(booleans));

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
                (property, propValue) -> assertTrue(propValue.isObject())
        );
        customEventPayload.getCustomEventBody().getProperties().get().get("something").getAsMap().values().forEach(
                propValue -> assertTrue(propValue.isString())
        );
        customEventPayload.getCustomEventBody().getProperties().get().get("numbers").getAsMap().values().forEach(
                propValue -> assertTrue(propValue.isNumber())
        );
        customEventPayload.getCustomEventBody().getProperties().get().get("booleans").getAsMap().values().forEach(
                propValue -> assertTrue(propValue.isBoolean())
        );
    }
}
