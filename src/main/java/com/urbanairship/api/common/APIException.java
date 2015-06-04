/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.common;

import javax.ws.rs.core.Response;

public class APIException extends RuntimeException {

    private Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;

    public APIException(String message) {
        super(message);
    }

    public APIException(Throwable cause) {
        super(cause);
    }

    public APIException(String message, Throwable cause) {
        super(message, cause);
    }

    public APIException(Response.Status status, String message) {
        super(message);
        this.status = status;
    }

    public APIException(Response.Status status, Throwable cause) {
        super(cause);
        this.status = status;
    }

    public APIException(Response.Status status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public Response.Status getStatus() {
        return status;
    }
}
