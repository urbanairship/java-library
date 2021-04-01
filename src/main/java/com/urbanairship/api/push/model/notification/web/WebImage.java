package com.urbanairship.api.push.model.notification.web;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class WebImage {
    private final String url;

    public WebImage(String url) {
        Preconditions.checkNotNull(url, "Url cannot be null.");
        this.url = url;
    }

    private WebImage(Builder builder) {
        this.url = builder.url;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebImage webImage = (WebImage) o;
        return Objects.equal(url, webImage.url);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(url);
    }

    public static class Builder {
        String url = null;

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public WebImage build() {
            return new WebImage(this);
        }
    }
}
