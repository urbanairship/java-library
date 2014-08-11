package com.urbanairship.api.common;

import javax.ws.rs.core.Response;

public class APINotFoundException extends APIException {

    public APINotFoundException(String message) {
        super(message);
    }

    public APINotFoundException(Throwable cause) {
        super(cause);
    }

    public APINotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public Response.Status getStatus() {
        return Response.Status.NOT_FOUND;
    }
}
