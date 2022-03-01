package com.urbanairship.api.common.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

public final class ErrorDetails extends APIModelObject {
    private final Optional<String> error;
    private final Optional<String> path;

    private ErrorDetails(String error, String path) {
        this.error = Optional.fromNullable(error);
        this.path = Optional.fromNullable(path);
    }

    /**
     * ErrorDetails Builder
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the error details
     *
     * @return String error
     */
    public Optional<String> getError() {
        return error;
    }

    /**
     * Get the path error details
     *
     * @return String path
     */
    public Optional<String> getPath() {
        return path;
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

    public static class Builder {
        String error = null;
        String path = null;

        /**
         * Set the error details.
         *
         * @param error String
         * @return Builder
         */
        public Builder setError(String error) {
            this.error = error;
            return this;
        }

        /**
         * Set the path error details.
         *
         * @param path String
         * @return Builder
         */
        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public ErrorDetails build() {
            return new ErrorDetails(error, path);
        }
    }
}
