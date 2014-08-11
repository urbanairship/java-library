package com.urbanairship.api.push.parse;

import com.google.common.base.Optional;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.Platform;
import com.urbanairship.api.push.model.PlatformData;
import com.urbanairship.api.push.model.PushOptions;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.adm.ADMDevicePayload;
import com.urbanairship.api.push.model.notification.android.AndroidDevicePayload;
import com.urbanairship.api.push.model.notification.blackberry.BlackberryDevicePayload;
import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;
import com.urbanairship.api.push.model.notification.mpns.MPNSDevicePayload;
import com.urbanairship.api.push.model.notification.richpush.RichPushMessage;
import com.urbanairship.api.push.model.notification.wns.WNSDevicePayload;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

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
        List<PushPayload> pushes = mapper.readValue(json, new TypeReference<List<PushPayload>>() {});
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
        List<PushPayload> pushes = mapper.readValue(json, new TypeReference<List<PushPayload>>() {});
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
        assertFalse(push.getOptions().isPresent());
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
        assertTrue(push.getOptions().isPresent());
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
        assertTrue(push.getPlatforms().isAll());
        assertFalse(push.getPlatforms().getPlatforms().isPresent());
    }

    @Test
    public void testDeviceTypesList() throws Exception {
        String json
            = "{"
            + "  \"audience\" : \"all\","
            + "  \"device_types\" : [\"ios\", \"android\", \"wns\", \"adm\"],"
            + "  \"notification\" : { \"alert\" : \"wat\" }"
            + "}";
        PushPayload push = mapper.readValue(json, PushPayload.class);
        assertFalse(push.getPlatforms().isAll());
        assertTrue(push.getPlatforms().getPlatforms().isPresent());
        Set<Platform> platforms = push.getPlatforms().getPlatforms().get();
        assertEquals(4, platforms.size());
        assertTrue(platforms.contains(Platform.IOS));
        assertTrue(platforms.contains(Platform.ANDROID));
        assertTrue(platforms.contains(Platform.WNS));
        assertTrue(platforms.contains(Platform.ADM));
    }

    @Test(expected=APIParsingException.class)
    public void testDeviceTypesInvalidString() throws Exception {
        String json
            = "{"
            + "  \"audience\" : \"all\","
            + "  \"device_types\" : \"ios\","
            + "  \"notification\" : { \"alert\" : \"wat\" }"
            + "}";
        mapper.readValue(json, PushPayload.class);
    }

    @Test(expected=APIParsingException.class)
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
            + "  \"device_types\" : [ \"ios\", \"wns\", \"mpns\", \"android\" ],"
            + "  \"notification\" : { "
            + "    \"alert\" : \"wat\","
            + "    \"ios\" : {"
            + "      \"alert\" : \"ios\""
            + "    },"
            + "    \"wns\" : {"
            + "      \"alert\" : \"wns\""
            + "    },"
            + "    \"mpns\" : {"
            + "      \"alert\" : \"mpns\""
            + "    },"
            + "    \"android\" : {"
            + "      \"alert\" : \"droid\""
            + "    },"
            + "    \"blackberry\" : {"
            + "      \"alert\" : \"doomed\""
            + "    },"
            + "    \"adm\" : {"
            + "      \"alert\" : \"phoenix\""
            + "    }"
            + "  }"
            + "}";
        PushPayload push = mapper.readValue(json, PushPayload.class);
        Notification notification = push.getNotification().get();
        assertTrue(notification.getPlatformOverride(Platform.IOS, IOSDevicePayload.class).isPresent());
        assertTrue(notification.getPlatformOverride(Platform.WNS, WNSDevicePayload.class).isPresent());
        assertTrue(notification.getPlatformOverride(Platform.MPNS, MPNSDevicePayload.class).isPresent());
        assertTrue(notification.getPlatformOverride(Platform.ANDROID, AndroidDevicePayload.class).isPresent());
        assertTrue(notification.getPlatformOverride(Platform.BLACKBERRY, BlackberryDevicePayload.class).isPresent());
        assertTrue(notification.getPlatformOverride(Platform.ADM, ADMDevicePayload.class).isPresent());

        IOSDevicePayload ios = notification.getPlatformOverride(Platform.IOS, IOSDevicePayload.class).get();
        assertTrue(ios.getAlert().isPresent());
        assertEquals("ios", ios.getAlert().get());

        WNSDevicePayload wns = notification.getPlatformOverride(Platform.WNS, WNSDevicePayload.class).get();
        assertTrue(wns.getAlert().isPresent());
        assertEquals("wns", wns.getAlert().get());

        MPNSDevicePayload mpns = notification.getPlatformOverride(Platform.MPNS, MPNSDevicePayload.class).get();
        assertTrue(mpns.getAlert().isPresent());
        assertEquals("mpns", mpns.getAlert().get());

        AndroidDevicePayload android = notification.getPlatformOverride(Platform.ANDROID, AndroidDevicePayload.class).get();
        assertTrue(android.getAlert().isPresent());
        assertEquals("droid", android.getAlert().get());

        BlackberryDevicePayload bb = notification.getPlatformOverride(Platform.BLACKBERRY, BlackberryDevicePayload.class).get();
        assertTrue(bb.getAlert().isPresent());
        assertEquals("doomed", bb.getAlert().get());

        ADMDevicePayload adm = notification.getPlatformOverride(Platform.ADM, ADMDevicePayload.class).get();
        assertTrue(adm.getAlert().isPresent());
        assertEquals("phoenix", adm.getAlert().get());
    }

    // TODO: split this into individual tests
    @Test
    public void testRoundTrip() throws Exception {

        PushPayload expected = PushPayload.newBuilder()
            .setAudience(Selectors.tag("derp"))
            .setNotification(Notification.newBuilder()
                             .setAlert(RandomStringUtils.randomAlphabetic(10))
                             .build()
                )
            .setPlatforms(PlatformData.newBuilder()
                          .addPlatform(Platform.WNS)
                          .build())
            .build();

        String serial = mapper.writeValueAsString(expected);
        PushPayload parsed = mapper.readValue(serial, PushPayload.class);

        assertEquals(expected,  parsed);

        expected = PushPayload.newBuilder()
            .setAudience(Selectors.tags("this", "that"))
            .setNotification(Notification.newBuilder()
                        .setAlert(RandomStringUtils.randomAlphabetic(10))
                        .build()
                )
            .setPlatforms(PlatformData.newBuilder()
                          .addPlatform(Platform.WNS)
                          .build())
            .build();

        serial = mapper.writeValueAsString(expected);
        parsed = mapper.readValue(serial, PushPayload.class);

        assertEquals(expected,  parsed);

        expected = PushPayload.newBuilder()
            .setAudience(Selectors.or(Selectors.alias("alpern"), Selectors.tag("wat")))
            .setNotification(Notification.newBuilder()
                             .setAlert(RandomStringUtils.randomAlphabetic(10))
                             .build())
            .setPlatforms(PlatformData.newBuilder()
                          .addPlatform(Platform.WNS)
                          .build())
            .build();

        serial = mapper.writeValueAsString(expected);
        parsed = mapper.readValue(serial, PushPayload.class);

        assertEquals(expected,  parsed);

        expected = PushPayload.newBuilder()
            .setAudience(Selectors.and(Selectors.tag("Beyonce"), Selectors.tag("GreenDay")))
            .setNotification(Notification.newBuilder()
                             .setAlert(RandomStringUtils.randomAlphabetic(10))
                             .build())
            .setPlatforms(PlatformData.newBuilder()
                          .addPlatform(Platform.WNS)
                          .build())
            .build();

        serial = mapper.writeValueAsString(expected);
        parsed = mapper.readValue(serial, PushPayload.class);

        assertEquals(expected,  parsed);
    }

    @Test(expected = APIParsingException.class)
    public void testInvalidDeviceIdentifiers() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
            .setAudience(Selectors.apid("wat"))
            .setNotification(Notification.newBuilder()
                             .setAlert("WAT")
                             .build())
            .setPlatforms(PlatformData.newBuilder()
                          .addPlatform(Platform.WNS)
                          .build())
            .build();
        mapper.readValue(mapper.writeValueAsString(payload), PushPayload.class);
    }

    @Test
    public void testValidDeviceIdentifiers() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
            .setAudience(Selectors.apid("6de14dab-a4e0-fe5b-06f7-f03b090e4a25"))
            .setNotification(Notification.newBuilder()
                             .setAlert("WAT")
                             .build())
            .setPlatforms(PlatformData.newBuilder()
                          .addPlatform(Platform.WNS)
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
