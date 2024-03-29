package com.urbanairship.api.client;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import io.netty.handler.codec.http.HttpHeaders;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

public class ClientExceptionTest {

    @Mock
    HttpResponseStatus httpResponseStatus;
    @Mock
    ResponseCallback responseCallback;
    @Mock
    HttpResponseStatus notFoundHttpResponseStatus;
    @Mock
    HttpResponseStatus serverErrorHttpResponseStatus;
    @Mock
    HttpResponseBodyPart bodyPart;
    @Mock
    ResponseParser parser;
    @Mock
    HttpHeaders httpHeaders;
    @Mock
    JsonParser jsonParser;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(httpResponseStatus.getStatusCode()).thenReturn(401);
        Mockito.when(httpResponseStatus.getStatusText()).thenReturn("401 Unauthorized");

        Mockito.when(serverErrorHttpResponseStatus.getStatusCode()).thenReturn(502);
        Mockito.when(serverErrorHttpResponseStatus.getStatusText()).thenReturn("502 Internal Server Error");

        Mockito.when(notFoundHttpResponseStatus.getStatusCode()).thenReturn(404);
        Mockito.when(notFoundHttpResponseStatus.getStatusText()).thenReturn("404 Invalid Channel Id.");

        List<Map.Entry<String, String>> entries = new LinkedList<>();
        entries.add(new AbstractMap.SimpleEntry<>("Content-Length", "61"));
        entries.add(new AbstractMap.SimpleEntry<>("Connection", "keep-alive"));
        entries.add(new AbstractMap.SimpleEntry<>("Date", "Tue, 28 Jan 2020 20:42:04 GMT"));
        entries.add(new AbstractMap.SimpleEntry<>("Content-Type", "application/vnd.urbanairship+json;version=3"));


        Mockito.when(httpHeaders.entries()).thenReturn(entries);
    }

    @After
    public void tearDown() {
        Mockito.verifyNoMoreInteractions(httpResponseStatus, responseCallback, parser, httpHeaders);
    }
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

    @Test
    public void testResponseAsyncHandler() throws Exception {
        ResponseAsyncHandler asyncHandler = new ResponseAsyncHandler(Optional.of(responseCallback), parser);

        Mockito.when(bodyPart.getBodyPartBytes()).thenReturn("{\"ok\":false,\"error\":\"Unauthorized\",\"error_code\":40101}".getBytes());

        asyncHandler.onStatusReceived(httpResponseStatus);

        Mockito.when(httpHeaders.get(anyString())).thenReturn("application/vnd.urbanairship+json;version=3");

        asyncHandler.onHeadersReceived(httpHeaders);

        try {
            asyncHandler.onBodyPartReceived(bodyPart);
            throw new RuntimeException("This line should never be reached.");
        } catch (ClientException e) {
            assertEquals(401, e.getStatusCode());
        }

        Mockito.verify(httpResponseStatus, Mockito.times(1)).getStatusCode();
        Mockito.verify(httpResponseStatus, Mockito.times(1)).getStatusText();
        Mockito.verify(httpHeaders, Mockito.times(1)).get(anyString());
        Mockito.verifyNoInteractions(responseCallback);
        Mockito.verifyNoInteractions(parser);
    }

    @Test
    public void testAkamaiResponse() throws Exception {
        ResponseAsyncHandler asyncHandler = new ResponseAsyncHandler(Optional.of(responseCallback), parser);

        Mockito.when(bodyPart.getBodyPartBytes()).thenReturn("Internal Server Error".getBytes());

        asyncHandler.onStatusReceived(serverErrorHttpResponseStatus);

        Mockito.when(httpHeaders.get(anyString())).thenReturn("text/html");

        asyncHandler.onHeadersReceived(httpHeaders);

        try {
            asyncHandler.onBodyPartReceived(bodyPart);
            throw new RuntimeException("This line should never be reached.");
        } catch (ServerException e) {
            assertEquals(502, e.getStatusCode().intValue());
        }

        Mockito.verify(serverErrorHttpResponseStatus, Mockito.times(1)).getStatusCode();
        Mockito.verify(serverErrorHttpResponseStatus, Mockito.times(1)).getStatusText();
        Mockito.verify(httpHeaders, Mockito.times(1)).get(anyString());
        Mockito.verifyNoInteractions(responseCallback);
        Mockito.verifyNoInteractions(parser);
    }

    @Test
    public void testNotFoundStatusCodeNoException() throws Exception {
        ResponseAsyncHandler asyncHandler = new ResponseAsyncHandler(Optional.of(responseCallback), parser);

        Mockito.when(bodyPart.getBodyPartBytes()).thenReturn("{\"ok\":false,\"error\":\"Invalid channel id.\",\"error_code\":40404}".getBytes());

        asyncHandler.onStatusReceived(notFoundHttpResponseStatus);

        Mockito.when(httpHeaders.get(anyString())).thenReturn("application/vnd.urbanairship+json;version=3");

        asyncHandler.onHeadersReceived(httpHeaders);

        asyncHandler.onBodyPartReceived(bodyPart);

        Mockito.verify(httpHeaders, Mockito.times(1)).entries();
        Mockito.verifyNoInteractions(responseCallback);
        Mockito.verifyNoInteractions(parser);
    }
}
