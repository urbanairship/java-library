package com.urbanairship.api.staticlists;

import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.common.model.GenericResponse;
import com.urbanairship.api.staticlists.parse.StaticListsObjectMapper;

import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StaticListUploadRequestTest {
    private static final String TEST_LIST_NAME = "testlist";
    private static final String TEST_CSV_FILE = (new File("src/test/data")).getAbsolutePath() + "/test.csv";
    private static final String STATIC_LIST_UPLOAD_PATH = "/api/lists/" + TEST_LIST_NAME + "/csv";
    private static final String CONTENT_ENCODING_GZIP = "gzip";

    StaticListUploadRequest request;

    @Before
    public void setup() {
        request = StaticListUploadRequest.newRequest(TEST_LIST_NAME, TEST_CSV_FILE);
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(request.getContentType(), ContentType.TEXT_PLAIN);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(request.getHttpMethod(), Request.HttpMethod.PUT);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");

        URI expectedUri = URI.create("https://go.urbanairship.com" + STATIC_LIST_UPLOAD_PATH);
        assertEquals(request.getUri(baseURI), expectedUri);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_JSON);
        headers.put(HttpHeaders.CONTENT_TYPE, Request.CONTENT_TYPE_TEXT_CSV);
        assertEquals(request.getRequestHeaders(), headers);

        request.setGzipEnabled(true);
        headers.put(HttpHeaders.CONTENT_ENCODING, CONTENT_ENCODING_GZIP);
        assertEquals(request.getRequestHeaders(), headers);
    }

    @Test
    public void testBody() throws Exception {
        String expected = "alias,stevenh\n" +
                "alias,marianb\n" +
                "ios_channel,b64a5105-20a1-459e-a15d-6aa22f4365f5\n" +
                "named_user,SDo09sczvJkoiu\n" +
                "named_user,\"gates,bill\"\n";

        assertEquals(expected, request.getRequestBody());
    }

    @Test
    public void testParser() throws Exception {
        GenericResponse genericResponse = new GenericResponse(true, null, null, null);

        String responseJson = "{" +
                "\"ok\": true" +
                "}";

        assertEquals(request.getResponseParser().parse(responseJson), genericResponse);

    }
}
