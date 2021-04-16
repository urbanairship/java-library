package com.urbanairship.api.push.parse.notification.ios;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.internal.HbsParser;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.OpenExternalURLAction;
import com.urbanairship.api.push.model.notification.ios.IOSAlertData;
import com.urbanairship.api.push.model.notification.ios.IOSBadgeData;
import com.urbanairship.api.push.model.notification.ios.IOSDevicePayload;
import com.urbanairship.api.push.model.notification.ios.IOSFields;
import com.urbanairship.api.push.model.notification.ios.IOSSoundData;
import com.urbanairship.api.push.model.notification.ios.IOSTemplate;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PayloadDeserializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
                + "    \"title\" : \"Super Cool Title\","
                + "    \"body\" : \"B\","
                + "    \"summary-arg\" : \"New England Patriots\","
                + "    \"summary-arg-count\" : 1,"
                + "    \"action-loc-key\" : \"ALK\","
                + "    \"loc-key\" : \"LK\","
                + "    \"loc-args\" : ["
                + "        \"arg1\", \"arg2\""
                + "      ],"
                + "    \"launch-image\" : \"LI\","
                + "    \"title-loc-args\" : ["
                + "        \"arg4\", \"arg5\""
                + "      ],"
                + "    \"title-loc-key\" : \"Special Key\""
                + "  }"
                + "}";

        IOSDevicePayload expected = IOSDevicePayload.newBuilder()
                .setAlert(IOSAlertData.newBuilder()
                        .setTitle("Super Cool Title")
                        .setBody("B")
                        .setActionLocKey("ALK")
                        .setLocKey("LK")
                        .setLocArgs(ImmutableList.of("arg1", "arg2"))
                        .setLaunchImage("LI")
                        .setSummaryArg("New England Patriots")
                        .setSummaryArgCount(1)
                        .setTitleLocArgs(ImmutableList.of("arg4", "arg5"))
                        .setTitleLocKey("Special Key")
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
        assertEquals("Super Cool Title", alert.getTitle().get());
        assertEquals("B", alert.getBody().get());
        assertEquals("ALK", alert.getActionLocKey().get());
        assertEquals("LK", alert.getLocKey().get());
        assertEquals("LI", alert.getLaunchImage().get());
        assertEquals(2, alert.getLocArgs().get().size());
        assertEquals("New England Patriots", alert.getSummaryArg().get());
        assertEquals(1, alert.getSummaryArgCount().get().intValue());
        assertEquals(2, alert.getTitleLocArgs().get().size());
        assertEquals("Special Key", alert.getTitleLocKey().get());
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
        String json =
                "{"
                + "    \"name\" : \"Billy Bob Thorton\","
                + "    \"critical\" : true,"
                + "    \"volume\" : 0.5"
                + "}";

        IOSSoundData payload = mapper.readValue(json, IOSSoundData.class);
        String objectJson = mapper.writeValueAsString(payload);
        IOSSoundData payload2 = mapper.readValue(json, IOSSoundData.class);

        assertEquals(payload, payload2);

        IOSSoundData expected = IOSSoundData.newBuilder()
                .setName("Billy Bob Thorton")
                .setCritical(true)
                .setVolume(0.5)
                .build();

        payload = mapper.readValue(objectJson, IOSSoundData.class);
        assertNotNull(payload);
        assertEquals(0.5, payload.getVolume().get(), 0.0f);
        assertEquals(true, payload.getCritical().get());
        assertEquals("Billy Bob Thorton", payload.getName().get());
        assertEquals(expected, payload);
    }

    @Test
    public void testSoundObjectWithNoName() throws Exception {
        String json =
                "{"
                + "  \"sound\": {"
                + "    \"name\" : \"Billy Bob Thorton\","
                + "    \"critical\" : true,"
                + "    \"volume\" : 0.5"
                + "  }"
                + "}";

        thrown.expect(APIParsingException.class);
        thrown.expectMessage("The sound file name cannot be null");
        mapper.readValue(json, IOSSoundData.class);
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
        assertNotNull(payload.getSoundData());
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
        assertFalse(payload.getSoundData().isPresent());
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
    public void testThreadId() throws Exception {
        String json
                = "{"
                + "  \"thread_id\": \"woof\""
                + "}";
        IOSDevicePayload payload = mapper.readValue(json, IOSDevicePayload.class);
        assertTrue(payload.getThreadId().get().equals("woof"));
    }

    @Test
    public void testIos10Extras() throws Exception {
        String json
            = "{\n" +
                "    \"alert\": \"alert\"," +
                "    \"subtitle\": \"subtitle\"," +
                "    \"mutable_content\": true," +
                "    \"sound\": \"beep boop\"," +
                "    \"media_attachment\": {" +
                "        \"url\": \"https://media.giphy.com/media/JYsWwF82EGnpC/giphy.gif\"," +
                "        \"options\": {" +
                "            \"time\": 10," +
                "            \"crop\": {" +
                "                \"x\": 0.1," +
                "                \"y\": 0.2," +
                "                \"width\": 0.3," +
                "                \"height\": 0.4" +
                "            }," +
                "           \"hidden\": true" +
                "        }," +
                "        \"content\": {" +
                "            \"body\": \"content body\"," +
                "            \"title\": \"content title\"," +
                "            \"subtitle\": \"content subtitle\"" +
                "        }" +
                "    }," +
                "    \"collapse_id\": \"collapseId\"," +
                "    \"thread_id\": \"threadId\"" +
                "}";

        IOSDevicePayload payload = mapper.readValue(json, IOSDevicePayload.class);
        String objectJson = mapper.writeValueAsString(payload);
        IOSDevicePayload payload2 = mapper.readValue(json, IOSDevicePayload.class);

        assertTrue(payload.equals(payload2));

        payload = mapper.readValue(objectJson, IOSDevicePayload.class);

        assertTrue(payload.getAlert().get().equals("alert"));

        //Collapse ID
        assertTrue(payload.getCollapseId().get().equals("collapseId"));

        //Thread ID
        assertTrue(payload.getThreadId().get().equals("threadId"));

        //Mutable Content
        assertTrue(payload.getMutableContent().get().equals(true));

        //Subtitle
        assertTrue(payload.getSubtitle().get().equals("subtitle"));

        //Media Attachment
        assertTrue(payload.getMediaAttachment().get().getUrl().equals("https://media.giphy.com/media/JYsWwF82EGnpC/giphy.gif"));

        //Sound
        assertTrue(payload.getSound().get().equals("beep boop"));

        //options
        assertTrue(payload.getMediaAttachment().get().getOptions().get().getTime().get().equals(10));
        assertTrue(payload.getMediaAttachment().get().getOptions().get().getCrop().get().getX().get().equals(new BigDecimal("0.1")));
        assertTrue(payload.getMediaAttachment().get().getOptions().get().getCrop().get().getY().get().equals(new BigDecimal("0.2")));
        assertTrue(payload.getMediaAttachment().get().getOptions().get().getCrop().get().getWidth().get().equals(new BigDecimal("0.3")));
        assertTrue(payload.getMediaAttachment().get().getOptions().get().getCrop().get().getHeight().get().equals(new BigDecimal("0.4")));
        assertTrue(payload.getMediaAttachment().get().getOptions().get().getHidden().get().equals(true));

        //content
        assertTrue(payload.getMediaAttachment().get().getContent().get().getBody().get().equals("content body"));
        assertTrue(payload.getMediaAttachment().get().getContent().get().getSubtitle().get().equals("content subtitle"));
        assertTrue(payload.getMediaAttachment().get().getContent().get().getTitle().get().equals("content title"));
    }

    @Test
    public void testRoundTripDeserialization() throws Exception {
        URI uri = URI.create("http://www.airship.com");

        OpenExternalURLAction externalURLAction = new OpenExternalURLAction(uri);

        Actions actions = Actions.newBuilder()
                .setOpen(externalURLAction)
                .build();


        IOSDevicePayload payload = IOSDevicePayload.newBuilder()
                .setActions(actions)
                .setTargetContentId("targetContentId")
                .setAlert("Alert")
                .build();

        String json = mapper.writeValueAsString(payload);

        IOSDevicePayload roundTripPayload = mapper.readValue(json, IOSDevicePayload.class);

        assertEquals(payload, roundTripPayload);
    }

    @Test
    public void testTemplatesIosFieldsDeserialization() throws Exception {
        IOSFields iosFieldsExpected = IOSFields.newBuilder()
                .setAlert("{{NAME}} stymies the {{OTHER_TEAM}} for San Francisco's first perfect game in franchise history.")
                .setSubtitle("San Francisco Giants {{DATE}}")
                .setTitle("{{NAME}} Throws a Perfect Game")
                .build();

        String json = "{\n" +
                "  \"title\": \"{{NAME}} Throws a Perfect Game\",\n" +
                "  \"alert\": \"{{NAME}} stymies the {{OTHER_TEAM}} for San Francisco's first perfect game in franchise history.\",\n" +
                "  \"subtitle\": \"San Francisco Giants {{DATE}}\"\n" +
                "}";

        IOSFields iosFieldsActual = mapper.readValue(json, IOSFields.class);

        String roundTripJsonStr = mapper.writeValueAsString(iosFieldsActual);
        JsonNode iosFieldsExpectedJson = mapper.readTree(json);
        JsonNode iosFieldsActualJson = mapper.readTree(roundTripJsonStr);

        assertEquals(iosFieldsExpected, iosFieldsActual);
        assertEquals(iosFieldsExpectedJson, iosFieldsActualJson);
    }

    @Test
    public void testTemplateDeserialization() throws Exception {
        IOSFields iosFields = IOSFields.newBuilder()
                .setAlert("{{NAME}} stymies the {{OTHER_TEAM}} for San Francisco's first perfect game in franchise history.")
                .setSubtitle("San Francisco Giants {{DATE}}")
                .setTitle("{{NAME}} Throws a Perfect Game")
                .build();

        IOSTemplate iosTemplateWithFields = IOSTemplate.newBuilder()
                .setFields(iosFields)
                .build();

        IOSTemplate iosTemplateWithId = IOSTemplate.newBuilder()
                .setTemplateId("templateId")
                .build();

        String json = mapper.writeValueAsString(iosTemplateWithFields);
        String jsonWithId = mapper.writeValueAsString(iosTemplateWithId);

        String jsonStrExpected = "{\n" +
                "  \"fields\": {\n" +
                "    \"alert\": \"{{NAME}} stymies the {{OTHER_TEAM}} for San Francisco's first perfect game in franchise history.\",\n" +
                "    \"subtitle\": \"San Francisco Giants {{DATE}}\",\n" +
                "    \"title\": \"{{NAME}} Throws a Perfect Game\"\n" +
                "  }\n" +
                "}";

        String jsonStrExpectedWithId = "{\"template_id\":\"templateId\"}";

        IOSTemplate iosTemplateWithFieldsRoundTrip = mapper.readValue(json, IOSTemplate.class);
        IOSTemplate iosTemplateWithIdRoundTrip = mapper.readValue(jsonWithId, IOSTemplate.class);

        assertEquals(iosTemplateWithFields, iosTemplateWithFieldsRoundTrip);
        assertEquals(iosTemplateWithId, iosTemplateWithIdRoundTrip);

        JsonNode expectedJson = mapper.readTree(jsonStrExpected);
        JsonNode expectedJsonWithId = mapper.readTree(jsonStrExpectedWithId);

        JsonNode actualJson = mapper.readTree(json);
        JsonNode actualJsonWithId = mapper.readTree(jsonWithId);

        assertEquals(expectedJson, actualJson);
        assertEquals(expectedJsonWithId, actualJsonWithId);
    }

    @Test
    public void testPayloadWithTemplate() throws Exception {
        IOSFields iosFields = IOSFields.newBuilder()
                .setAlert("{{NAME}} stymies the {{OTHER_TEAM}} for San Francisco's first perfect game in franchise history.")
                .setSubtitle("San Francisco Giants {{DATE}}")
                .setTitle("{{NAME}} Throws a Perfect Game")
                .build();

        IOSTemplate iosTemplateWithFields = IOSTemplate.newBuilder()
                .setFields(iosFields)
                .build();

        IOSDevicePayload payload = IOSDevicePayload.newBuilder()
                .setIosTemplate(iosTemplateWithFields)
                .build();

        String json = mapper.writeValueAsString(payload);

        IOSDevicePayload roundTripPayload = mapper.readValue(json, IOSDevicePayload.class);

        assertEquals(payload, roundTripPayload);
    }

    @Test
    public void testPayloadWithTemplateId() throws Exception {
        IOSTemplate iosTemplateWithId = IOSTemplate.newBuilder()
                .setTemplateId("templateId")
                .build();

        IOSDevicePayload iosDevicePayload = IOSDevicePayload.newBuilder()
                .setIosTemplate(iosTemplateWithId)
                .build();

        String actualJson = mapper.writeValueAsString(iosDevicePayload);

        IOSDevicePayload roundTripIosDevicePayload = mapper.readValue(actualJson, IOSDevicePayload.class);

        assertEquals(iosDevicePayload, roundTripIosDevicePayload);
    }
}
