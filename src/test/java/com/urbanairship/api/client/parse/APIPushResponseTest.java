package com.urbanairship.api.client.parse;

import com.urbanairship.api.client.APIPushResponse;
import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import org.apache.log4j.BasicConfigurator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;


public class APIPushResponseTest {

    @Test
    public void testAPIPushResponse(){
        String pushJSON = "{\n" +
                "    \"ok\" : true,\n" +
                "    \"operation_id\" : \"df6a6b50\",\n" +
                "    \"push_ids\": [\n" +
                "        \"id1\",\n" +
                "        \"id2\"\n" +
                "    ]\n" +
                "}";

        ObjectMapper mapper = APIResponseObjectMapper.getInstance();
        try {
            APIPushResponse response = mapper.readValue(pushJSON, APIPushResponse.class);
            assertTrue("Error in response operationId",
                       response.getOperationId().get().equals("df6a6b50"));
            assertTrue("Error in response pushIds",
                       response.getPushIds().get().get(0).equals("id1"));
            assertTrue("Error in response pushIds",
                       response.getPushIds().get().get(1).equals("id2"));
        }
        catch (IOException ex){
            fail("Exception in APIPushResponseTest Message: " + ex.getMessage());
        }
    }
}
