package com.urbanairship.api.sms.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import java.util.Optional;

/**
 * Represents the payload to be used for creating slides object.
 */
public class MmsSlides {
    private final Optional<String> text;
    private final String mediaUrl;
    private final String mediaContentType;
    private final Optional<Integer> mediaContentLength;

    //Protected to facilitate subclassing for create and send child object
    protected MmsSlides(Builder builder) {
        this.text = Optional.ofNullable(builder.text);
        this.mediaUrl = builder.mediaUrl;
        this.mediaContentType = builder.mediaContentType;
        this.mediaContentLength = Optional.ofNullable(builder.mediaContentLength);

    }

    /**
     * Get the MmsSlides text.
     *
     * @return String text
     */
    public Optional<String> getText() {
        return text;
    }

    /**
     * Get the mediaUrl.
     *
     * @return String mediaUrl
     */
    public String getMediaUrl() {
        return mediaUrl;
    }

    /**
     * Get the mediaContentType value.
     *
     * @return String mediaContentType
     */
    public String getMediaContentType() {
        return mediaContentType;
    }

    /**
     * Get the MmsSlides mediaContentLength.
     *
     * @return Integer mediaContentLength
     */
    public Optional<Integer> getMediaContentLength() {
        return mediaContentLength;
    }

    /**
     * New MmsSlides builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MmsSlides that = (MmsSlides) o;
        return text == that.text &&
                Objects.equal(mediaUrl, that.mediaUrl) &&
                Objects.equal(mediaContentType, that.mediaContentType) &&
                Objects.equal(mediaContentLength, that.mediaContentLength);

    }

    @Override
    public String toString() {
        return "MmsSlides{" +
                "text=" + text +
                ", mediaUrl=" + mediaUrl +
                ", mediaContentType=" + mediaContentType +
                ", mediaContentLength=" + mediaContentLength +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text, mediaUrl, mediaContentType, mediaContentLength);
    }

    /**
     * Create MmsSlides Builder
     */
    public final static class Builder {
        private String text;
        private String mediaUrl;
        private String mediaContentType;
        private Integer mediaContentLength;

        protected Builder() {
        }

        /**
         * Set the text
         *
         * @param text String
         * @return MmsSlides Builder
         */
        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        /**
         * Set the mediaUrl
         *
         * @param mediaUrl String
         * @return MmsSlides Builder
         */
        public Builder setMediaUrl(String mediaUrl) {
            this.mediaUrl = mediaUrl;
            return this;
        }

        /**
         * Set mediaContentType value
         *
         * @param mediaContentType String
         * @return Channel Builder
         */
        public Builder setMediaContentType(String mediaContentType) {
            this.mediaContentType = mediaContentType;
            return this;
        }

        /**
         * Set mediaContentType value
         *
         * @param mediaContentLength Integer
         * @return Channel Builder
         */
        public Builder setMediaContentLength(Integer mediaContentLength) {
            this.mediaContentLength = mediaContentLength;
            return this;
        }

        public MmsSlides build() {
            Preconditions.checkNotNull(mediaUrl, "mediaUrl cannot be null.");
            Preconditions.checkNotNull(mediaContentType, "mediaContentType cannot be null.");

            return new MmsSlides(this);
        }
    }
}
