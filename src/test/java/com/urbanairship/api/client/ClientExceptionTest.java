package com.urbanairship.api.client;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import io.netty.handler.codec.http.HttpHeaders;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.filter.FilterContext;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;

public class ClientExceptionTest {

    @Mock
    HttpResponseStatus httpResponseStatus;
    @Mock
    Optional<ResponseCallback> responseCallback;
    @Mock
    HttpResponseStatus notFoundHttpResponseStatus;
    @Mock
    HttpResponseBodyPart bodyPart;
    @Mock
    ResponseParser parser;
    @Mock
    HttpHeaders httpHeaders;
    @Mock
    Predicate<FilterContext> retryPredicate;

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(wireMockConfig().dynamicPort().dynamicHttpsPort());


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(httpResponseStatus.getStatusCode()).thenReturn(401);
        Mockito.when(httpResponseStatus.getStatusText()).thenReturn("401 Unauthorized");
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
    public void tearDown() throws Exception {
        //TODO figure out why this is failing (httpResponseStatus and httpHeaders both fail similarly).
        //Mockito.verifyNoMoreInteractions(httpResponseStatus);
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
        ResponseAsyncHandler asyncHandler = new ResponseAsyncHandler(responseCallback, parser);

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
        Mockito.verifyZeroInteractions(responseCallback);
        Mockito.verifyZeroInteractions(parser);
    }

    @Test
    public void testNotFoundStatusCodeNoException() throws Exception {
        ResponseAsyncHandler asyncHandler = new ResponseAsyncHandler(responseCallback, parser);

        Mockito.when(bodyPart.getBodyPartBytes()).thenReturn("{\"ok\":false,\"error\":\"Invalid channel id.\",\"error_code\":40404}".getBytes());

        asyncHandler.onStatusReceived(notFoundHttpResponseStatus);

        Mockito.when(httpHeaders.get(anyString())).thenReturn("application/vnd.urbanairship+json;version=3");

        asyncHandler.onHeadersReceived(httpHeaders);

        asyncHandler.onBodyPartReceived(bodyPart);

        Mockito.verify(httpHeaders, Mockito.times(1)).entries();
        Mockito.verifyZeroInteractions(responseCallback);
        Mockito.verifyZeroInteractions(parser);
    }
}
