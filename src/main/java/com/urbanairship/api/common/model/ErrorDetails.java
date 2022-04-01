package com.urbanairship.api.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.google.common.base.Optional;

public final class ErrorDetails extends APIModelObject {
    private final String error;
    private final String path;

    public ErrorDetails(@JsonProperty("error") String error, @JsonProperty("path") String path) {
        this.error = error;
        this.path = path;
    }

    /**
     * Get the error details
     *
     * @return String error
     */
    public Optional<String> getError() {
        return Optional.fromNullable(error);
    }

    /**
     * Get the path error details
     *
     * @return String path
     */
    public Optional<String> getPath() {
        return Optional.fromNullable(path);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ErrorDetails that = (ErrorDetails) o;
        return Objects.equal(error, that.error) && Objects.equal(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(error, path);
    }

    @Override
    public String toString() {
        return "ErrorDetails{" +
            "error=" + error +
            ", path=" + path +
            '}';
    }
}