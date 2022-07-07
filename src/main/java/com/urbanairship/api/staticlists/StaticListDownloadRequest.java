/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.staticlists;

import java.util.Optional;
import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.opencsv.CSVWriter;
import com.urbanairship.api.client.Request;
import com.urbanairship.api.client.RequestUtils;
import com.urbanairship.api.client.ResponseParser;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/*
 * The StaticListDownloadRequest class static list download request to be executed in
 * the {@link com.urbanairship.api.client.UrbanAirshipClient}.
 */
public class StaticListDownloadRequest implements Request<String> {
    private final static String API_LISTS_PATH = "/api/lists/";
    private final String path;

    private Optional<FileOutputStream> fileOutputStream = Optional.empty();

    private StaticListDownloadRequest(String name) {
        this.path = API_LISTS_PATH + name + "/csv";
    }

    /**
     * Build a static list download request.
     *
     * @param name The name of the list as a string.
     * @return StaticListDownloadRequest
     */
    public static StaticListDownloadRequest newRequest(String name) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(name), "List name cannot be empty.");
        return new StaticListDownloadRequest(name);
    }

    /**
     * Build a static list download request.
     *
     * @param type A lifecycle list type.
     * @return StaticListDownloadRequest
     */
    public static StaticListDownloadRequest newRequest(LifecycleListType type) {
        return new StaticListDownloadRequest(type.getType());
    }


    /**
     * Specify a file output stream to route the response data to.
     *
     * @param fileOutputStream The output stream to write to.
     * @return StaticListDownloadRequest
     */
    public StaticListDownloadRequest setOutputStream(FileOutputStream fileOutputStream) {
        this.fileOutputStream = Optional.of(fileOutputStream);
        return this;
    }

    @Override
    public ContentType getContentType() {
        return null;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeaders.ACCEPT, UA_VERSION_CSV);
        return headers;
    }

    @Override
    public Request.HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getRequestBody() {
        return null;
    }

    @Override
    public URI getUri(URI baseUri) {
        return RequestUtils.resolveURI(baseUri, path);
    }

    @Override
    public ResponseParser<String> getResponseParser() {
        return new ResponseParser<String>() {
            @Override
            public String parse(String response) throws IOException {
                if (fileOutputStream.isPresent()) {
                    try (OutputStreamWriter stream = new OutputStreamWriter(fileOutputStream.get());
                         CSVWriter writer = new CSVWriter(stream, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
                        String[] rows = response.split("\n");
                        for (String row : rows) {
                            writer.writeNext(row.split(","));
                        }
                    } finally {
                        fileOutputStream.get().close();
                    }
                }
                return response;
            }
        };
    }

    @Override
    public boolean bearerTokenAuthRequired() {
        return false;
    }

    @Override
    public boolean canUseBearerTokenAuth() {
        return true;
    }
}
