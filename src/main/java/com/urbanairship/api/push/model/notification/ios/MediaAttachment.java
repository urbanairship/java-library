/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.model.notification.ios;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.push.model.PushModelObject;

/**
 * MediaAttachment for iOS specific push messages.
 */
public final class MediaAttachment extends PushModelObject {

    private final String url;
    private final Optional<Content> content;
    private final Optional<Options> options;

    private MediaAttachment(String url, Optional<Content> content, Optional<Options> options) {
        this.url = url;
        this.content = content;
        this.options = options;
    }

    /**
     * Get an MediaAttachment
     * @return IOSPayloadBuilder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the url
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Get the Content
     * @return Content
     */
    public Optional<Content> getContent() {
        return content;
    }

    /**
     * Get the Options
     * @return Options
     */
    public Optional<Options> getOptions() {
        return options;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MediaAttachment that = (MediaAttachment)o;
        if (url != null ? !url.equals(that.url) : that.url != null) {
            return false;
        }
        if (content != null ? !content.equals(that.content) : that.content != null) {
            return false;
        }
        if (options != null ? !options.equals(that.options) : that.options != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (url != null ? url.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (options != null ? options.hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){
        return "MediaAttachment{" +
                "content=" + content +
                ", options=" + options;
    }

    public static class Builder{
        private String url = null;
        private Content content = null;
        private Options options = null;

        private Builder() { }

        /**
         * Set the url string.
         * @param url String url
         * @return Builder
         */
        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        /**
         * Set the Content object.
         * @param content Content
         * @return Builder
         */
        public Builder setContent(Content content) {
            this.content = content;
            return this;
        }

        /**
         * Set the Options object.
         * @param options Options
         * @return Builder
         */
        public Builder setOptions(Options options) {
            this.options = options;
            return this;
        }

        /**
         * Build MediaAttachment
         * @return MediaAttachment
         */
        public MediaAttachment build() {
            Preconditions.checkNotNull(url, "'url' must be set");

            return new MediaAttachment(url,
                                       Optional.fromNullable(content),
                                       Optional.fromNullable(options));
        }
    }
}