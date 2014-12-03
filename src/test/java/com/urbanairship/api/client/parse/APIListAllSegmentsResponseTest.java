package com.urbanairship.api.client.parse;

import com.urbanairship.api.client.model.APIListAllSegmentsResponse;
import com.urbanairship.api.client.model.SegmentInformation;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class APIListAllSegmentsResponseTest {

    @Test
    public void testAPIListAllSegmentsResponse() {

        String testresponse = "{\n" +
                "   \"next_page\": \"https://go.urbanairship.com/api/segments?limit=1&sort=id&order=asc&start=3832cf72-cb44-4132-a11f-eafb41b82f64\",\n" +
                "   \"segments\": [\n" +
                "      {\n" +
                "         \"creation_date\": 1346248822220,\n" +
                "         \"display_name\": \"A segment\",\n" +
                "         \"id\": \"00c0d899-a595-4c66-9071-bc59374bbe6b\",\n" +
                "         \"modification_date\": 1346248822221\n" +
                "      }\n" +
                "   ]\n" +
                "}";

        ObjectMapper mapper = APIResponseObjectMapper.getInstance();

        try {
            APIListAllSegmentsResponse response = mapper.readValue(testresponse, APIListAllSegmentsResponse.class);
            assertEquals("https://go.urbanairship.com/api/segments?limit=1&sort=id&order=asc&start=3832cf72-cb44-4132-a11f-eafb41b82f64", response.getNextPage());
            assertEquals(1, response.getSegments().size());

            SegmentInformation si = response.getSegments().get(0);

            assertEquals(1346248822220L, (long) si.getCreationDate());
            assertEquals("A segment", si.getDisplayName());
            assertEquals("00c0d899-a595-4c66-9071-bc59374bbe6b", si.getId());
            assertEquals(1346248822221L, (long) si.getModificationDate());

        }
        catch (Exception ex){
            fail("Exception " + ex.getMessage());
        }
    }
}
