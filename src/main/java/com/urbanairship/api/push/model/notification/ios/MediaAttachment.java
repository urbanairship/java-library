/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.model.notification.ios;

import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.Optional;

/**
 * MediaAttachment for iOS specific push messages.
 */
public final class MediaAttachment extends PushModelObject {

    private final String url;
    private final Optional<IOSMediaContent> content;
    private final Optional<IOSMediaOptions> options;

    private MediaAttachment(String url, Optional<IOSMediaContent> content, Optional<IOSMediaOptions> options) {
        this.url = url;
        this.content = content;
        this.options = options;
    }

    /**
     * Get a MediaAttachment builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the url used for the iOS media
     * @return String representation of the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Get the Content object that describes portions of the notification that should be modified if the media attachment succeeds
     * @return Optional Content object
     */
    public Optional<IOSMediaContent> getContent() {
        return content;
    }

    /**
     * Get the IOSMediaOptions that describes how to display the resource at the URL
     * @return Optional IOSMediaOptions object
     */
    public Optional<IOSMediaOptions> getOptions() {
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
        private IOSMediaContent content = null;
        private IOSMediaOptions options = null;

        private Builder() { }

        /**
         * Set the url string for iOS media.
         * @param url String url
         * @return Builder
         */
        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        /**
         * Set the Content object that describes portions of the notification that should be modified if the media attachment succeeds.
         * @param content Content
         * @return Builder
         */
        public Builder setContent(IOSMediaContent content) {
            this.content = content;
            return this;
        }

        /**
         * Set the IOSMediaOptions object that describes how to display the resource at the URL specified.
         * @param options IOSMediaOptions
         * @return Builder
         */
        public Builder setOptions(IOSMediaOptions options) {
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
                                       Optional.ofNullable(content),
                                       Optional.ofNullable(options));
        }
    }
}