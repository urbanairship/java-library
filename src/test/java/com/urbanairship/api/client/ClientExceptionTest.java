package com.urbanairship.api.client;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

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
                .setMessage("Not Found")
                .setStatusCode(404)
                .setRequestError(requestError)
                .build();

        assertTrue("HTTP response code incorrect",
                testException.getStatusCode() == 404);
        assertTrue("APIError incorrect",
                testException.getError().get().equals(requestError));
        assertTrue("Status message incorrect",
                testException.getStatusText().equals("Not Found"));
        assertTrue("Exception message incorrect",
                testException.getMessage().equals("Not Found"));

    }
}
