package com.urbanairship.api.push.parse.notification.android;


import com.urbanairship.api.push.model.notification.android.BigPictureStyle;
import com.urbanairship.api.push.model.notification.android.BigTextStyle;
import com.urbanairship.api.push.model.notification.android.InboxStyle;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class StyleSerializerTest {
    private static final ObjectMapper MAPPER = PushObjectMapper.getInstance();

    @Test
    public void testBigPictureStyleSerializer() throws Exception {
        String bigPictureStyleJson =
                "{" +
                        "\"type\":\"big_picture\"," +
                        "\"big_picture\":\"pic.png\"," +
                        "\"title\":\"big pic title\"," +
                        "\"summary\":\"big pic summary\"" +
                "}";

        BigPictureStyle bigPictureStyle = BigPictureStyle.newBuilder()
                .setContent("pic.png")
                .setTitle("big pic title")
                .setSummary("big pic summary")
                .build();

        String parsedBigText = MAPPER.writeValueAsString(bigPictureStyle);
        assertEquals(bigPictureStyleJson, parsedBigText);
    }

    @Test
    public void testBigTextStyleSerializer() throws Exception {
        String bigTextStyleJson =
                "{" +
                        "\"type\":\"big_text\"," +
                        "\"big_text\":\"text\"," +
                        "\"title\":\"big text title\"," +
                        "\"summary\":\"big text summary\"" +
                "}";

        BigTextStyle bigTextStyle = BigTextStyle.newBuilder()
                .setContent("text")
                .setSummary("big text summary")
                .setTitle("big text title")
                .build();

        String parsedBigPicture = MAPPER.writeValueAsString(bigTextStyle);
        assertEquals(bigTextStyleJson, parsedBigPicture);
    }

    @Test
    public void testInboxStyle() throws Exception {
        String styleInboxJson =
                "{" +
                        "\"type\":\"inbox\"," +
                        "\"lines\":[\"line_1\",\"line_2\",\"line_3\"]," +
                        "\"title\":\"lines title\"," +
                        "\"summary\":\"lines summary\"" +
                "}";

        InboxStyle inboxStyle = InboxStyle.newBuilder()
                .addLine("line_1").addLine("line_2").addLine("line_3")
                .setTitle("lines title")
                .setSummary("lines summary")
                .build();

        String parsedInbox = MAPPER.writeValueAsString(inboxStyle);
        assertEquals(styleInboxJson, parsedInbox);
    }
}
