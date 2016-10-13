package com.urbanairship.api.push.model.notification.ios;

import com.google.common.base.Optional;
import com.urbanairship.api.push.model.PushModelObject;

/**
 * Created by devinsmythe on 9/16/16.
 */
public class Content extends PushModelObject{
    private final Optional<String> title;
    private final Optional<String> body;
    private final Optional<String> subtitle;

    private Content(Optional<String> title, Optional<String> body, Optional<String> subtitle) {
        this.title = title;
        this.body = body;
        this.subtitle = subtitle;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<String> getTitle() {
        return title;
    }

    public Optional<String> getBody() {
        return body;
    }

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

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Builder setSubtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public Content build() {
            return new Content(Optional.fromNullable(title),
                               Optional.fromNullable(body),
                               Optional.fromNullable(subtitle));
        }
    }
}