package com.urbanairship.api.client;

import com.urbanairship.api.client.parse.APIResponseObjectMapper;
import org.apache.log4j.BasicConfigurator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class APIErrorTest {

    @Test
    public void testAPIErrorDetailsLocationDeserialization(){

        String errorJSON = "{\"line\":5, \"column\":5}";

        ObjectMapper mapper = APIResponseObjectMapper.getInstance();
        try {
            APIErrorDetails.Location location = mapper.readValue(errorJSON, APIErrorDetails.Location.class);
            assertTrue("Error in line", location.getLine().equals(5));
            assertTrue("Error in column", location.getColumn().equals(5));
        }
        catch (Exception ex){
            fail("Exception " + ex.getMessage());
        }

    }

    @Test
    public void testAPIErrorDetailsDeserialization(){
        String errorJson = "{\"error\":\"error\", \"path\":\"path\", \"location\":{\"line\":42,\"column\":42}}";

        ObjectMapper mapper = APIResponseObjectMapper.getInstance();

        try{
            APIErrorDetails errorDetails = mapper.readValue(errorJson, APIErrorDetails.class);
            assertTrue("Error in detail error string", errorDetails.getError().equals("error"));
            assertTrue("Error in detail path string", errorDetails.getPath().equals("path"));
            APIErrorDetails.Location testLocation = APIErrorDetails.Location.newBuilder()
                    .setColumn(42)
                    .setLine(42)
                    .build();
            APIErrorDetails.Location location = errorDetails.getLocation().get();
            assertTrue("Error in detail location object", testLocation.equals(location));
        }
        catch (Exception ex){
            fail("Exception " + ex.getMessage());
        }
    }

    @Test
    public void testAPIErrorDeserialization(){
        String errorJSON = "{\n" +
                "    \"ok\" : false,\n" +
                "    \"operation_id\" : \"operation id\",\n" +
                "    \"error\" : \"Invalid push content\",\n" +
                "    \"error_code\" : 40001,\n" +
                "    \"details\" : {\n" +
                "        \"error\" : \"error message\",\n" +
                "        \"path\" : \"push.wns.text\",\n" +
                "        \"location\" : {\n" +
                "            \"line\" : 47,\n" +
                "            \"column\" : 12\n" +
                "        }\n" +
                "    }\n" +
                "}";
        ObjectMapper mapper = APIResponseObjectMapper.getInstance();

        try {
            APIError error = mapper.readValue(errorJSON, APIError.class);
            assertTrue("Error in operation id", error.getOperationId().get().equals("operation id"));
            assertTrue("Error in error code", error.getErrorCode().get().equals(40001));
            assertTrue("Error in error string", error.getError().equals("Invalid push content"));
            APIErrorDetails testDetails = APIErrorDetails.newBuilder()
                    .setError("error message")
                    .setPath("push.wns.text")
                    .setLocation(APIErrorDetails.Location.newBuilder()
                                                .setLine(47)
                                                .setColumn(12)
                                                .build())
                    .build();
            assertTrue("Error in the details", error.getDetails().get().equals(testDetails));
        }
        catch (Exception ex){
            fail("Exception " + ex.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsOnNullString(){
        @SuppressWarnings("UnusedAssignment") APIError error = APIError.newBuilder().build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsOnEmptyError(){
        @SuppressWarnings("UnusedAssignment") APIError error = APIError.newBuilder().setError("").build();
    }
}
