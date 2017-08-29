package com.urbanairship.api.client;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClientExceptionTest {

    @Test
    @SuppressWarnings({"ThrowableResultOfMethodCallIgnored", "ThrowableInstanceNeverThrown"})
    public void testClientException() {
        RequestError requestError = RequestError.newBuilder()
                .setError("TEST")
                .setErrorCode(42)
                .setOperationId("OpID")
                .build();

        ClientException testException = ClientException.newBuilder()
                .setStatusText("Not Found")
                .setStatusCode(404)
                .setRequestError(requestError)
                .build();

        assertEquals("HTTP response code incorrect",
                testException.getStatusCode(), 404);
        assertEquals("APIError incorrect",
                testException.getError().get(), requestError);
        assertEquals("Status message incorrect",
                testException.getStatusText(), "Not Found");
        assertEquals("Exception message incorrect",
                testException.getMessage(), "Not Found");

    }
}
