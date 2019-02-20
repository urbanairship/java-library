/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */
package com.urbanairship.api.push.model.notification.android;


import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

/**
 * Represents an Android style with a type of "big_picture".
 */
public class BigPictureStyle implements Style<String> {
    private final String content;
    private final Optional<String> title;
    private final Optional<String> summary;

    private BigPictureStyle(Builder builder) {
        this.content = builder.content;
        this.title = Optional.fromNullable(builder.title);
        this.summary = Optional.fromNullable(builder.summary);
    }

    /**
     * Return a new BigPictureStyle builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Return the content associated with the "big_picture" key.
     *
     * @return String path to image
     */
    @Override
    public String getContent() {
        return content;
    }

    /**
     * Return the Type.BIG_PICTURE style type.
     *
     * @return Type.BIG_PICTURE
     */
    @Override
    public Type getType() {
        return Type.BIG_PICTURE;
    }

    /**
     * Return an optional title string.
     *
     * @return Optional String representing the style title
     */
    @Override
    public Optional<String> getTitle() {
        return title;
    }

    /**
     * Return an optional summary string.
     *
     * @return Optional String representing the style summary.
     */
    @Override
    public Optional<String> getSummary() {
        return summary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BigPictureStyle that = (BigPictureStyle) o;

        if (!content.equals(that.content)) return false;
        if (!summary.equals(that.summary)) return false;
        if (!title.equals(that.title)) return false;

        return true;
    }
    @Override
    public String toString() {
        return "BigPictureStyle{" +
                "content=" + content +
                ", title=" + title +
                ", summary=" + summary +
                '}';
    }

    @Override
    public int hashCode() {
        int result = content.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + summary.hashCode();
        return result;
    }


    public static class Builder {
        private String content = null;
        private String title = null;
        private String summary = null;

        /**
         * Set the "big_picture" String.
         *
         * @param content String
         * @return Builder
         */
        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        /**
         * Set the title String.
         *
         * @param title String
         * @return Builder
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set the summary String.
         *
         * @param summary String
         * @return Builder
         */
        public Builder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        /**
         * Build a BigPictureStyle object.
         *
         * <pre>
         *     1. The content field cannot be null.
         * </pre>
         *
         * @return BigPictureStyle
         */
        public BigPictureStyle build() {
            Preconditions.checkNotNull(content, "The content field cannot be null.");
            return new BigPictureStyle(this);
        }
    }
}