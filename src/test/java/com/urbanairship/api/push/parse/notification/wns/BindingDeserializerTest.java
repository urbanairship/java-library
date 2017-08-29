package com.urbanairship.api.push.parse.notification.wns;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.push.model.notification.wns.WNSBinding;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BindingDeserializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testDeserialize() throws Exception {
        String template = RandomStringUtils.randomAlphabetic(20);
        String fallback = RandomStringUtils.randomAlphabetic(20);
        String lang = RandomStringUtils.randomAlphabetic(4);
        String base_uri = RandomStringUtils.randomAlphabetic(20);
        String image = RandomStringUtils.randomAlphabetic(20);
        String text = RandomStringUtils.randomAlphabetic(20);

        String json =
                "{" +
                        "\"template\": \"" + template + "\"," +
                        "\"fallback\": \"" + fallback + "\"," +
                        "\"lang\": \"" + lang + "\"," +
                        "\"base_uri\": \"" + base_uri + "\"," +
                        "\"image\": [\"" + image + "\"]," +
                        "\"text\": [\"" + text + "\"]," +
                        "\"version\": 1," +
                        "\"add_image_query\": true" +
                        "}";

        WNSBinding expected = WNSBinding.newBuilder()
                .setTemplate(template)
                .setFallback(fallback)
                .setLang(lang)
                .setBaseUri(base_uri)
                .addImage(image)
                .addText(text)
                .setVersion(1)
                .setAddImageQuery(true)
                .build();
        WNSBinding parsed = mapper.readValue(json,
                WNSBinding.class);
        assertEquals(expected, parsed);
    }
}
