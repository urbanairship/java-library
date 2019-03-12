package com.urbanairship.api.push.parse.notification.ios;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.ios.*;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class PayloadSerializerTest {

    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testAlert() throws Exception {


        IOSDevicePayload payload = IOSDevicePayload.newBuilder()
                .setAlert("iOS override")
                .build();

        String expected
                = "\"iOS override\"";

        String json = mapper.writeValueAsString(payload.getAlert().get());

        assertEquals(expected, json);
    }

    @Test
    public void testEmptyAlert() throws Exception {
        IOSDevicePayload payload = IOSDevicePayload.newBuilder()
                .setAlert(IOSAlertData.newBuilder().build())
                .setBadge(IOSBadgeData.newBuilder().setType(IOSBadgeData.Type.VALUE).setValue(1).build())
                .build();

        String json = mapper.writeValueAsString(payload);


        String expected
                = "{\"alert\":{},\"badge\":1}";

        assertEquals(expected, json);
    }

    @Test
    public void testCompoundAlert() throws Exception {

        IOSDevicePayload payload = IOSDevicePayload.newBuilder()
                .setAlert(IOSAlertData.newBuilder()
                        .setTitle("T")
                        .setBody("B")
                        .setActionLocKey("ALK")
                        .setLocKey("LK")
                        .setLocArgs(ImmutableList.of("arg1", "arg2"))
                        .setLaunchImage("LI")
                        .setTitleLocArgs(ImmutableList.of("arg3", "arg4"))
                        .setTitleLocKey("TLK")
                        .setSummaryArg("SA")
                        .setSummaryArgCount(1)
                        .build())
                .build();

        String json = mapper.writeValueAsString(payload);

        String expected
            = "{\"alert\":{\"body\":\"B\",\"action-loc-key\":\"ALK\",\"loc-key\":\"LK\",\"loc-args\":[\"arg1\",\"arg2\"],\"launch-image\":\"LI\",\"title\":\"T\",\"title-loc-args\":[\"arg3\",\"arg4\"],\"title-loc-key\":\"TLK\",\"summary-arg\":\"SA\",\"summary-arg-count\":1}}";

        assertEquals(expected, json);
    }

    @Test
    public void testAlertSansBody() throws Exception {
        IOSDevicePayload payload = IOSDevicePayload.newBuilder()
            .setAlert(IOSAlertData.newBuilder()
                .setActionLocKey("ALK")
                .setLocKey("LK")
                .setLocArgs(ImmutableList.of("arg1", "arg2"))
                .setLaunchImage("LI")
                .build())
            .build();

        String json = mapper.writeValueAsString(payload);

        String expected
            = "{\"alert\":{\"action-loc-key\":\"ALK\",\"loc-key\":\"LK\",\"loc-args\":[\"arg1\",\"arg2\"],\"launch-image\":\"LI\"}}";

        assertEquals(expected, json);
    }

    @Test
    public void testCategory() throws Exception {
        IOSDevicePayload expected = IOSDevicePayload.newBuilder()
            .setAlert("alert")
            .setCategory("CAT5")
            .build();
        String json = mapper.writeValueAsString(expected);
        IOSDevicePayload parsed = mapper.readValue(json, IOSDevicePayload.class);
        assertEquals(expected, parsed);
        assertEquals("CAT5", parsed.getCategory().get());
    }

    @Test
    public void testInteractiveNotificationActions() throws Exception {
        String json
            = "{"
            + "  \"type\" : \"ua_yes_no_foreground\","
            + "  \"button_actions\" : {"
            + "    \"yes\" : {"
            + "      \"share\" : \"foo\""
            + "    }"
            + "  }"
            + "}";
        Interactive interactive = mapper.readValue(json, Interactive.class);

        IOSDevicePayload expected = IOSDevicePayload.newBuilder()
            .setAlert("test alert")
            .setInteractive(interactive)
            .build();

        String devicePayloadJSON = mapper.writeValueAsString(expected);
        IOSDevicePayload parsed = mapper.readValue(devicePayloadJSON, IOSDevicePayload.class);
        assertEquals(expected, parsed);
        Interactive returned = parsed.getInteractive().get();
        assertEquals(interactive, returned);
    }

    @Test
    public void testTitle() throws Exception {
        IOSDevicePayload payload = IOSDevicePayload.newBuilder()
            .setAlert("alert")
            .setTitle("title")
            .build();

        String json = mapper.writeValueAsString(payload);


        String expected
            = "{\"alert\":\"alert\",\"title\":\"title\"}";

        assertEquals(expected, json);
    }

    @Test
    public void testSubtitle() throws Exception {
        IOSDevicePayload payload = IOSDevicePayload.newBuilder()
                .setAlert("alert")
                .setSubtitle("subtitle")
                .build();

        String json = mapper.writeValueAsString(payload);

        String expected
            = "{\"alert\":\"alert\",\"subtitle\":\"subtitle\"}";

        assertEquals(expected, json);
    }

    @Test
    public void testMediaAttachment() throws Exception {

        Crop crop = Crop.newBuilder()
                .setHeight(new BigDecimal("0.4"))
                .setWidth(new BigDecimal("0.3"))
                .setX(new BigDecimal("0.1"))
                .setY(new BigDecimal("0.2"))
                .build();

        IOSMediaOptions options = IOSMediaOptions.newBuilder()
                .setTime(10)
                .setCrop(crop)
                .setHidden(true)
                .build();

        IOSMediaContent content = IOSMediaContent.newBuilder()
                .setBody("content body")
                .setTitle("content title")
                .setSubtitle("content subtitle")
                .build();

        MediaAttachment mediaAttachment = MediaAttachment.newBuilder()
                .setUrl("http://www.google.com")
                .setOptions(options)
                .setContent(content)
                .build();

        IOSDevicePayload payload = IOSDevicePayload.newBuilder()
                .setAlert("alert")
                .setMutableContent(true)
                .setSubtitle("subtitle")
                .setMediaAttachment(mediaAttachment)
                .setSoundData("just a string file name")
                .setCollapseId("collapseId")
                .setThreadId("threadId")
                .build();

        String json = mapper.writeValueAsString(payload);
        String expected
                = "{\"alert\":\"alert\",\"subtitle\":\"subtitle\",\"mutable_content\":true,\"sound\":\"just a string file name\",\"media_attachment\":{\"url\":\"http://www.google.com\",\"options\":{\"time\":10,\"crop\":{\"x\":0.1,\"y\":0.2,\"width\":0.3,\"height\":0.4},\"hidden\":true},\"content\":{\"body\":\"content body\",\"title\":\"content title\",\"subtitle\":\"content subtitle\"}},\"collapse_id\":\"collapseId\",\"thread_id\":\"threadId\"}";

        assertEquals(expected, json);
    }

}
