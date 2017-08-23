package com.urbanairship.api.push.parse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.InApp;
import com.urbanairship.api.push.model.Position;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.adm.ADMDevicePayload;
import com.urbanairship.api.push.model.notification.android.AndroidDevicePayload;
import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;
import com.urbanairship.api.push.model.notification.wns.WNSDevicePayload;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PushPayloadBasicSerializationTest {

    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testArrayOfPushes() throws Exception {
        String json = "["
                + "  { "
                + "    \"audience\" : \"all\","
                + "    \"device_types\" : [ \"ios\" ],"
                + "    \"notification\" : { \"alert\" : \"wat\" }"
                + "  },"
                + "  { "
                + "    \"audience\" : \"all\","
                + "    \"device_types\" : [ \"ios\" ],"
                + "    \"notification\" : { \"alert\" : \"derp\" }"
                + "  }"
                + "]";
        List<PushPayload> pushes = mapper.readValue(json, new TypeReference<List<PushPayload>>() {
        });
        assertNotNull(pushes);
        assertEquals(2, pushes.size());
    }

    @Test
    public void testSinglePushAsList() throws Exception {
        String json
                = "{"
                + "  \"audience\" : \"all\","
                + "  \"device_types\" : [ \"ios\" ],"
                + "  \"notification\" : { \"alert\" : \"wat\" }"
                + "}";
        List<PushPayload> pushes = mapper.readValue(json, new TypeReference<List<PushPayload>>() {
        });
        assertNotNull(pushes);
        assertEquals(1, pushes.size());
    }

    @Test
    public void testNoOptions() throws Exception {
        String json
                = "{"
                + "  \"audience\" : \"all\","
                + "  \"device_types\" : [ \"ios\" ],"
                + "  \"notification\" : { \"alert\" : \"wat\" }"
                + "}";
        PushPayload push = mapper.readValue(json, PushPayload.class);
        assertFalse(push.getPushOptions().isPresent());
    }

    @Test
    public void testOptions() throws Exception {
        String json
                = "{"
                + "  \"audience\" : \"all\","
                + "  \"device_types\" : [ \"ios\" ],"
                + "  \"notification\" : { \"alert\" : \"wat\" },"
                + "  \"options\" : {"
                + "  }"
                + "}";
        PushPayload push = mapper.readValue(json, PushPayload.class);
        assertTrue(push.getPushOptions().isPresent());
    }

    @Test
    public void testDeviceTypesAll() throws Exception {
        String json
                = "{"
                + "  \"audience\" : \"all\","
                + "  \"device_types\" : \"all\","
                + "  \"notification\" : { \"alert\" : \"wat\" }"
                + "}";
        PushPayload push = mapper.readValue(json, PushPayload.class);
        assertTrue(push.getDeviceTypes().isAll());
        assertFalse(push.getDeviceTypes().getDeviceTypes().isPresent());
    }

    @Test
    public void testDeviceTypesList() throws Exception {
        String json
                = "{"
                + "  \"audience\" : \"all\","
                + "  \"device_types\" : [\"ios\", \"android\", \"wns\", \"amazon\"],"
                + "  \"notification\" : { \"alert\" : \"wat\" }"
                + "}";
        PushPayload push = mapper.readValue(json, PushPayload.class);
        assertFalse(push.getDeviceTypes().isAll());
        assertTrue(push.getDeviceTypes().getDeviceTypes().isPresent());
        Set<DeviceType> deviceTypes = push.getDeviceTypes().getDeviceTypes().get();
        assertEquals(4, deviceTypes.size());
        assertTrue(deviceTypes.contains(DeviceType.IOS));
        assertTrue(deviceTypes.contains(DeviceType.ANDROID));
        assertTrue(deviceTypes.contains(DeviceType.WNS));
        assertTrue(deviceTypes.contains(DeviceType.AMAZON));
    }

    @Test(expected = APIParsingException.class)
    public void testDeviceTypesInvalidString() throws Exception {
        String json
                = "{"
                + "  \"audience\" : \"all\","
                + "  \"device_types\" : \"ios\","
                + "  \"notification\" : { \"alert\" : \"wat\" }"
                + "}";
        mapper.readValue(json, PushPayload.class);
    }

    @Test(expected = JsonMappingException.class)
    public void testDeviceTypesAllInList() throws Exception {
        String json
                = "{"
                + "  \"audience\" : \"all\","
                + "  \"device_types\" : [\"ios\",\"all\"],"
                + "  \"notification\" : { \"alert\" : \"wat\" }"
                + "}";
        mapper.readValue(json, PushPayload.class);
    }

    @Test
    public void testRichPush1() throws Exception {
        String json
                = "{"
                + "  \"audience\" : \"all\","
                + "  \"device_types\" : [\"ios\"],"
                + "  \"message\" : { \"title\" : \"T\", \"body\" : \"B\" }"
                + "}";
        PushPayload payload = mapper.readValue(json, PushPayload.class);
        assertTrue(payload.getMessage().isPresent());
        assertFalse(payload.getNotification().isPresent());
        RichPushMessage m = payload.getMessage().get();
        assertEquals(m.getTitle(), "T");
        assertEquals(m.getBody(), "B");
    }

    @Test
    public void testPlatformOverrides() throws Exception {
        String json
                = "{"
                + "  \"audience\" : \"all\","
                + "  \"device_types\" : [ \"ios\", \"wns\", \"android\" ],"
                + "  \"notification\" : { "
                + "    \"alert\" : \"wat\","
                + "    \"ios\" : {"
                + "      \"alert\" : \"ios\""
                + "    },"
                + "    \"wns\" : {"
                + "      \"alert\" : \"wns\""
                + "    },"
                + "    \"android\" : {"
                + "      \"alert\" : \"droid\""
                + "    },"
                + "    \"amazon\" : {"
                + "      \"alert\" : \"phoenix\""
                + "    }"
                + "  }"
                + "}";
        PushPayload push = mapper.readValue(json, PushPayload.class);
        Notification notification = push.getNotification().get();
        assertTrue(notification.getDeviceTypeOverride(DeviceType.IOS, IOSDevicePayload.class).isPresent());
        assertTrue(notification.getDeviceTypeOverride(DeviceType.WNS, WNSDevicePayload.class).isPresent());
        assertTrue(notification.getDeviceTypeOverride(DeviceType.ANDROID, AndroidDevicePayload.class).isPresent());
        assertTrue(notification.getDeviceTypeOverride(DeviceType.AMAZON, ADMDevicePayload.class).isPresent());

        IOSDevicePayload ios = notification.getDeviceTypeOverride(DeviceType.IOS, IOSDevicePayload.class).get();
        assertTrue(ios.getAlert().isPresent());
        assertEquals("ios", ios.getAlert().get());

        WNSDevicePayload wns = notification.getDeviceTypeOverride(DeviceType.WNS, WNSDevicePayload.class).get();
        assertTrue(wns.getAlert().isPresent());
        assertEquals("wns", wns.getAlert().get());

        AndroidDevicePayload android = notification.getDeviceTypeOverride(DeviceType.ANDROID, AndroidDevicePayload.class).get();
        assertTrue(android.getAlert().isPresent());
        assertEquals("droid", android.getAlert().get());

        ADMDevicePayload adm = notification.getDeviceTypeOverride(DeviceType.AMAZON, ADMDevicePayload.class).get();
        assertTrue(adm.getAlert().isPresent());
        assertEquals("phoenix", adm.getAlert().get());
    }

    @Test
    public void testInAppMessage() throws Exception {
        String json = "{" +
                "\"audience\": \"all\"," +
                "\"device_types\": [\"ios\", \"android\", \"amazon\"]," +
                "\"notification\": {" +
                    "\"alert\": \"test alert test test\"" +
                "}," +
                "\"in_app\": {" +
                    "\"alert\": \"This part appears in-app!\"," +
                    "\"display_type\": \"banner\"," +
                    "\"display\": {" +
                        "\"position\": \"top\"" +
                    "}" +
                "}" +
                "}";

        PushPayload push = mapper.readValue(json, PushPayload.class);
        assertNotNull(push);
        assertEquals(push.getAudience(), Selectors.all());
        assertEquals(push.getDeviceTypes(), DeviceTypeData.of(DeviceType.IOS,DeviceType.AMAZON,DeviceType.ANDROID));

        Notification notification = push.getNotification().get();
        assertNotNull(notification);
        assertEquals(notification.getAlert().get(), "test alert test test");

        InApp inAppMessage = push.getInApp().get();
        assertNotNull(inAppMessage);
        assertEquals(inAppMessage.getAlert(), "This part appears in-app!");
        assertEquals(inAppMessage.getDisplayType(), "banner");
        assertEquals(inAppMessage.getDisplay().get().getPosition().get(), Position.TOP);
    }


    // TODO: split this into individual tests
    @Test
    public void testRoundTrip() throws Exception {

        PushPayload expected = PushPayload.newBuilder()
                .setAudience(Selectors.tag("tag1"))
                .setNotification(Notification.newBuilder()
                                .setAlert(RandomStringUtils.randomAlphabetic(10))
                                .build()
                )
                .setDeviceTypes(DeviceTypeData.newBuilder()
                        .addDeviceType(DeviceType.WNS)
                        .build())
                .build();

        String serial = mapper.writeValueAsString(expected);
        PushPayload parsed = mapper.readValue(serial, PushPayload.class);

        assertEquals(expected, parsed);

        expected = PushPayload.newBuilder()
                .setAudience(Selectors.tags("this", "that"))
                .setNotification(Notification.newBuilder()
                                .setAlert(RandomStringUtils.randomAlphabetic(10))
                                .build()
                )
                .setDeviceTypes(DeviceTypeData.newBuilder()
                        .addDeviceType(DeviceType.IOS)
                        .build())
                .build();

        serial = mapper.writeValueAsString(expected);
        parsed = mapper.readValue(serial, PushPayload.class);

        assertEquals(expected, parsed);

        expected = PushPayload.newBuilder()
                .setAudience(Selectors.or(Selectors.alias("alias1"), Selectors.tag("tag1")))
                .setNotification(Notification.newBuilder()
                        .setAlert(RandomStringUtils.randomAlphabetic(10))
                        .build())
                .setDeviceTypes(DeviceTypeData.newBuilder()
                        .addDeviceType(DeviceType.ANDROID)
                        .build())
                .build();

        serial = mapper.writeValueAsString(expected);
        parsed = mapper.readValue(serial, PushPayload.class);

        assertEquals(expected, parsed);

        expected = PushPayload.newBuilder()
                .setAudience(Selectors.and(Selectors.tag("tag1"), Selectors.tag("tag2")))
                .setNotification(Notification.newBuilder()
                        .setAlert(RandomStringUtils.randomAlphabetic(10))
                        .build())
                .setDeviceTypes(DeviceTypeData.newBuilder()
                        .addDeviceType(DeviceType.AMAZON)
                        .build())
                .build();

        serial = mapper.writeValueAsString(expected);
        parsed = mapper.readValue(serial, PushPayload.class);

        assertEquals(expected, parsed);

        expected = PushPayload.newBuilder()
            .setAudience(Selectors.or(Selectors.tagWithGroup("tag1", "group1"), Selectors.tagWithGroup("tag2", "group2")))
            .setNotification(Notification.newBuilder()
                .setAlert(RandomStringUtils.randomAlphabetic(10))
                .build())
            .setDeviceTypes(DeviceTypeData.newBuilder()
                .addDeviceType(DeviceType.IOS)
                .build())
            .build();

        serial = mapper.writeValueAsString(expected);
        parsed = mapper.readValue(serial, PushPayload.class);

        assertEquals(expected, parsed);
    }

    @Test(expected = APIParsingException.class)
    public void testInvalidDeviceIdentifiers() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setAudience(Selectors.apid("apid1"))
                .setNotification(Notification.newBuilder()
                        .setAlert(RandomStringUtils.randomAlphabetic(10))
                        .build())
                .setDeviceTypes(DeviceTypeData.newBuilder()
                        .addDeviceType(DeviceType.WNS)
                        .build())
                .build();
        mapper.readValue(mapper.writeValueAsString(payload), PushPayload.class);
    }

    @Test
    public void testValidDeviceIdentifiers() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setAudience(Selectors.apid("6de14dab-a4e0-fe5b-06f7-f03b090e4a25"))
                .setNotification(Notification.newBuilder()
                        .setAlert(RandomStringUtils.randomAlphabetic(10))
                        .build())
                .setDeviceTypes(DeviceTypeData.newBuilder()
                        .addDeviceType(DeviceType.WNS)
                        .build())
                .build();
        mapper.readValue(mapper.writeValueAsString(payload), PushPayload.class);
    }

    @Test(expected = APIParsingException.class)
    public void testInvalidJson() throws Exception {
        String json = "[{\"device_tokens\": [\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4733\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"(null)\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"fakeTokenTest\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"(null)\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"(null)\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8ad9473703d48713777a21ea7ff5ca10cf4a038ea88717ee62e1e796953d4734\",\"8ad9473703d48713777a21ea7ff5ca10cf4a038ea88717ee62e1e796953d4734\",\"8ad9473703d48713777a21ea7ff5ca10cf4a038ea88717ee62e1e796953d4734\",\"8ad9473703d48713777a21ea7ff5ca10cf4a038ea88717ee62e1e796953d4734\",\"8ad9473703d48713777a21ea7ff5ca10cf4a038ea88717ee62e1e796953d4734\",\"8ad9473703d48713777a21ea7ff5ca10cf4a038ea88717ee62e1e796953d4734\",\"8ad9473703d48713777a21ea7ff5ca10cf4a038ea88717ee62e1e796953d4734\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\",\"8adc473703d48713777a21ea7445ca10cf4a038eaf87170062e1e796953d4735\"],\"aps\": {  \"alert\":{\"loc-key\":\"NCA\", \"loc-args\":\"62\"},\"sound\": \"cat.caf\" },\"vstb\":{\"loc-key\":\"NCA\", \"loc-args\":\"62\"}}]";

        mapper.readValue(json, PushPayload.class);
    }

    @Test(expected = APIParsingException.class)
    public void testEmptyArray() throws Exception {
        mapper.readValue("[]", PushPayload.class);
    }

    @Test(expected = APIParsingException.class)
    public void testEmptyObject() throws Exception {
        mapper.readValue("{}", PushPayload.class);
    }

    @Test(expected = IOException.class)
    public void testEmptyString() throws Exception {
        mapper.readValue("", PushPayload.class);
    }

    @Test(expected = APIParsingException.class)
    public void testArrayWithSingleObject() throws Exception {
        mapper.readValue("[{\"what\":\"for\"}]", PushPayload.class);
    }
}
