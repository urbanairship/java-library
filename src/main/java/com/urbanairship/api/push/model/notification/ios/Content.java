/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.model.notification.ios;

import com.google.common.base.Optional;
import com.urbanairship.api.push.model.PushModelObject;

/**
 * Content for iOS specific media push messages.
 */
public final class Content extends PushModelObject{
    private final Optional<String> title;
    private final Optional<String> body;
    private final Optional<String> subtitle;

    private Content(Optional<String> title, Optional<String> body, Optional<String> subtitle) {
        this.title = title;
        this.body = body;
        this.subtitle = subtitle;
    }

    /**
     * Get a Content builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the title.
     * @return Optional string representing the title
     */
    public Optional<String> getTitle() {
        return title;
    }

    /**
     * Get the body.
     * @return Optional string representing the body
     */
    public Optional<String> getBody() {
        return body;
    }

    /**
     * Get the subtitle.
     * @return Optional string representing the body
     */
    public Optional<String> getSubtitle() {
        return subtitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Content that = (Content)o;
        if (title != null ? !title.equals(that.title) : that.title != null) {
            return false;
        }
        if (body != null ? !body.equals(that.body) : that.body != null) {
            return false;
        }
        if (subtitle != null ? !subtitle.equals(that.subtitle) : that.subtitle != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (title != null ? title.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (subtitle != null ? body.hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){
        return "Content{" +
                "title=" + title +
                ", body=" + body +
                ", subtitle=" + subtitle +
                '}';
    }

    public static class Builder{
        private String title = null;
        private String body = null;
        private String subtitle = null;

        private Builder() { }

        /**
         * Set the title
         * @param title String
         * @return Builder
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set the body
         * @param body String
         * @return Builder
         */
        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        /**
         * Set the subtitle
         * @param subtitle String
         * @return Builder
         */
        public Builder setSubtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        /**
         * Build Content
         * @return Content
         */
        public Content build() {
            return new Content(Optional.fromNullable(title),
                               Optional.fromNullable(body),
                               Optional.fromNullable(subtitle));
        }
    }
}