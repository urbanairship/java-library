package com.urbanairship.api.segment.parse;

import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.audience.location.DateRange;
import com.urbanairship.api.push.model.audience.location.DateRangeUnit;
import com.urbanairship.api.reports.parse.ReportsObjectMapper;
import com.urbanairship.api.segments.model.SegmentView;
import com.urbanairship.api.segments.parse.SegmentObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SegmentViewDeserializerTest {
    private static final ObjectMapper mapper = SegmentObjectMapper.getInstance();

    @Test
    public void testSegmentViewDeserializer() throws Exception {

        String json = "{  \n" +
                "  \"display_name\":\"elOhEl\",\n" +
                "  \"criteria\":{  \n" +
                "    \"and\":[  \n" +
                "      {  \n" +
                "        \"not\":{  \n" +
                "          \"tag\":\"not-tag\"\n" +
                "        }\n" +
                "      },\n" +
                "      {  \n" +
                "          \"tag\":\"yay\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        Selector criteria = Selectors.and(Selectors.not(Selectors.tag("not-tag")), Selectors.tag("yay"));
        SegmentView obj = mapper.readValue(json, SegmentView.class);
        assertNotNull(obj);

        assertEquals("elOhEl",obj.getDisplayName());
        assertEquals(criteria, obj.getCriteria());
    }
}
