package com.urbanairship.api.client.parse;

import com.urbanairship.api.client.APIListTagsResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class APIListTagsResponseTest {

    @Test
    public void testAPIListTagsResponse(){

        String listtagresponse = "{\"tags\":[\"Puppies\",\"Kitties\",\"GrumpyCat\"]}";

        ObjectMapper mapper = APIResponseObjectMapper.getInstance();

        try {
            APIListTagsResponse response = mapper.readValue(listtagresponse, APIListTagsResponse.class);
            assertNotNull(response);

            List<String> tags = response.getTags();

            assertEquals("Puppies", tags.get(0));
            assertEquals("Kitties", tags.get(1));
            assertEquals("GrumpyCat", tags.get(2));
        } catch (Exception e){
            fail("Exception " + e.getMessage());
        }
    }
}
