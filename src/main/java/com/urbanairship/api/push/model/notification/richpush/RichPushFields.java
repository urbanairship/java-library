package com.urbanairship.api.push.model.notification.richpush;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.base.Objects;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.Optional;

@JsonDeserialize(builder = RichPushFields.Builder.class)
public class RichPushFields extends PushModelObject {
    private final Optional<String> body;
    private final Optional<String> title;

    private RichPushFields(Builder builder) {
        this.body = Optional.ofNullable(builder.body);
        this.title = Optional.ofNullable(builder.title);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Set the body.
     *
     *
     * @return Optional String body.
     */
    @JsonProperty("body")
    public Optional<String> getBody() {
        return body;
    }

    /**
     * Set the title.
     *
     *
     * @return Optional String title.
     */
    @JsonProperty("title")
    public Optional<String> getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RichPushFields that = (RichPushFields) o;
        return Objects.equal(body, that.body) &&
                Objects.equal(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(body, title);
    }

    @Override
    public String toString() {
        return "RichPushFields{" +
                "body=" + body +
                ", title=" + title +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        String body = null;
        String title = null;

        /**
         * Set the body.
         *
         * @param body String
         * @return Builder
         */
        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        /**
         * Set the title.
         *
         * @param title String
         * @return Builder
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public RichPushFields build() {
            return new RichPushFields(this);
        }
    }
}
