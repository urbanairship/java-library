package com.urbanairship.api.common.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

public class APIErrorDetails extends APIModelObject {

    private final Optional<String> path;
    private final String error;
    private final Optional<Location> location;

    private APIErrorDetails(Optional<String> path, String error, Optional<Location> location) {
        this.path = path;
        this.error = error;
        this.location = location;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<String> getPath() {
        return path;
    }

    public String getError() {
        return error;
    }

    public Optional<Location> getLocation() {
        return location;
    }

    public static class Builder {

        private String path = null;
        private String error = null;
        private Location location = null;

        private Builder() { }

        public Builder setPath(String value) {
            path = value;
            return this;
        }

        public Builder setError(String value) {
            error = value;
            return this;
        }

        public Builder setLocation(Location value) {
            location = value;
            return this;
        }

        public APIErrorDetails build() {
            Preconditions.checkNotNull(error);
            return new APIErrorDetails(Optional.fromNullable(path), error, Optional.fromNullable(location));
        }
    }

    /**
     * Records a location in the JSON input where a parsing error
     * occurred.
     */
    public static class Location {
        private final int line;
        private final int column;

        private Location(int line, int column) {
            this.line = line;
            this.column = column;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public int getLine() {
            return line;
        }

        public int getColumn() {
            return column;
        }

        public static class Builder {
            private int line = 0;
            private int column = 0;

            private Builder() { }

            public Builder setLine(int value) {
                line = value;
                return this;
            }

            public Builder setColumn(int value) {
                column = value;
                return this;
            }

            public Location build() {
                return new Location(line, column);
            }
        }
    }
}
