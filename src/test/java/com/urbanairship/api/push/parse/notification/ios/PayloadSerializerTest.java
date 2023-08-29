package com.urbanairship.api.push.parse.notification.ios;


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import com.urbanairship.api.push.model.notification.ios.IOSLiveActivity;
import com.urbanairship.api.push.model.notification.ios.IOSLiveActivityAlert;
import com.urbanairship.api.push.model.notification.ios.IOSLiveActivityEvent;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.push.model.notification.Interactive;
import com.urbanairship.api.push.model.notification.ios.Crop;
import com.urbanairship.api.push.model.notification.ios.IOSAlertData;
import com.urbanairship.api.push.model.notification.ios.IOSBadgeData;
import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;
import com.urbanairship.api.push.model.notification.ios.IOSInterruptionLevel;
import com.urbanairship.api.push.model.notification.ios.IOSMediaContent;
import com.urbanairship.api.push.model.notification.ios.IOSMediaOptions;
import com.urbanairship.api.push.model.notification.ios.IOSSoundData;
import com.urbanairship.api.push.model.notification.ios.MediaAttachment;
import com.urbanairship.api.push.parse.PushObjectMapper;

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
                        .setSubtitleLocArgs(ImmutableList.of("arg5","arg6"))
                        .setSubtitleLocKey("PNL")
                        .build())
                .build();

        String json = mapper.writeValueAsString(payload);

        String expected
            = "{\"alert\":{\"body\":\"B\",\"action-loc-key\":\"ALK\",\"loc-key\":\"LK\",\"loc-args\":[\"arg1\",\"arg2\"],\"launch-image\":\"LI\",\"title\":\"T\",\"title-loc-args\":[\"arg3\",\"arg4\"],\"title-loc-key\":\"TLK\",\"summary-arg\":\"SA\",\"summary-arg-count\":1,\"subtitle-loc-args\":[\"arg5\",\"arg6\"],\"subtitle-loc-key\":\"PNL\"}}";

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
    public void testSound() throws Exception {
        IOSSoundData soundData = IOSSoundData.newBuilder()
                .setCritical(true)
                .setVolume(1.0)
                .setName("Goliath")
                .build();

        String json = mapper.writeValueAsString(soundData);

        String expected
                = "{\"critical\":true,\"volume\":1.0,\"name\":\"Goliath\"}";

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
                .setSoundData(IOSSoundData.newBuilder().setName("just a string file name").build())
                .setCollapseId("collapseId")
                .setThreadId("threadId")
                .build();

        String json = mapper.writeValueAsString(payload);
        String expected
                = "{\"alert\":\"alert\",\"subtitle\":\"subtitle\",\"mutable_content\":true,\"sound\":\"just a string file name\",\"media_attachment\":{\"url\":\"http://www.google.com\",\"options\":{\"time\":10,\"crop\":{\"x\":0.1,\"y\":0.2,\"width\":0.3,\"height\":0.4},\"hidden\":true},\"content\":{\"body\":\"content body\",\"title\":\"content title\",\"subtitle\":\"content subtitle\"}},\"collapse_id\":\"collapseId\",\"thread_id\":\"threadId\"}";

        assertEquals(expected, json);
    }

    @Test
    public void testInterruptionLevel() throws Exception {
        IOSDevicePayload payload = IOSDevicePayload.newBuilder()
                .setAlert("alert")
                .setIosInterruptionLevel(IOSInterruptionLevel.CRITICAL)
                .build();

        String json = mapper.writeValueAsString(payload);

        String expected
            = "{\"alert\":\"alert\",\"interruption_level\":\"critical\"}";

        assertEquals(expected, json);
    }


    @Test
    public void testRelevanceScore() throws Exception {
        String json =
                "{" +
                        "\"relevance_score\": 1.0" +
                "}";

        IOSDevicePayload payload = mapper.readValue(json, IOSDevicePayload.class);
        assertEquals(payload.getRelevanceScore().get(), 1.0, 0.0f);
    }

    @Test
    public void testLiveActivity() throws Exception {
        IOSLiveActivity iosLiveActivity = IOSLiveActivity.newBuilder()
                .setIosLiveActivityEvent(IOSLiveActivityEvent.UPDATE)
                .setName("Foxes-Tigers")
                .setPriority(5)
                .setIosLiveActivityAlert(IOSLiveActivityAlert.newBuilder()
                        .setBody("test").setTitle("test").setSound("test.mp3").build())
                .setDismissalDate(1234)
                .setRelevanceScore(1.0)
                .setStaleDate(1234)
                .addContentState("key", "value")
                .addContentState("key2", "value2")
                .build();

        IOSDevicePayload payload = IOSDevicePayload.newBuilder()
                .setIosLiveActivity(iosLiveActivity)
                .build();

        String json = mapper.writeValueAsString(payload);

        String expected = "{\"live_activity\":{\"event\":\"update\",\"name\":\"Foxes-Tigers\",\"alert\":{\"body\":\"test\",\"title\":\"test\",\"sound\":\"test.mp3\"},\"content_state\":{\"key\":\"value\",\"key2\":\"value2\"},\"dismissal_date\":1234,\"priority\":5,\"relevance_score\":1.0,\"stale_date\":1.0}}";

        assertEquals(expected, json);
    }
}
