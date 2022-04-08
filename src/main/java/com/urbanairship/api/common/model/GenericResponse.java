package com.urbanairship.api.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.google.common.base.Optional;

public class GenericResponse {
    private final Boolean ok;
    private final String operationId;
    private final String error;
    private final ErrorDetails errorDetails;

    public GenericResponse(
        @JsonProperty("ok") Boolean ok, 
        @JsonProperty("operation_id") String operationId, 
        @JsonProperty("error") String error,
        @JsonProperty("details") ErrorDetails errorDetails) {
        this.ok = ok;
        this.operationId = operationId;
        this.error = error;
        this.errorDetails = errorDetails;
    
    }

    public Optional<Boolean> getOk() {
        return Optional.fromNullable(ok);
    }

    public Optional<String> getOperationId() {
        return Optional.fromNullable(operationId);
    }

    public Optional<String> getError() {
        return Optional.fromNullable(error);
    }

    public Optional<ErrorDetails> getErrorDetails() {
        return Optional.fromNullable(errorDetails);
    }

    @Override
    public String toString() {
        return "GenericResponse{" +
                "ok=" + ok +
                ", operationId=" + operationId +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericResponse that = (GenericResponse) o;
        return  Objects.equal(this.ok, that.ok) &&
                Objects.equal(this.operationId, that.operationId) &&
                Objects.equal(this.error, that.error) &&
                Objects.equal(this.errorDetails, that.errorDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, operationId, error, errorDetails);
    }
}
