/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.parse;


import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import com.urbanairship.api.common.parse.DateFormats;
import com.urbanairship.api.reports.model.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PerPushSeriesResponseDeserializerTest {

    private static final ObjectMapper mapper = APIResponseObjectMapper.getInstance();

    @Test
    public void testPerPushSeriesDetailResponseDeserialize() throws IOException {

        String json = "{  \n" +
                "  \"app_key\":\"some_app_key\",\n" +
                "  \"push_id\":\"57ef3728-79dc-46b1-a6b9-20081e561f97\",\n" +
                "  \"start\":\"2013-07-25 23:00:00\",\n" +
                "  \"end\":\"2013-07-26 11:00:00\",\n" +
                "  \"precision\":\"HOURLY\",\n" +
                "  \"counts\":[  \n" +
                "    {  \n" +
                "      \"push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"direct_responses\":1,\n" +
                "          \"influenced_responses\":2,\n" +
                "          \"sends\":58\n" +
                "        },\n" +
                "        \"android\":{  \n" +
                "          \"direct_responses\":3,\n" +
                "          \"influenced_responses\":4,\n" +
                "          \"sends\":22\n" +
                "        },\n" +
                "        \"ios\":{  \n" +
                "          \"direct_responses\":5,\n" +
                "          \"influenced_responses\":6,\n" +
                "          \"sends\":36\n" +
                "        }\n" +
                "      },\n" +
                "      \"rich_push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"responses\":7,\n" +
                "          \"sends\":8\n" +
                "        }\n" +
                "      },\n" +
                "      \"time\":\"2013-07-25 23:00:00\"\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"direct_responses\":9,\n" +
                "          \"influenced_responses\":10,\n" +
                "          \"sends\":11\n" +
                "        },\n" +
                "        \"android\":{  \n" +
                "          \"direct_responses\":12,\n" +
                "          \"influenced_responses\":13,\n" +
                "          \"sends\":14\n" +
                "        },\n" +
                "        \"ios\":{  \n" +
                "          \"direct_responses\":15,\n" +
                "          \"influenced_responses\":16,\n" +
                "          \"sends\":17\n" +
                "        }\n" +
                "      },\n" +
                "      \"rich_push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"responses\":18,\n" +
                "          \"sends\":19\n" +
                "        }\n" +
                "      },\n" +
                "      \"time\":\"2013-07-26 00:00:00\"\n" +
                "    },\n" +
                "    {  \n" +
                "      \"push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"direct_responses\":20,\n" +
                "          \"influenced_responses\":21,\n" +
                "          \"sends\":22\n" +
                "        },\n" +
                "        \"android\":{  \n" +
                "          \"direct_responses\":23,\n" +
                "          \"influenced_responses\":24,\n" +
                "          \"sends\":25\n" +
                "        },\n" +
                "        \"ios\":{  \n" +
                "          \"direct_responses\":26,\n" +
                "          \"influenced_responses\":27,\n" +
                "          \"sends\":28\n" +
                "        }\n" +
                "      },\n" +
                "      \"rich_push_platforms\":{  \n" +
                "        \"all\":{  \n" +
                "          \"responses\":29,\n" +
                "          \"sends\":30\n" +
                "        }\n" +
                "      },\n" +
                "      \"time\":\"2013-07-26 01:00:00\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        PerPushSeriesResponse obj = mapper.readValue(json, PerPushSeriesResponse.class);
        assertNotNull(obj);

        assertEquals("some_app_key", obj.getAppKey());
        assertEquals(UUID.fromString("57ef3728-79dc-46b1-a6b9-20081e561f97"), obj.getPushID());
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-07-25 23:00:00"), obj.getStart());
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-07-26 11:00:00"), obj.getEnd());
        assertEquals("HOURLY", obj.getPrecision());

        List<PlatformCounts> listCounts = obj.getCounts();
        PlatformCounts one = listCounts.get(0);
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-07-25 23:00:00"), one.getTime());

        ImmutableMap<PlatformType, PerPushCounts> onePush = one.getPushPlatforms();

        PerPushCounts onePushAll = onePush.get(PlatformType.ALL);
        assertEquals(1, onePushAll.getDirectResponses());
        assertEquals(2, onePushAll.getInfluencedResponses());
        assertEquals(58, onePushAll.getSends());

        PerPushCounts onePushAndroid = onePush.get(PlatformType.ANDROID);
        assertEquals(3, onePushAndroid.getDirectResponses());
        assertEquals(4, onePushAndroid.getInfluencedResponses());
        assertEquals(22, onePushAndroid.getSends());

        PerPushCounts onePushIOS = onePush.get(PlatformType.IOS);
        assertEquals(5, onePushIOS.getDirectResponses());
        assertEquals(6, onePushIOS.getInfluencedResponses());
        assertEquals(36, onePushIOS.getSends());

        ImmutableMap<PlatformType, RichPerPushCounts> oneRich = one.getRichPushPlatforms();

        RichPerPushCounts oneRichAll = oneRich.get(PlatformType.ALL);
        assertEquals(7, oneRichAll.getResponses());
        assertEquals(8, oneRichAll.getSends());

        PlatformCounts two = listCounts.get(1);
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-07-26 00:00:00"), two.getTime());

        ImmutableMap<PlatformType, PerPushCounts> twoPush = two.getPushPlatforms();

        PerPushCounts twoPushAll = twoPush.get(PlatformType.ALL);
        assertEquals(9, twoPushAll.getDirectResponses());
        assertEquals(10, twoPushAll.getInfluencedResponses());
        assertEquals(11, twoPushAll.getSends());

        PerPushCounts twoPushAndroid = twoPush.get(PlatformType.ANDROID);
        assertEquals(12, twoPushAndroid.getDirectResponses());
        assertEquals(13, twoPushAndroid.getInfluencedResponses());
        assertEquals(14, twoPushAndroid.getSends());

        PerPushCounts twoPushIOS = twoPush.get(PlatformType.IOS);
        assertEquals(15, twoPushIOS.getDirectResponses());
        assertEquals(16, twoPushIOS.getInfluencedResponses());
        assertEquals(17, twoPushIOS.getSends());

        ImmutableMap<PlatformType, RichPerPushCounts> twoRich = two.getRichPushPlatforms();

        RichPerPushCounts twoRichAll = twoRich.get(PlatformType.ALL);
        assertEquals(18, twoRichAll.getResponses());
        assertEquals(19, twoRichAll.getSends());

        PlatformCounts three = listCounts.get(2);
        assertEquals(DateFormats.DATE_PARSER.parseDateTime("2013-07-26 01:00:00"), three.getTime());

        ImmutableMap<PlatformType, PerPushCounts> threePush = three.getPushPlatforms();

        PerPushCounts threePushAll = threePush.get(PlatformType.ALL);
        assertEquals(20, threePushAll.getDirectResponses());
        assertEquals(21, threePushAll.getInfluencedResponses());
        assertEquals(22, threePushAll.getSends());

        PerPushCounts threePushAndroid = threePush.get(PlatformType.ANDROID);
        assertEquals(23, threePushAndroid.getDirectResponses());
        assertEquals(24, threePushAndroid.getInfluencedResponses());
        assertEquals(25, threePushAndroid.getSends());

        PerPushCounts threePushIOS = threePush.get(PlatformType.IOS);
        assertEquals(26, threePushIOS.getDirectResponses());
        assertEquals(27, threePushIOS.getInfluencedResponses());
        assertEquals(28, threePushIOS.getSends());

        ImmutableMap<PlatformType, RichPerPushCounts> threeRich = three.getRichPushPlatforms();

        RichPerPushCounts threeRichAll = threeRich.get(PlatformType.ALL);
        assertEquals(29, threeRichAll.getResponses());
        assertEquals(30, threeRichAll.getSends());

    }
}
