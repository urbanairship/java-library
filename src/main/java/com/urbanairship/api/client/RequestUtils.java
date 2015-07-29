package com.urbanairship.api.client;

import java.net.URI;
import java.net.URISyntaxException;

public class RequestUtils {

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
