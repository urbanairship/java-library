package com.urbanairship.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.client.parse.RequestErrorObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RequestErrorTest {

    @Test
    public void testRequestErrorDetailsLocationDeserialization() throws Exception {
        String errorJSON = "{\"line\":5, \"column\":5}";
        ObjectMapper mapper = RequestErrorObjectMapper.getInstance();

        RequestErrorDetails.Location location = mapper.readValue(errorJSON, RequestErrorDetails.Location.class);
        assertEquals("Error in line", location.getLine(), 5);
        assertEquals("Error in column", location.getColumn(), 5);
    }

    @Test
    public void testRequestErrorDetailsDeserialization() throws Exception {
        String errorJson = "{\"error\":\"error\", \"path\":\"path\", \"location\":{\"line\":42,\"column\":42}}";
        ObjectMapper mapper = RequestErrorObjectMapper.getInstance();

        RequestErrorDetails errorDetails = mapper.readValue(errorJson, RequestErrorDetails.class);
        assertEquals("Error in detail error string", errorDetails.getError(), "error");
        assertEquals("Error in detail path string", errorDetails.getPath(), "path");

        RequestErrorDetails.Location testLocation = RequestErrorDetails.Location.newBuilder()
            .setColumn(42)
            .setLine(42)
            .build();
        RequestErrorDetails.Location location = errorDetails.getLocation().get();
        assertEquals("Error in detail location object", testLocation, location);
    }

    @Test
    public void testRequestErrorDeserialization() throws Exception{
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

        RequestError error = mapper.readValue(errorJSON, RequestError.class);
        assertEquals(RequestError.errorFromResponse(errorJSON, "application/vnd.urbanairship+json"), error);
        assertFalse("Error in ok", error.getOk());
        assertEquals("Error in operation id", error.getOperationId().get(), "operation id");
        assertEquals("Error in error code", error.getErrorCode().get(), 40001);
        assertEquals("Error in error string", error.getError(), "Invalid push content");

        RequestErrorDetails testDetails = RequestErrorDetails.newBuilder()
            .setError("error message")
            .setPath("push.wns.text")
            .setLocation(RequestErrorDetails.Location.newBuilder()
                .setLine(47)
                .setColumn(12)
                .build())
            .build();
        assertEquals("Error in the details", error.getDetails().get(), testDetails);
    }

    @Test
    public void testTemplateRequestErrorDeserialization() throws IOException {
        InputStream responseStream = this.getClass()
                .getResourceAsStream("/com/urbanairship/api/client/template-request-error.json");
        String response = new BufferedReader(new InputStreamReader(responseStream))
                .lines().collect(Collectors.joining("\n"));

        RequestError error = RequestError.errorFromResponse(response, RequestError.UA_APPLICATION_JSON_V3);

        Assert.assertFalse(error.getOk());

        String errorMessage = error.getError();
        Assert.assertEquals("\"id\" must be a valid GUID", errorMessage);
    }

    @Test
    public void testDeprecatedJsonRequestErrorDeserialization() throws Exception {
        String errorJSON = "{\"message\":\"Unauthorized\"}";
        RequestError error = RequestError.newBuilder().setError("Unauthorized").build();

        assertEquals(RequestError.errorFromResponse(errorJSON, "application/json"), error);
    }

    @Test
    public void testDeprecatedStringRequestErrorDeserialization() throws Exception {
        String errorString = "Unauthorized";
        RequestError error = RequestError.newBuilder().setError("Unauthorized").build();

        assertEquals(RequestError.errorFromResponse(errorString, "text/html"), error);
        assertEquals("Error in error string", error.getError(), "Unauthorized");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsOnNullString() throws Exception {
        @SuppressWarnings("UnusedAssignment") RequestError error = RequestError.newBuilder().build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsOnEmptyError() throws Exception {
        @SuppressWarnings("UnusedAssignment") RequestError error = RequestError.newBuilder().setError("").build();
    }
}
