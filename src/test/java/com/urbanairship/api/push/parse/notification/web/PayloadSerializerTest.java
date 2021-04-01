package com.urbanairship.api.push.parse.notification.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.PushExpiry;
import com.urbanairship.api.push.model.notification.actions.Actions;
import com.urbanairship.api.push.model.notification.actions.OpenExternalURLAction;
import com.urbanairship.api.push.model.notification.web.Button;
import com.urbanairship.api.push.model.notification.web.WebDevicePayload;
import com.urbanairship.api.push.model.notification.web.WebIcon;
import com.urbanairship.api.push.model.notification.web.WebImage;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;

public class PayloadSerializerTest {

    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testFullWebPayload() throws Exception {
        WebIcon webIcon = WebIcon.newBuilder()
                .setUrl("https://i.ytimg.com/vi/PNgykntrIzE/maxresdefault.jpg")
                .build();

        URI uri = new URI("https://www.airship.com");
        OpenExternalURLAction openExternalURLAction = new OpenExternalURLAction(uri);

        WebImage webImage = new WebImage("https://i.ytimg.com/vi/PNgykntrIzE/maxresdefault.jpg");

        WebDevicePayload webPayload = WebDevicePayload.newBuilder()
                .setAlert("WebSettings specific alert")
                .setTitle("WebSettings title")
                .addExtraEntry("extrakey", "extravalue")
                .setWebIcon(webIcon)
                .addButton(Button.newBuilder()
                        .setLabel("label")
                        .setId("id")
                        .setActions(Actions.newBuilder()
                                .setOpen(openExternalURLAction)
                                .build())
                        .build())
                .addButton(Button.newBuilder()
                        .setLabel("label2")
                        .setId("id2")
                        .setActions(Actions.newBuilder()
                                .setOpen(openExternalURLAction)
                                .build())
                        .build())
                .setExpiry(PushExpiry.newBuilder()
                        .setExpirySeconds(20)
                        .build())
                .setActions(Actions.newBuilder()
                        .setOpen(openExternalURLAction)
                        .build())
                .setWebImage(webImage)
                .setRequireInteraction(true)
                .build();

        String expected = "{\n" +
                "  \"alert\": \"WebSettings specific alert\",\n" +
                "  \"extra\": {\n" +
                "    \"extrakey\": \"extravalue\"\n" +
                "  },\n" +
                "  \"icon\": {\n" +
                "    \"url\": \"https://i.ytimg.com/vi/PNgykntrIzE/maxresdefault.jpg\"\n" +
                "  },\n" +
                "  \"title\": \"WebSettings title\",\n" +
                "  \"require_interaction\": true,\n" +
                "  \"actions\": {\n" +
                "    \"open\": {\n" +
                "      \"type\": \"url\",\n" +
                "      \"content\": \"https://www.airship.com\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"image\": {\n" +
                "    \"url\": \"https://i.ytimg.com/vi/PNgykntrIzE/maxresdefault.jpg\"\n" +
                "  },\n" +
                "  \"time_to_live\": 20,\n" +
                "  \"buttons\": [\n" +
                "    {\n" +
                "      \"id\": \"id\",\n" +
                "      \"label\": \"label\",\n" +
                "      \"actions\": {\n" +
                "        \"open\": {\n" +
                "          \"type\": \"url\",\n" +
                "          \"content\": \"https://www.airship.com\"\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"id2\",\n" +
                "      \"label\": \"label2\",\n" +
                "      \"actions\": {\n" +
                "        \"open\": {\n" +
                "          \"type\": \"url\",\n" +
                "          \"content\": \"https://www.airship.com\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        String parsedJson = MAPPER.writeValueAsString(webPayload);

        JsonNode actualJson = MAPPER.readTree(parsedJson);
        JsonNode expectedJson = MAPPER.readTree(expected);

        assertEquals(expectedJson, actualJson);

        WebDevicePayload roundTripWebPayload = MAPPER.readValue(parsedJson, WebDevicePayload.class);

        assertEquals(webPayload, roundTripWebPayload);
    }
}
