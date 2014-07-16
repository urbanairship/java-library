package com.urbanairship.api.push.parse;

import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushOptions;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.push.model.notification.Notifications;
import com.urbanairship.api.push.model.notification.android.AndroidDevicePayload;
import com.urbanairship.api.push.model.notification.blackberry.BlackberryDevicePayload;
import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;
import com.urbanairship.api.push.model.notification.mpns.MPNSDevicePayload;
import com.urbanairship.api.push.model.notification.wns.WNSDevicePayload;
import com.urbanairship.api.push.model.notification.wns.WNSPush;
import com.urbanairship.api.push.model.notification.wns.WNSTileData;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Ignore;
import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

import java.util.List;

import static org.junit.Assert.*;

//@Ignore("Ignore until refactored")
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
        PushPayload push = PushPayload.newBuilder()
                .setAudience(Selectors.all())
                .setDeviceTypes(DeviceTypeData.newBuilder().addDeviceType(DeviceType.IOS).build())
                .setNotification(Notification.newBuilder().setAlert("wat").build())
                .build();

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

        PushPayload push = PushPayload.newBuilder()
                .setAudience(Selectors.all())
                .setDeviceTypes(DeviceTypeData.newBuilder().addDeviceType(DeviceType.IOS).build())
                .setNotification(Notification.newBuilder().setAlert("wat").build())
                .setPushOptions(PushOptions.newBuilder().build())
                .build();

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

    }

    @Test
    public void testDeviceTypesList() throws Exception {
        String json
            = "{"
            + "\"audience\":\"ALL\","
            + "\"device_types\":[\"ios\",\"android\",\"wns\"],"
            + "\"notification\":{\"alert\":\"wat\"}"
            + "}";

        PushPayload push = PushPayload.newBuilder()
                .setAudience(Selectors.all())
                .setDeviceTypes(DeviceTypeData.newBuilder().addAllDeviceTypes(ImmutableSet.of(DeviceType.IOS,
                        DeviceType.ANDROID, DeviceType.WNS)).build())
                .setNotification(Notification.newBuilder().setAlert("wat").build())
                .build();
        String pushJson = mapper.writeValueAsString(push);

        assertEquals(json, pushJson);

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
            + "\"audience\":\"ALL\","
            + "\"device_types\":[\"ios\",\"wns\",\"mpns\",\"android\"],"
            + "\"notification\":{"
             + "\"alert\":\"wat\","
            + "\"mpns\":{"
            + "\"alert\":\"mpns\""
            + "},"
            + "\"blackberry\":{"
            + "\"alert\":\"doomed\""
            + "},"
            + "\"android\":{"
            + "\"alert\":\"droid\""
            + "},"
            + "\"wns\":{"
            + "\"alert\":\"wns\""
            + "},"
            + "\"ios\":{"
            + "\"alert\":\"ios\""
            + "}"
            + "}"
            + "}";


        PushPojo push = mapper.readValue(json, PushPojo.class);


        assertEquals(push.audience, "ALL");

        assertEquals(push.device_types.size(), 4);
        assertEquals(push.device_types.get(0), "ios");
        assertEquals(push.device_types.get(1), "wns");
        assertEquals(push.device_types.get(2), "mpns");
        assertEquals(push.device_types.get(3), "android");

        assertEquals(push.notification.android.alert, "droid");
        assertEquals(push.notification.ios.alert, "ios");
        assertEquals(push.notification.blackberry.alert, "doomed");
        assertEquals(push.notification.wns.alert, "wns");
        assertEquals(push.notification.mpns.alert, "mpns");
    }


    public static class DeviceOverride {
        public String alert;
    }

    public static class DeviceNotifications {
        public String alert;
        public DeviceOverride ios;
        public DeviceOverride wns;
        public DeviceOverride android;
        public DeviceOverride mpns;
        public DeviceOverride blackberry;
    }

    public static class PushPojo {
        public String audience;
        public List<String> device_types;
        public DeviceNotifications notification;
    }


    @Test
    public void testWNSAlert() throws Exception {

        String json
                = "{"
                + "\"alert\":\"wns\""
                +"}";


        WNSDevicePayload push = WNSDevicePayload.newBuilder()
                .setAlert("wns").build();

        String pushJson = mapper.writeValueAsString(push);
        assertEquals(json, pushJson);
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

}
