package com.urbanairship.api.attributelists.parse;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.attributelists.model.AttributeListsListingResponse;
import com.urbanairship.api.attributelists.model.AttributeListsView;
import com.urbanairship.api.common.parse.DateFormats;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
public class AttributeListsListingResponseDeserializerTest {
    private static final ObjectMapper mapper = AttributeListsObjectMapper.getInstance();

    @Test
    public void testAttributeListsListingResponse() throws Exception {
        DateTime created = DateFormats.DATE_PARSER.parseDateTime("2013-08-08T20:41:06");
        DateTime updated = DateFormats.DATE_PARSER.parseDateTime("2014-05-01T18:00:27");

        String json = "{" +
                "\"ok\" : true," +
                "\"lists\" : [" +
                    "{" +
                        "\"name\" : \"ua_attributes_platinum_members\"," +
                        "\"description\" : \"loyalty program platinum members\"," +
                        "\"extra\" : {\"filename\": \"list.csv\",\"source\": \"crm\"}," +
                        "\"created\" : \"2013-08-08T20:41:06\"," +
                        "\"last_updated\" : \"2014-05-01T18:00:27\"," +
                        "\"channel_count\": 3145," +
                        "\"error_path\": \"https://go.urbanairship.com/api/attribute-lists/ua_attributes_my_list/errors\",\n" +
                        "\"status\": \"ready\"" +
                    "}," +
                    "{" +
                        "\"name\": \"ua_attributes_gold_members\"," +
                        "\"extra\": {\"filename\": \"list2.csv\",\"source\": \"api\"}," +
                        "\"created\": \"2013-08-08T20:41:06\"," +
                        "\"last_updated\": \"2014-05-01T18:00:27\"," +
                        "\"channel_count\": 678," +
                        "\"error_path\": \"https://go.urbanairship.com/api/attribute-lists/ua_attributes_my_list2/errors\",\n" +
                        "\"status\": \"ready\"" +
                    "}" +
                "]" +
            "}";

        AttributeListsListingResponse lists = mapper.readValue(json, AttributeListsListingResponse.class);
        assertNotNull(lists);
        assertTrue(lists.getOk());

        AttributeListsView list1 = lists.getAttributeListsViews().get(0);
        assertNotNull(list1);
        assertEquals("ua_attributes_platinum_members", list1.getName());
        assertEquals("loyalty program platinum members", list1.getDescription().get());
        assertEquals(created, list1.getCreated());
        assertEquals(updated, list1.getLastUpdated());
        assertEquals(Integer.valueOf(3145), list1.getChannelCount());
        assertEquals("https://go.urbanairship.com/api/attribute-lists/ua_attributes_my_list/errors", list1.getErrorPath());
        assertEquals("ready", list1.getStatus());

        AttributeListsView list2 = lists.getAttributeListsViews().get(1);
        assertNotNull(list2);
        assertEquals("ua_attributes_gold_members", list2.getName());
        assertEquals(Optional.empty(), list2.getDescription());
        assertEquals(created, list2.getCreated());
        assertEquals(updated, list2.getLastUpdated());
        assertEquals(Integer.valueOf(678), list2.getChannelCount());
        assertEquals("https://go.urbanairship.com/api/attribute-lists/ua_attributes_my_list2/errors", list2.getErrorPath());
        assertEquals("ready", list2.getStatus());

    }
}
