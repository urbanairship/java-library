/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.actions;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.push.model.PushModelObject;
import com.google.common.base.Objects;
import com.google.common.base.Optional;

public final class LandingPageContent extends PushModelObject {
    private final Optional<Encoding> encoding;
    private final String body;
    private final String contentType;

    private static int byteLengthBase64Encoded(int bytes) {
        return (int)Math.ceil(bytes / 3.0) * 4;
    }

    /* the following are the absolute size limits */
    public static final int MAX_BODY_SIZE_BYTES = 512 * 1024;
    public static final int MAX_BODY_SIZE_BASE64 = byteLengthBase64Encoded(MAX_BODY_SIZE_BYTES);

    /* the following are the size limits beyond which CDN / image hosting entitlement is required */
    public static final int LARGE_BODY_SIZE_BYTES = 256 * 1024;
    public static final int LARGE_BODY_SIZE_BASE64 = byteLengthBase64Encoded(LARGE_BODY_SIZE_BYTES);

    public static final ImmutableSet<String> ALLOWED_CONTENT_TYPES = ImmutableSet.of(
            // Images
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/tiff",
            // Text
            "text/html",
            "text/plain",
            "application/json",
            "application/x-javascript",
            "text/javascript",
            "text/x-javascript",
            "text/x-json",
            // Documents
            "application/vnd.ms-excel",
            "application/msexcel",
            "application/x-msexcel",
            "application/x-ms-excel",
            "application/x-excel",
            "application/x-dos_ms_excel",
            "application/xls",
            "application/x-xls",
            "application/rtf",
            "application/x-rtf",
            "text/richtext",
            "application/pdf",
            "application/msexcel",
            "application/mspowerpoint",
            "application/msword",
            "application/vnd.apple.keynote",
            "application/vnd.apple.pages",
            "application/vnd.apple.numbers",
            "application/x-iwork-keynote-sffkey",
            "application/x-iwork-pages-sffpages",
            "application/x-iwork-numbers-sffnumbers"
    );

    private LandingPageContent(String contentType, Optional<Encoding> encoding, String body) {
        this.contentType = contentType;
        this.encoding = encoding;
        this.body = body;
    }

    public boolean requiresImageHostingEntitlement() {
        final int large_size = (encoding.isPresent() && encoding.get() == Encoding.Base64) ? LARGE_BODY_SIZE_BASE64 : LARGE_BODY_SIZE_BYTES;
        return body.length() > large_size;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return newBuilder().mergeFrom(this);
    }

    public String getContentType() {
        return contentType;
    }

    public Optional<Encoding> getEncoding() {
        return encoding;
    }

    public String getBody() {
        return body;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(encoding, body, contentType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final LandingPageContent other = (LandingPageContent) obj;
        return Objects.equal(this.encoding, other.encoding)
                && Objects.equal(this.body, other.body)
                && Objects.equal(this.contentType, other.contentType);
    }

    @Override
    public String toString() {
        return "LandingPageContent{" +
                "encoding=" + encoding +
                ", body='" + body + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }

    public enum Encoding {
        UTF8,
        Base64;
    }

    public static final class Builder {
        private Optional<Encoding> encoding = Optional.absent();
        private Optional<String> body = Optional.absent();
        private Optional<String> contentType = Optional.absent();

        private Builder() { }

        public Builder mergeFrom(LandingPageContent other) {
            encoding = other.getEncoding();
            return setBody(other.getBody())
                    .setContentType(other.getContentType());
        }

        public Builder setEncoding(Encoding encoding) {
            this.encoding = Optional.of(encoding);
            return this;
        }

        public Builder setEncoding(Optional<Encoding> encoding) {
            this.encoding = encoding;
            return this;
        }

        public Builder setBody(String body) {
            this.body = Optional.of(body);
            return this;
        }

        public Builder setContentType(String contentType) {
            this.contentType = Optional.of(contentType);
            return this;
        }

        public LandingPageContent build() {

            if (!body.isPresent()) {
                throw new RuntimeException("Content needs a body.");
            }

            if (!contentType.isPresent()) {
                throw new RuntimeException("Content needs contentType.");
            }

            Preconditions.checkNotNull(encoding, "encoding should not be null.");
            return new LandingPageContent(contentType.get(), encoding, body.get());
        }
    }
}
