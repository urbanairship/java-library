package com.urbanairship.api.reports.parse;


import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.reports.Base64ByteArray;
import com.urbanairship.api.reports.model.PerPushCounts;
import com.urbanairship.api.reports.model.PlatformType;
import com.urbanairship.api.reports.model.PushDetailResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @deprecated Marked to be removed in 2.0.0. Urban Airship stopped recommending use of these endpoints in October 2015,
 * so we are now completing their removal from our libraries.
 */
@Deprecated
public class PushDetailResponseDeserializerTest {

    private static final ObjectMapper mapper = ReportsObjectMapper.getInstance();

    @Test
    public void testPerPushDetailResponseDeserialize() throws IOException {

        String json = "{  \n" +
                "  \"app_key\":\"some_app_key\",\n" +
                "  \"push_id\":\"57ef3728-79dc-46b1-a6b9-20081e561f97\",\n" +
                "  \"created\":\"2013-07-31 22:05:53\",\n" +
                "  \"push_body\":\"PEJhc2U2NC1lbmNvZGVkIHN0cmluZz4=\",\n" +
                "  \"rich_deletions\":1,\n" +
                "  \"rich_responses\":2,\n" +
                "  \"rich_sends\":3,\n" +
                "  \"sends\":58,\n" +
                "  \"direct_responses\":4,\n" +
                "  \"influenced_responses\":5,\n" +
                "  \"platforms\":{  \n" +
                "    \"android\":{  \n" +
                "      \"direct_responses\":6,\n" +
                "      \"influenced_responses\":7,\n" +
                "      \"sends\":22\n" +
                "    },\n" +
                "    \"ios\":{  \n" +
                "      \"direct_responses\":8,\n" +
                "      \"influenced_responses\":9,\n" +
                "      \"sends\":36\n" +
                "    }\n" +
                "  }\n" +
                "}";

        PushDetailResponse obj = mapper.readValue(json, PushDetailResponse.class);
        assertNotNull(obj);

        assertEquals("some_app_key", obj.getAppKey());
        assertEquals(UUID.fromString("57ef3728-79dc-46b1-a6b9-20081e561f97"), obj.getPushID());
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-07-31 22:05:53"), obj.getCreated().get());

        assertEquals("PEJhc2U2NC1lbmNvZGVkIHN0cmluZz4=", obj.getPushBody().get().getBase64EncodedString());
        assertEquals("<Base64-encoded string>", new String(obj.getPushBody().get().getByteArray()));
        assertEquals("<Base64-encoded string>", obj.getPushBody().get().toString());

        assertEquals(1, obj.getRichDeletions());
        assertEquals(2, obj.getRichResponses());
        assertEquals(3, obj.getRichSends());
        assertEquals(58, obj.getSends());
        assertEquals(4, obj.getDirectResponses());
        assertEquals(5, obj.getInfluencedResponses());

        ImmutableMap<PlatformType, PerPushCounts> platforms = obj.getPlatforms();

        PerPushCounts android = platforms.get(PlatformType.ANDROID);

        assertEquals(6, android.getDirectResponses());
        assertEquals(7, android.getInfluencedResponses());
        assertEquals(22, android.getSends());

        PerPushCounts ios = platforms.get(PlatformType.IOS);

        assertEquals(8, ios.getDirectResponses());
        assertEquals(9, ios.getInfluencedResponses());
        assertEquals(36, ios.getSends());
    }

    @Test
    public void testPerPushDetailResponseDeserializeWithZeroNullValues() throws IOException {

        String json = "{  \n" +
                "  \"app_key\":\"some_app_key\",\n" +
                "  \"push_id\":\"57ef3728-79dc-46b1-a6b9-20081e561f97\",\n" +
                "  \"created\":0,\n" +
                "  \"push_body\":null,\n" +
                "  \"sends\":0,\n" +
                "  \"direct_responses\":0,\n" +
                "  \"influenced_responses\":0,\n" +
                "  \"rich_sends\":0,\n" +
                "  \"rich_responses\":0,\n" +
                "  \"rich_deletions\":0,\n" +
                "  \"platforms\":{  \n" +
                "    \"ios\":{  \n" +
                "      \"sends\":0,\n" +
                "      \"direct_responses\":0,\n" +
                "      \"influenced_responses\":0\n" +
                "    },\n" +
                "    \"android\":{  \n" +
                "      \"sends\":0,\n" +
                "      \"direct_responses\":0,\n" +
                "      \"influenced_responses\":0\n" +
                "    },\n" +
                "    \"amazon\":{  \n" +
                "      \"sends\":0,\n" +
                "      \"direct_responses\":0,\n" +
                "      \"influenced_responses\":0\n" +
                "    }\n" +
                "  }\n" +
                "}";

        PushDetailResponse obj = mapper.readValue(json, PushDetailResponse.class);
        assertNotNull(obj);

        assertEquals("some_app_key", obj.getAppKey());
        assertEquals(UUID.fromString("57ef3728-79dc-46b1-a6b9-20081e561f97"), obj.getPushID());
        assertEquals(Optional.<DateTime>absent(), obj.getCreated());

        assertEquals(Optional.<Base64ByteArray>absent(), obj.getPushBody());

        assertEquals(0, obj.getRichDeletions());
        assertEquals(0, obj.getRichResponses());
        assertEquals(0, obj.getRichSends());
        assertEquals(0, obj.getSends());
        assertEquals(0, obj.getDirectResponses());
        assertEquals(0, obj.getInfluencedResponses());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPerPushDetailResponseDeserializeWithInvalidPushBody() throws IOException {

        String json = "{  \n" +
                "  \"app_key\":\"some_app_key\",\n" +
                "  \"push_id\":\"57ef3728-79dc-46b1-a6b9-20081e561f97\",\n" +
                "  \"created\":0,\n" +
                "  \"push_body\":\"I am not a base64 encoded string\",\n" +
                "  \"sends\":0,\n" +
                "  \"direct_responses\":0,\n" +
                "  \"influenced_responses\":0,\n" +
                "  \"rich_sends\":0,\n" +
                "  \"rich_responses\":0,\n" +
                "  \"rich_deletions\":0,\n" +
                "  \"platforms\":{  \n" +
                "    \"ios\":{  \n" +
                "      \"sends\":0,\n" +
                "      \"direct_responses\":0,\n" +
                "      \"influenced_responses\":0\n" +
                "    },\n" +
                "    \"android\":{  \n" +
                "      \"sends\":0,\n" +
                "      \"direct_responses\":0,\n" +
                "      \"influenced_responses\":0\n" +
                "    },\n" +
                "    \"amazon\":{  \n" +
                "      \"sends\":0,\n" +
                "      \"direct_responses\":0,\n" +
                "      \"influenced_responses\":0\n" +
                "    }\n" +
                "  }\n" +
                "}";

        PushDetailResponse obj = mapper.readValue(json, PushDetailResponse.class);
        assertNotNull(obj);

        assertEquals("some_app_key", obj.getAppKey());
        assertEquals(UUID.fromString("57ef3728-79dc-46b1-a6b9-20081e561f97"), obj.getPushID());
        assertEquals(Optional.<DateTime>absent(), obj.getCreated());

        assertEquals(Optional.<Base64ByteArray>absent(), obj.getPushBody());

        assertEquals(0, obj.getRichDeletions());
        assertEquals(0, obj.getRichResponses());
        assertEquals(0, obj.getRichSends());
        assertEquals(0, obj.getSends());
        assertEquals(0, obj.getDirectResponses());
        assertEquals(0, obj.getInfluencedResponses());
    }

}
