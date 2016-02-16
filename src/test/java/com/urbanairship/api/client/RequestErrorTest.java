package com.urbanairship.api.client;

import com.urbanairship.api.client.parse.RequestErrorObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RequestErrorTest {

    @Test
    public void testRequestErrorDetailsLocationDeserialization() {

        String errorJSON = "{\"line\":5, \"column\":5}";

        ObjectMapper mapper = RequestErrorObjectMapper.getInstance();
        try {
            RequestErrorDetails.Location location = mapper.readValue(errorJSON, RequestErrorDetails.Location.class);
            assertTrue("Error in line", location.getLine().equals(5));
            assertTrue("Error in column", location.getColumn().equals(5));
        } catch (Exception ex) {
            fail("Exception " + ex.getMessage());
        }

    }

    @Test
    public void testRequestErrorDetailsDeserialization() {
        String errorJson = "{\"error\":\"error\", \"path\":\"path\", \"location\":{\"line\":42,\"column\":42}}";

        ObjectMapper mapper = RequestErrorObjectMapper.getInstance();

        try {
            RequestErrorDetails errorDetails = mapper.readValue(errorJson, RequestErrorDetails.class);
            assertTrue("Error in detail error string", errorDetails.getError().equals("error"));
            assertTrue("Error in detail path string", errorDetails.getPath().equals("path"));
            RequestErrorDetails.Location testLocation = RequestErrorDetails.Location.newBuilder()
                    .setColumn(42)
                    .setLine(42)
                    .build();
            RequestErrorDetails.Location location = errorDetails.getLocation().get();
            assertTrue("Error in detail location object", testLocation.equals(location));
        } catch (Exception ex) {
            fail("Exception " + ex.getMessage());
        }
    }

    @Test
    public void testRequestErrorDeserialization() {
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
        ObjectMapper mapper = RequestErrorObjectMapper.getInstance();

        try {
            RequestError error = mapper.readValue(errorJSON, RequestError.class);

            assertEquals(RequestError.errorFromResponse(errorJSON, "application/vnd.urbanairship+json"), error);
            assertFalse("Error in ok", error.getOk());
            assertTrue("Error in operation id", error.getOperationId().get().equals("operation id"));
            assertTrue("Error in error code", error.getErrorCode().get().equals(40001));
            assertTrue("Error in error string", error.getError().equals("Invalid push content"));
            RequestErrorDetails testDetails = RequestErrorDetails.newBuilder()
                    .setError("error message")
                    .setPath("push.wns.text")
                    .setLocation(RequestErrorDetails.Location.newBuilder()
                            .setLine(47)
                            .setColumn(12)
                            .build())
                    .build();
            assertTrue("Error in the details", error.getDetails().get().equals(testDetails));
        } catch (Exception ex) {
            fail("Exception " + ex.getMessage());
        }
    }

    @Test
    public void testDeprecatedJsonRequestErrorDeserialization() {
        String errorJSON = "{\"message\":\"Unauthorized\"}";
        try {
            RequestError error = RequestError.newBuilder().setError("Unauthorized").build();

            assertEquals(RequestError.errorFromResponse(errorJSON, "application/json"), error);
            assertTrue("Error in error string", error.getError().equals("Unauthorized"));
        } catch (Exception ex) {
            fail("Exception " + ex.getMessage());
        }
    }

    @Test
    public void testDeprecatedStringRequestErrorDeserialization() {
        String errorString = "Unauthorized";
        try {
            RequestError error = RequestError.newBuilder().setError("Unauthorized").build();

            assertEquals(RequestError.errorFromResponse(errorString, "text/html"), error);
            assertTrue("Error in error string", error.getError().equals("Unauthorized"));
        } catch (Exception ex) {
            fail("Exception " + ex.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsOnNullString() {
        @SuppressWarnings("UnusedAssignment") RequestError error = RequestError.newBuilder().build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsOnEmptyError() {
        @SuppressWarnings("UnusedAssignment") RequestError error = RequestError.newBuilder().setError("").build();
    }
}
