package com.urbanairship.api.push.parse.notification.ios;

import com.google.common.collect.ImmutableList;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.notification.ios.IOSAlertData;
import com.urbanairship.api.push.model.notification.ios.IOSBadgeData;
import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PayloadDeserializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testAlert() throws Exception {
        String json
                = "{"
                + "  \"alert\": \"iOS override\""
                + "}";

        IOSDevicePayload expected = IOSDevicePayload.newBuilder()
                .setAlert("iOS override")
                .build();

        IOSDevicePayload payload = mapper.readValue(json, IOSDevicePayload.class);
        assertNotNull(payload);
        assertNotNull(payload.getAlert());
        assertFalse(payload.getExtra().isPresent());
        assertTrue(payload.getAlert().isPresent());
        assertTrue(payload.getAlertData().isPresent());
        assertFalse(payload.getAlertData().get().isCompound());
        assertEquals("iOS override", payload.getAlert().get());
        assertEquals(expected, payload);
    }

    @Test
    public void testCompoundAlert() throws Exception {
        String json
                = "{"
                + "  \"alert\": {"
                + "    \"body\" : \"B\","
                + "    \"action-loc-key\" : \"ALK\","
                + "    \"loc-key\" : \"LK\","
                + "    \"loc-args\" : ["
                + "        \"arg1\", \"arg2\""
                + "      ],"
                + "    \"launch-image\" : \"LI\""
                + "  }"
                + "}";

        IOSDevicePayload expected = IOSDevicePayload.newBuilder()
                .setAlert(IOSAlertData.newBuilder()
                        .setBody("B")
                        .setActionLocKey("ALK")
                        .setLocKey("LK")
                        .setLocArgs(ImmutableList.of("arg1", "arg2"))
                        .setLaunchImage("LI")
                        .build())
                .build();

        IOSDevicePayload payload = mapper.readValue(json, IOSDevicePayload.class);
        assertTrue(String.format("Expected: '%s', Actual: '%s'", expected.toString(), payload.toString()),
                expected.equals(payload));
        assertEquals(expected.hashCode(), payload.hashCode());
        assertTrue(payload.getAlertData().isPresent());
        assertTrue(payload.getAlert().isPresent());
        assertEquals("B", payload.getAlert().get());

        IOSAlertData alert = payload.getAlertData().get();
        assertTrue(alert.isCompound());
        assertEquals("B", alert.getBody().get());
        assertEquals("ALK", alert.getActionLocKey().get());
        assertEquals("LK", alert.getLocKey().get());
        assertEquals("LI", alert.getLaunchImage().get());
        assertEquals(2, alert.getLocArgs().get().size());
    }

    @Test
    public void testAlertDataEquality() {
        IOSAlertData d1 = IOSAlertData.newBuilder()
                .setBody("A")
                .build();
        IOSAlertData d2 = IOSAlertData.newBuilder()
                .setActionLocKey("A")
                .setLaunchImage("L")
                .build();
        assertEquals(d1, d1);
        assertEquals(d2, d2);
        assertFalse(d1.equals(d2));
        assertFalse(d2.equals(d1));
    }

    @Test
    public void testSound() throws Exception {
        String json
                = "{"
                + "  \"sound\": \"cat.wav\""
                + "}";

        IOSDevicePayload expected = IOSDevicePayload.newBuilder()
                .setSound("cat.wav")
                .build();

        IOSDevicePayload payload = mapper.readValue(json, IOSDevicePayload.class);
        assertNotNull(payload);
        assertNotNull(payload.getSound());
        assertFalse(payload.getAlert().isPresent());
        assertFalse(payload.getExtra().isPresent());
        assertTrue(payload.getSound().isPresent());
        assertEquals("cat.wav", payload.getSound().get());
        assertEquals(expected, payload);
    }

    @Test
    public void testBadgeIntValue() throws Exception {
        String json
                = "{"
                + "  \"badge\": 37"
                + "}";

        IOSDevicePayload payload = mapper.readValue(json, IOSDevicePayload.class);
        assertNotNull(payload);
        assertNotNull(payload.getBadge());
        assertTrue(payload.getBadge().isPresent());

        IOSBadgeData badge = payload.getBadge().get();
        assertEquals(IOSBadgeData.Type.VALUE, badge.getType());
        assertEquals(37, badge.getValue().get().intValue());
    }

    @Test
    public void testBadgeAutobadge() throws Exception {
        IOSDevicePayload payload = mapper.readValue("{ \"badge\": \"auto\" }", IOSDevicePayload.class);
        assertTrue(payload.getBadge().isPresent());
        assertEquals(IOSBadgeData.Type.AUTO, payload.getBadge().get().getType());

        payload = mapper.readValue("{ \"badge\": \"+1\" }", IOSDevicePayload.class);
        assertTrue(payload.getBadge().isPresent());
        assertEquals(IOSBadgeData.Type.INCREMENT, payload.getBadge().get().getType());
        assertEquals(1, payload.getBadge().get().getValue().get().intValue());

        payload = mapper.readValue("{ \"badge\": \"-1\" }", IOSDevicePayload.class);
        assertTrue(payload.getBadge().isPresent());
        assertEquals(IOSBadgeData.Type.DECREMENT, payload.getBadge().get().getType());
        assertEquals(1, payload.getBadge().get().getValue().get().intValue());


        payload = mapper.readValue("{ \"badge\": \"+12\" }", IOSDevicePayload.class);
        assertTrue(payload.getBadge().isPresent());
        assertEquals(IOSBadgeData.Type.INCREMENT, payload.getBadge().get().getType());
        assertEquals(12, payload.getBadge().get().getValue().get().intValue());

        payload = mapper.readValue("{ \"badge\": \"-37\" }", IOSDevicePayload.class);
        assertTrue(payload.getBadge().isPresent());
        assertEquals(IOSBadgeData.Type.DECREMENT, payload.getBadge().get().getType());
        assertEquals(37, payload.getBadge().get().getValue().get().intValue());
    }

    @Test(expected = APIParsingException.class)
    public void testBadgeBadAutoValue() throws Exception {
        mapper.readValue("{ \"badge\": \"foo\" }", IOSDevicePayload.class);
    }

    @Test(expected = APIParsingException.class)
    public void testBadgeBadNumber() throws Exception {
        mapper.readValue("{ \"badge\": 3.14159 }", IOSDevicePayload.class);
    }

    @Test
    public void testContentAvailable() throws Exception {
        String json
                = "{"
                + "  \"content-available\": true"
                + "}";

        IOSDevicePayload expected = IOSDevicePayload.newBuilder()
                .setContentAvailable(true)
                .build();

        IOSDevicePayload payload = mapper.readValue(json, IOSDevicePayload.class);
        assertNotNull(payload);
        assertNotNull(payload.getSound());
        assertTrue(payload.getContentAvailable().isPresent());
        assertEquals(true, payload.getContentAvailable().get());
        assertEquals(expected, payload);
    }

    @Test
    public void testExtra() throws Exception {
        String json
                = "{"
                + "  \"extra\": {"
                + "    \"k1\" : \"v1\","
                + "    \"k2\" : \"v2\""
                + "  }"
                + "}";

        IOSDevicePayload expected = IOSDevicePayload.newBuilder()
                .addExtraEntry("k1", "v1")
                .addExtraEntry("k2", "v2")
                .build();

        IOSDevicePayload payload = mapper.readValue(json, IOSDevicePayload.class);
        assertNotNull(payload);
        assertNotNull(payload.getExtra());
        assertTrue(payload.getExtra().isPresent());
        assertFalse(payload.getAlert().isPresent());
        Map<String, String> extra = payload.getExtra().get();
        assertTrue(extra.containsKey("k1"));
        assertTrue(extra.containsKey("k2"));
        assertEquals("v1", extra.get("k1"));
        assertEquals("v2", extra.get("k2"));
        assertEquals(expected, payload);
    }

    @Test
    public void testValidate_Empty() throws Exception {
        IOSDevicePayload payload = mapper.readValue("{}", IOSDevicePayload.class);
        assertNotNull(payload);
        assertFalse(payload.getAlert().isPresent());
        assertFalse(payload.getExtra().isPresent());
        assertFalse(payload.getBadge().isPresent());
        assertFalse(payload.getSound().isPresent());
        assertFalse(payload.getContentAvailable().isPresent());
    }

    @Test
    public void testCategory() throws Exception {
        String json
            = "{"
            + "  \"category\": \"CAT1\""
            + "}";
        IOSDevicePayload payload = mapper.readValue(json, IOSDevicePayload.class);
        assertTrue(payload.getCategory().isPresent());
        assertEquals("CAT1", payload.getCategory().get());
    }

    @Test
    public void testPriority() throws Exception {
        String json
            = "{"
            + "  \"priority\": 5"
            + "}";
        IOSDevicePayload payload = mapper.readValue(json, IOSDevicePayload.class);
        assertTrue(payload.getPriority().get().equals(5));
    }

    @Test
    public void testTitle() throws Exception {
        String json
            = "{"
            + "  \"title\": \"title\""
            + "}";
        IOSDevicePayload payload = mapper.readValue(json, IOSDevicePayload.class);
        assertTrue(payload.getTitle().get().equals("title"));
    }

    @Test
    public void testIos10Extras() throws Exception {
        String json
            = "{\n" +
                "    \"alert\": \"alert\"," +
                "    \"subtitle\": \"subtitle\"," +
                "    \"mutable_content\": true," +
                "    \"media_attachment\": {" +
                "        \"url\": \"https://media.giphy.com/media/JYsWwF82EGnpC/giphy.gif\"," +
                "        \"options\": {" +
                "            \"time\": 10," +
                "            \"crop\": {" +
                "                \"x\": 0.1," +
                "                \"y\": 0.1," +
                "                \"width\": 0.2," +
                "                \"height\": 0.2" +
                "            }" +
                "        }," +
                "        \"content\": {" +
                "            \"body\": \"content body\"," +
                "            \"title\": \"content title\"," +
                "            \"subtitle\": \"content subtitle\"" +
                "        }" +
                "    }" +
                "}";

        IOSDevicePayload payload = mapper.readValue(json, IOSDevicePayload.class);
        assertTrue(payload.getAlert().get().equals("alert"));

        //Mutable Content
        assertTrue(payload.getMutableContent().get().equals(true));

        //Subtitle
        assertTrue(payload.getSubtitle().get().equals("subtitle"));

        //Media Attachment
        assertTrue(payload.getMediaAttachment().get().getUrl().equals("https://media.giphy.com/media/JYsWwF82EGnpC/giphy.gif"));

        //options
        assertTrue(payload.getMediaAttachment().get().getOptions().get().getTime().get().equals(10));
        assertTrue(payload.getMediaAttachment().get().getOptions().get().getCrop().get().getX().get().equals(0.1f));
        assertTrue(payload.getMediaAttachment().get().getOptions().get().getCrop().get().getY().get().equals(0.1f));
        assertTrue(payload.getMediaAttachment().get().getOptions().get().getCrop().get().getWidth().get().equals(0.2f));
        assertTrue(payload.getMediaAttachment().get().getOptions().get().getCrop().get().getHeight().get().equals(0.2f));

        //content
        assertTrue(payload.getMediaAttachment().get().getContent().get().getBody().get().equals("content body"));
        assertTrue(payload.getMediaAttachment().get().getContent().get().getSubtitle().get().equals("content subtitle"));
        assertTrue(payload.getMediaAttachment().get().getContent().get().getTitle().get().equals("content title"));
    }
}
