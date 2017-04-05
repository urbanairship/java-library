package com.urbanairship.api.push.model.notification.web;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

/**
 * Represents the a icon to be used in web notify.
 */
public final class WebIcon extends PushModelObject {
    private final String url;

    private WebIcon(String url){
        this.url = url;
    }

    /**
     * New WebIcon Builder.
     *
     * @return WebIcon Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the URL to be used for the icon.
     *
     * @return Required String url
     */
    public String getUrl(){
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WebIcon webIcon = (WebIcon) o;

        return Objects.equal(url, webIcon.url);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(url);
    }

    @Override
    public String toString() {
        return "WebIcon{" +
                "url='" + url + '\'' +
                '}';
    }

    /**
     * WebIcon Builder.
     */
    public static class Builder {
        private String url;

        /**
         * Set the url String used for the WebIcon.
         *
         * @param url String
         * @return WebIcon Builder
         */
        public Builder setUrl(String url){
            this.url = url;
            return this;
        }

        public WebIcon build() {
            Preconditions.checkNotNull(url, "WebIcon payload requires a 'url' field");

            return new WebIcon(url);
        }
    }
}
