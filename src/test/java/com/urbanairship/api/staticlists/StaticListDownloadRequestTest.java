package com.urbanairship.api.staticlists;


import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StaticListDownloadRequestTest {
    private static final String TEST_LIST_NAME = "testlist";
    private static final String STATIC_LIST_DOWNLOAD_PATH = "/api/lists/" + TEST_LIST_NAME + "/csv";
    private static final String OUTPUT_FILE_PATH = "src/test/data/out.csv";

    StaticListDownloadRequest request;
    StaticListDownloadRequest requestWithFile;


    @Before
    public void setup() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(OUTPUT_FILE_PATH));
        request = StaticListDownloadRequest.newRequest(TEST_LIST_NAME);
        requestWithFile = StaticListDownloadRequest.newRequest(TEST_LIST_NAME)
            .setOutputStream(fileOutputStream);
    }

    @Test
    public void testContentType() throws Exception {
        assertEquals(request.getContentType(), null);
        assertEquals(requestWithFile.getContentType(), null);
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(request.getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(requestWithFile.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");
        URI expectedUri = URI.create("https://go.urbanairship.com" + STATIC_LIST_DOWNLOAD_PATH);

        assertEquals(request.getUri(baseURI), expectedUri);
        assertEquals(requestWithFile.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_CSV);
        assertEquals(request.getRequestHeaders(), headers);
        assertEquals(requestWithFile.getRequestHeaders(), headers);
    }

    @Test
    public void testBody() throws Exception {
        assertEquals(request.getRequestBody(), null);
        assertEquals(requestWithFile.getRequestBody(), null);
    }

    @Test
    public void testParser() throws Exception {
        String response = "alias,stevenh\n" +
                "alias,marianb\n" +
                "ios_channel,b64a5105-20a1-459e-a15d-6aa22f4365f5\n" +
                "named_user,SDo09sczvJkoiu\n" +
                "named_user,\"gates,bill\"\n";


        assertEquals(request.getResponseParser().parse(response), response);
        assertEquals(requestWithFile.getResponseParser().parse(response), response);

        File outFile = new File(OUTPUT_FILE_PATH);
        assertTrue(outFile.exists());

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(outFile)));
        StringBuilder fileString = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            fileString.append(line).append("\n");
        }
        assertEquals(response, fileString.toString());
        br.close();
    }

    @After
    public void tearDown() {
        try {
            Files.deleteIfExists(Paths.get(OUTPUT_FILE_PATH));
        } catch (IOException e) {
            return;
        }
    }

}
