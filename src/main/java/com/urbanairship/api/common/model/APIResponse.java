package com.urbanairship.api.common.model;

import com.urbanairship.api.common.APIException;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import javax.ws.rs.core.Response;

public class APIResponse {

    private final Optional<String> dataAttribute;
    private final Optional<ImmutableMap<String, Object>> data;
    private final Optional<APIError> error;
    private final Response.Status status;

    private APIResponse(Optional<String> dataAttribute,
                        Optional<ImmutableMap<String, Object>> data,
                        Optional<APIError> error,
                        Response.Status status) {
        this.dataAttribute = dataAttribute;
        this.data = data;
        this.error = error;
        this.status = status;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static APIResponse fromException(Throwable e) {
        return fromException(e, null);
    }

    public static APIResponse fromException(Throwable e, String operationId, Response.Status status) {
        if (e instanceof APIException) {
            return fromException((APIException)e, operationId);
        }
        return APIResponse.newBuilder()
                .setStatus(status)
                .setOperationId(operationId)
                .setError(APIError.fromException(e))
                .build();
    }

    public static APIResponse fromException(Throwable e, String operationId) {
        if (e instanceof APIException) {
            return fromException((APIException)e, operationId);
        }
        return APIResponse.newBuilder()
            .setStatus(Response.Status.INTERNAL_SERVER_ERROR)
            .setOperationId(operationId)
            .setError(APIError.fromException(e))
            .build();
    }

    public static APIResponse fromException(APIException e) {
        return fromException(e, null);
    }

    public static APIResponse fromException(APIException e, String operationId) {
        return APIResponse.newBuilder()
            .setStatus(e.getStatus())
            .setOperationId(operationId)
            .setError(APIError.fromException((APIException)e))
            .build();
    }

    public Optional<String> getDataAttribute() {
        return dataAttribute;
    }

    public Optional<String> getOperationId() {
        if (data == null || !data.isPresent()) {
            return Optional.absent();
        } else {
            return Optional.fromNullable((String)data.get().get(APIConstants.Field.OPERATION_ID));
        }
    }

    public Optional<ImmutableMap<String, Object>> getData() {
        return data;
    }

    public Optional<APIError> getError() {
        return error;
    }

    public Response.Status getStatus() {
        return status;
    }

    public static class Builder {

        private String dataAttribute;
        private ImmutableMap.Builder<String, Object> data;
        private APIError error;
        private Response.Status status = Response.Status.OK;

        private Builder() { }

        public Builder setData(String attributeName, Object value) {
            setDataAttribute(attributeName);
            put(attributeName, value);
            return this;
        }

        public Builder setDataAttribute(String value) {
            dataAttribute = value;
            return this;
        }

        public Builder setOperationId(String value) {
            if (value == null) {
                return this;
            }
            return put(APIConstants.Field.OPERATION_ID, value);
        }

        public Builder setError(APIError value) {
            error = value;
            return this;
        }

        public Builder setStatus(Response.Status value) {
            status = value;
            return this;
        }

        public Builder put(String key, Object value) {
            if (data == null) {
                data = ImmutableMap.<String, Object>builder();
            }
            data.put(key, value);
            return this;
        }

        public APIResponse build() {
            return new APIResponse(Optional.fromNullable(dataAttribute),
                                   data == null
                                   ? Optional.<ImmutableMap<String,Object>>absent()
                                   : Optional.fromNullable(data.build()),
                                   Optional.fromNullable(error),
                                   status);
        }
    }
}
