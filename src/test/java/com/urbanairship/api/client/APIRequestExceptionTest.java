package com.urbanairship.api.client;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class APIRequestExceptionTest {

    @Test
    @SuppressWarnings({"ThrowableResultOfMethodCallIgnored", "ThrowableInstanceNeverThrown"})
    public void testAPIException(){
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP",1,1), 404, "Not Found"));
        String testMsg = "Test Message";
        APIError apiError = APIError.newBuilder()
                .setError("TEST")
                .setErrorCode(42)
                .setOperationId("OpID")
                .build();

        RuntimeException runtimeException =
                new RuntimeException("Test RuntimeException");

        APIRequestException testException = APIRequestException.newBuilder()
                .setMessage(testMsg)
                .setCause(runtimeException)
                .setApiError(apiError)
                .setHttpResponse(httpResponse)
                .build();

        assertTrue("HttpMessage set improperly",
                   testException.getHttpResponse().equals(httpResponse));
        assertTrue("HTTP response code incorrect",
                   testException.httpResponseStatusCode() == 404);
        assertTrue("APIError incorrect",
                   testException.getError().get().equals(apiError));
        assertTrue("Status message incorrect",
                   testException.httpResponseStatusMessage().equals("Not Found"));
        assertTrue("Throwable cause incorrect",
                   testException.getCause().equals(runtimeException));
        assertTrue("Exception message incorrect",
                   testException.getMessage().equals(testMsg));

    }
}
