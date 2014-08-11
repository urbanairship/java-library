package com.urbanairship.api.common.model;

import com.urbanairship.api.common.parse.APIParsingException;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

public final class APIError extends APIModelObject {

    private final String error;
    private final int error_code;
    private final Optional<APIErrorDetails> details;

    private APIError(String error, int error_code, Optional<APIErrorDetails> details) {
        this.error = error;
        this.error_code = error_code;
        this.details = details;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getError() {
        return error;
    }

    public int getErrorCode() {
        return error_code;
    }

    public Optional<APIErrorDetails> getDetails() {
        return details;
    }

    public static APIError fromException(Throwable e) {
        if (e instanceof APIParsingException) {
            return fromException((APIParsingException)e);
        } else {
            return APIError.newBuilder()
                .setError("Internal server error")
                .setErrorCode(50000)
                .build();
        }
    }

    public static APIError fromException(APIParsingException e) {
        APIError.Builder builder = APIError.newBuilder()
            .setError("Could not parse request body.") // TODO: find a location for these constants
            .setErrorCode(40000);
        APIErrorDetails.Builder details = APIErrorDetails.newBuilder()
            .setError(e.getMessage());
        if (e.getPath().isPresent()) {
            details.setPath(e.getPath().get());
        }
        if (e.getLocation().isPresent()) {
            details.setLocation(APIErrorDetails.Location.newBuilder()
                                .setLine(e.getLocation().get().getLineNr())
                                .setColumn(e.getLocation().get().getColumnNr())
                                .build());
        }
        return builder
            .setDetails(details.build())
            .build();
    }

    public static class Builder {
        private String error;
        private int error_code = 0;
        private APIErrorDetails details = null;

        private Builder() { }

        public Builder setError(String value) {
            error = value;
            return this;
        }

        public Builder setErrorCode(int value) {
            error_code = value;
            return this;
        }

        public Builder setDetails(APIErrorDetails value) {
            details = value;
            return this;
        }

        public APIError build() {
            Preconditions.checkNotNull(error);
            Preconditions.checkArgument(error_code > 0);

            return new APIError(error, error_code, Optional.fromNullable(details));
        }
    }
}
