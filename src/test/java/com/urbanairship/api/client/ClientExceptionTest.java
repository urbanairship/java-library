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
import org.mockito.*;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;

public class ClientExceptionTest {
    public final static String CONTENT_TYPE_KEY = "Content-type";

    @Mock
    HttpResponseStatus httpResponseStatus;
    @Mock
    Optional<ResponseCallback> responseCallback;
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
    }

    @After
    public void tearDown() throws Exception {
        MockingDetails mockingDetails = Mockito.mockingDetails(httpResponseStatus);

        //TODO figure out why this is failing (httpResponseStatus and httpHeaders both fail similarly).
        Mockito.verifyNoMoreInteractions(httpResponseStatus);
        //Mockito.verifyNoMoreInteractions(httpResponseStatus, responseCallback, parser, httpHeaders);
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
                .setStatusText("Unauthorized")
                .setStatusCode(401)
                .setRequestError(requestError)
                .build();

        assertEquals("HTTP response code incorrect",
                testException.getStatusCode(), 401);
        assertEquals("APIError incorrect",
                testException.getError().get(), requestError);
        assertEquals("Status message incorrect",
                testException.getStatusText(), "Unauthorized");
        assertEquals("Exception message incorrect",
                testException.getMessage(), "Unauthorized");

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
        } catch (Exception e) {
            assertTrue(e instanceof ClientException);
        }

        Mockito.verifyZeroInteractions(responseCallback);
        Mockito.verifyZeroInteractions(parser);
    }

    @Test
    public void testNotFoundStatusCode() {
        //TODO Finish this test. 404 should no longer throw an exception
    }
}
