/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanairship.api.common.model.GenericResponse;

import java.net.URI;
import java.net.URISyntaxException;

public class RequestUtils {

    private final static ObjectMapper mapper = new ObjectMapper();
    public final static ResponseParser<GenericResponse> GENERIC_RESPONSE_PARSER = response -> mapper.readValue(response, GenericResponse.class);

    /**
     * A method to resolve base URIs without excluding the original path.
     * @param baseURI URI
     * @param path String
     * @return URI
     */
    public static URI resolveURI(URI baseURI, String path) {
        URI uri = baseURI;

        if (!uri.getPath().endsWith("/")) {
            try {
                uri = new URI(uri.toString() + "/");
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        if (path.startsWith("/"))  {
            path = path.substring(1);
        }

        return uri.resolve(path);
    }

}
