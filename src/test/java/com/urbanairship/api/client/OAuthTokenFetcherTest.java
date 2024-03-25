package com.urbanairship.api.client;

import com.urbanairship.api.client.parse.OAuthTokenResponse;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicStatusLine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.io.IOException;

import static com.urbanairship.api.client.UrbanAirshipClient.US_OAUTH_URI;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OAuthTokenFetcherTest {

    private OAuthTokenFetcher tokenFetcher;

    @Before
    public void setUp() {
        tokenFetcher = new OAuthTokenFetcher();
    }

    @Test
    public void testFetchToken_RetryOnUnauthorizedError() throws IOException {
        HttpClient httpClientMock = Mockito.mock(HttpClient.class);
        HttpResponse unauthorizedResponse = new HttpResponseStub(HttpStatus.SC_UNAUTHORIZED);
        when(httpClientMock.execute(any(HttpPost.class)))
                .thenReturn(unauthorizedResponse)
                .thenReturn(mockSuccessfulResponse());

        OAuthCredentials credentials = OAuthCredentials.newBuilder("client_id")
                .setClientSecret("client_secret")
                .setSub("test")
                .build();

        assertThrows(IOException.class, () -> tokenFetcher.fetchToken(credentials, US_OAUTH_URI));
    }

    @Test
    public void testFetchToken_InvalidCredentials() throws IOException {
        HttpClient httpClientMock = Mockito.mock(HttpClient.class);
        // 401 Unauthorized
        HttpResponse unauthorizedResponse = new HttpResponseStub(HttpStatus.SC_UNAUTHORIZED);
        when(httpClientMock.execute(any(HttpPost.class))).thenReturn(unauthorizedResponse);

        OAuthCredentials invalidCredentials = OAuthCredentials.newBuilder("test")
                .setClientSecret("client_secret")
                .setSub("test")
                .build();

        assertThrows(IOException.class, () -> tokenFetcher.fetchToken(invalidCredentials, US_OAUTH_URI));
        assertEquals(HttpStatus.SC_UNAUTHORIZED, unauthorizedResponse.getStatusLine().getStatusCode());
    }

    private HttpResponse mockSuccessfulResponse() {
        return new HttpResponseStub(HttpStatus.SC_OK);
    }

    private static class HttpResponseStub extends org.apache.http.message.BasicHttpResponse {
        public HttpResponseStub(int statusCode) {
            super(new BasicStatusLine(HttpVersion.HTTP_1_1, statusCode, null));
        }
    }
}
