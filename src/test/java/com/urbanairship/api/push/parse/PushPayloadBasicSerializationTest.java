package com.urbanairship.api.push.parse;

import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.Options;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

@Ignore("Ignore until refactored")
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
        List<Object> pushes = mapper.readValue(json, new TypeReference<List<Object>>() {});
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
        List<Object> pushes = mapper.readValue(json, new TypeReference<List<Object>>() {});
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

    }

    @Test
    public void testOptions() throws Exception {
        // TODO: Fix documentation. As of Oct 9, 2013 the documentation for expiry at http://docs.urbanairship.com/reference/api/v3/push.html#expiry is incorrect. The options in the documentation are within the notification, which does not work. The options should be at the same level as notification, audience, device_types, etc.
        String json
            = "{"
            + "  \"audience\" : \"all\","
            + "  \"device_types\" : [ \"ios\" ],"
            + "  \"notification\" : { \"alert\" : \"wat\" },"
            + "  \"options\" : { \"expiry\" : \"2015-04-01T12:00:00\" }"
            + "}";

        // TODO: what is the test supposed to be?
    }

    @Test
    public void testOptionsExpiryInSeconds() throws Exception {
        // TODO: Fix documentation. As of Oct 9, 2013 the documentation for expiry at http://docs.urbanairship.com/reference/api/v3/push.html#expiry is incorrect. The options in the documentation are within the notification, which does not work. The options should be at the same level as notification, audience, device_types, etc.
        String json
            = "{"
            + "  \"audience\" : \"all\","
            + "  \"device_types\" : [ \"ios\" ],"
            + "  \"notification\" : { \"alert\" : \"wat\" },"
            + "  \"options\" : { \"expiry\" : 3600 }"
            + "}";

        // TODO: what is the test supposed to be?
    }

    @Test
    public void testDeviceTypesAll() throws Exception {
        String json
            = "{"
            + "  \"audience\" : \"all\","
            + "  \"device_types\" : \"all\","
            + "  \"notification\" : { \"alert\" : \"wat\" }"
            + "}";

    }

    @Test
    public void testDeviceTypesList() throws Exception {
        String json
            = "{"
            + "  \"audience\" : \"all\","
            + "  \"device_types\" : [\"ios\", \"android\", \"wns\", \"adm\"],"
            + "  \"notification\" : { \"alert\" : \"wat\" }"
            + "}";

    }

//    @Test(expected=APIParsingException.class)
//    public void testDeviceTypesInvalidString() throws Exception {
//        String json
//            = "{"
//            + "  \"audience\" : \"all\","
//            + "  \"device_types\" : \"ios\","
//            + "  \"notification\" : { \"alert\" : \"wat\" }"
//            + "}";
//    }
//
//    @Test(expected=APIParsingException.class)
//    public void testDeviceTypesAllInList() throws Exception {
//        String json
//            = "{"
//            + "  \"audience\" : \"all\","
//            + "  \"device_types\" : [\"ios\",\"all\"],"
//            + "  \"notification\" : { \"alert\" : \"wat\" }"
//            + "}";
//    }

    @Test
    public void testRichPush1() throws Exception {
        String json
            = "{"
            + "  \"audience\" : \"all\","
            + "  \"device_types\" : [\"ios\"],"
            + "  \"message\" : { \"title\" : \"T\", \"body\" : \"B\" }"
            + "}";
    }

    @Test
    public void testDeviceTypeOverrides() throws Exception {
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

    }

//    @Test(expected = APIParsingException.class)
//    public void testInvalidDeviceIdentifiers() throws Exception {
//        PushPayload payload = PushPayload.newBuilder()
//            .setAudience(Selectors.apid("wat"))
//            .setNotification(Notification.newBuilder()
//                             .setAlert("WAT")
//                             .build())
//            .setDeviceTypes(DeviceTypeData.newBuilder()
//                          .addDeviceType(DeviceType.WNS)
//                          .build())
//            .build();
//    }

    @Test
    public void testValidDeviceIdentifiers() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
            .setAudience(Selectors.apid("6de14dab-a4e0-fe5b-06f7-f03b090e4a25"))
            .setNotification(Notification.newBuilder()
                             .setAlert("WAT")
                             .build())
            .setDeviceTypes(DeviceTypeData.newBuilder()
                          .addDeviceType(DeviceType.WNS)
                          .build())
            .build();
    }

    @Test
    public void testSerialization() throws Exception {

        PushPayload pushPayload = PushPayload.newBuilder()
                .setAudience(Selectors.all())
                .setDeviceTypes(DeviceTypeData.newBuilder().addDeviceType(DeviceType.IOS).build())
                .setNotification(Notification.newBuilder().setAlert("alert").build())
                .setOptions(Options.newBuilder().setExpiry(new DateTime("2015-04-01T12:00:00", DateTimeZone.UTC)).build())
                .build();

        String json = mapper.writeValueAsString(pushPayload);

        // TODO: Fix documentation. As of Oct 9, 2013 the documentation for expiry at http://docs.urbanairship.com/reference/api/v3/push.html#expiry is incorrect. The options in the documentation are within the notification, which does not work. The options should be at the same level as notification, audience, device_types, etc.
        String properJson
                = "{"
                + "\"audience\":\"ALL\","
                + "\"device_types\":[\"ios\"],"
                + "\"notification\":{\"alert\":\"alert\"},"
                + "\"options\":{\"expiry\":\"2015-04-01T12:00:00\"}"
                + "}";

        assertEquals(properJson, json);
    }

    @Test
    public void testSerializationExpirySeconds() throws Exception {

        PushPayload pushPayload = PushPayload.newBuilder()
                .setAudience(Selectors.all())
                .setDeviceTypes(DeviceTypeData.newBuilder().addDeviceType(DeviceType.IOS).build())
                .setNotification(Notification.newBuilder().setAlert("alert").build())
                .setOptions(Options.newBuilder().setExpirySeconds(3600).build())
                .build();

        String json = mapper.writeValueAsString(pushPayload);

        // TODO: Fix documentation. As of Oct 9, 2013 the documentation for expiry at http://docs.urbanairship.com/reference/api/v3/push.html#expiry is incorrect. The options in the documentation are within the notification, which does not work. The options should be at the same level as notification, audience, device_types, etc.
        String properJson
                = "{"
                + "\"audience\":\"ALL\","
                + "\"device_types\":[\"ios\"],"
                + "\"notification\":{\"alert\":\"alert\"},"
                + "\"options\":{\"expiry\":3600}"
                + "}";

        assertEquals(properJson, json);
    }

}
