package com.urbanairship.api.push.parse.notification.actions;

import com.urbanairship.api.push.model.notification.actions.*;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ActionsSerializationTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testRoundTripActions() throws IOException {
        URI u = URI.create("http://foo.com");
        String appDefined = "{\n" +
                "                \"field1\" : \"x\",\n" +
                "                \"field2\" : \"y\"\n" +
                "            }\n";
        ObjectNode expected = (ObjectNode) mapper.readTree(appDefined);
        AppDefinedAction appDefinedAction = new AppDefinedAction(expected);

        Actions actions = Actions.newBuilder()
                .addTags(new AddTagAction(TagActionData.single("some-tag")))
                .setOpen(new OpenExternalURLAction(u))
                .setShare(new ShareAction("share this!"))
                .addAppDefined(appDefinedAction)
                .build();
        String json = mapper.writeValueAsString(actions);

        Actions actions1 = mapper.readValue(json, Actions.class);
        assertEquals(actions, actions1);
    }

    private Actions createLandingPageActions() {
        return Actions.newBuilder()
                .setOpen(
                        new OpenLandingPageWithContentAction(
                                LandingPageContent.newBuilder()
                                        .setBody("<html>Landing Page.</html>")
                                        .setContentType("text/html")
                                        .setEncoding(LandingPageContent.Encoding.UTF8)
                                        .build()))
                .build();
    }

    @Test
    public void testDeserializeLandingPage() throws IOException {
        String landingPageJSON =
                "{\n" +
                        "    \"open\": {\n" +
                        "        \"type\": \"landing_page\"," +
                        "        \"content\": {\n" +
                        "            \"body\": \"<html>Landing Page.</html>\"," +
                        "            \"content_type\": \"text/html\"," +
                        "            \"content_encoding\": \"utf-8\"" +
                        "        }\n" +
                        "    }\n" +
                        "}\n";
        Actions actions = mapper.readValue(landingPageJSON, Actions.class);
        assertTrue("The open action should be present.", actions.getOpenAction().isPresent());

        Actions expectedActions = createLandingPageActions();
        assertEquals(expectedActions, actions);
    }

    @Test
    public void testSerializeLandingPage() throws IOException {
        Actions actions = createLandingPageActions();
        String json = mapper.writeValueAsString(actions);
        Actions actionsRoundTripped = mapper.readValue(json, Actions.class);
        assertEquals(actions, actionsRoundTripped);
    }
}
