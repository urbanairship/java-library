package com.urbanairship.api.push.model.notification.email;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class Attachment {
    private final String id;

    private Attachment(Builder builder) {
        this.id = builder.id;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the id string which represents an email attachment.
     *
     * @return String
     */
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachment that = (Attachment) o;
        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private String id;

        /**
         * Set the id string which represents an email attachment.
         *
         * @param id String
         * @return Builder
         */
        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Attachment build() {
            Preconditions.checkNotNull(id, "The attachment id must be set.");
            return new Attachment(this);
        }
    }
}
