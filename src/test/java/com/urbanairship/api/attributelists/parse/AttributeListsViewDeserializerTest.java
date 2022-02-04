package com.urbanairship.api.attributelists.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.urbanairship.api.attributelists.model.AttributeListsView;
import com.urbanairship.api.common.parse.DateFormats;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AttributeListsViewDeserializerTest {

    private static final ObjectMapper mapper = AttributeListsObjectMapper.getInstance();

    @Test
    public void testAttributeListsView() throws Exception {
        DateTime created = DateFormats.DATE_PARSER.parseDateTime("2014-10-01T12:00:00");
        DateTime updated = DateFormats.DATE_PARSER.parseDateTime("2014-10-03T12:00:00");

        String json = "{" +
                "\"ok\" : true," +
                "\"name\" : \"ua_attributes_platinum_members\"," +
                "\"description\" : \"loyalty program platinum members\"," +
                "\"extra\" : { \"key\" : \"value\" }," +
                "\"created\" : \"2014-10-01T12:00:00\"," +
                "\"last_updated\" : \"2014-10-03T12:00:00\"," +
                "\"channel_count\" : 1000," +
                "\"error_path\": \"https://go.urbanairship.com/api/attribute-lists/ua_attributes_my_list/errors\",\n" +
                "\"status\" : \"ready\"" +
                "}";

        AttributeListsView list = mapper.readValue(json, AttributeListsView.class);
        assertEquals(Optional.of(true), list.getOk());
        assertEquals("ua_attributes_platinum_members", list.getName());
        assertEquals(Optional.of("loyalty program platinum members"), list.getDescription());
        assertEquals(created, list.getCreated());
        assertEquals(updated, list.getLastUpdated());
        assertEquals("https://go.urbanairship.com/api/attribute-lists/ua_attributes_my_list/errors", list.getErrorPath());
        assertEquals(Integer.valueOf(1000), list.getChannelCount());
        assertEquals("ready", list.getStatus());
    }
}
