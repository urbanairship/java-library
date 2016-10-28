/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.wns;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public class WNSBinding {
    private final String template;
    private final Optional<Integer> version;
    private final Optional<String> fallback;
    private final Optional<String> lang;
    private final Optional<String> baseUri;
    private final Optional<Boolean> addImageQuery;
    private final Optional<ImmutableList<String>> images;
    private final Optional<ImmutableList<String>> text;

    private WNSBinding(String template,
                       Optional<Integer> version,
                       Optional<String> fallback,
                       Optional<String> lang,
                       Optional<String> baseUri,
                       Optional<Boolean> addImageQuery,
                       Optional<ImmutableList<String>> images,
                       Optional<ImmutableList<String>> text)
    {
        this.template = template;
        this.version = version;
        this.fallback = fallback;
        this.lang = lang;
        this.baseUri = baseUri;
        this.addImageQuery = addImageQuery;
        this.images = images;
        this.text = text;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getTemplate() {
        return this.template;
    }

    public Optional<Integer> getVersion() {
        return this.version;
    }

    public Optional<String> getFallback() {
        return this.fallback;
    }

    public Optional<String> getLang() {
        return this.lang;
    }

    public Optional<String> getBaseUri() {
        return this.baseUri;
    }

    public Optional<Boolean> getAddImageQuery() {
        return this.addImageQuery;
    }

    public int getImageCount() {
        return images.isPresent() ? images.get().size() : 0;
    }

    public String getImage(int i) {
        return images.get().get(i);
    }

    public Optional<ImmutableList<String>> getImages() {
        return this.images;
    }

    public int getTextCount() {
        return text.isPresent() ? text.get().size() : 0;
    }

    public String getText(int i) {
        return text.get().get(i);
    }

    public Optional<ImmutableList<String>> getText() {
        return this.text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WNSBinding that = (WNSBinding)o;
        if (template != null ? !template.equals(that.template) : that.template != null) {
            return false;
        }
        if (version != null ? !version.equals(that.version) : that.version != null) {
            return false;
        }
        if (fallback != null ? !fallback.equals(that.fallback) : that.fallback != null) {
            return false;
        }
        if (lang != null ? !lang.equals(that.lang) : that.lang != null) {
            return false;
        }
        if (baseUri != null ? !baseUri.equals(that.baseUri) : that.baseUri != null) {
            return false;
        }
        if (addImageQuery != null ? !addImageQuery.equals(that.addImageQuery) : that.addImageQuery != null) {
            return false;
        }
        if (images != null ? !images.equals(that.images) : that.images != null) {
            return false;
        }
        if (text != null ? !text.equals(that.text) : that.text != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (template != null ? template.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (fallback != null ? fallback.hashCode() : 0);
        result = 31 * result + (lang != null ? lang.hashCode() : 0);
        result = 31 * result + (baseUri != null ? baseUri.hashCode() : 0);
        result = 31 * result + (addImageQuery != null ? addImageQuery.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    public static class Builder {

        private String template;
        private Integer version;
        private String fallback;
        private String lang;
        private String baseUri;
        private Boolean addImageQuery;
        private ImmutableList.Builder<String> images;
        private ImmutableList.Builder<String> text;

        private Builder() { }

        public Builder setTemplate(String value) {
            this.template = value;
            return this;
        }

        public Builder setVersion(Integer value) {
            this.version = value;
            return this;
        }

        public Builder setFallback(String value) {
            this.fallback = value;
            return this;
        }

        public Builder setLang(String value) {
            this.lang = value;
            return this;
        }

        public Builder setBaseUri(String value) {
            this.baseUri = value;
            return this;
        }

        public Builder setAddImageQuery(boolean value) {
            this.addImageQuery = value;
            return this;
        }

        public Builder addImage(String value) {
            if (this.images == null) {
                this.images = ImmutableList.builder();
            }
            this.images.add(value);
            return this;
        }

        public Builder addAllImages(Iterable<String> values) {
            if (this.images == null) {
                this.images = ImmutableList.builder();
            }
            this.images.addAll(values);
            return this;
        }

        public Builder addText(String value) {
            if (this.text == null) {
                this.text = ImmutableList.builder();
            }
            this.text.add(value);
            return this;
        }

        public Builder addAllText(Iterable<String> values) {
            if (this.text == null) {
                this.text = ImmutableList.builder();
            }
            this.text.addAll(values);
            return this;
        }

        public WNSBinding build() {
            if (template == null) {
                throw new IllegalArgumentException("Must supply a value for 'template'.");
            }
            return new WNSBinding(template,
                                  Optional.fromNullable(version),
                                  Optional.fromNullable(fallback),
                                  Optional.fromNullable(lang),
                                  Optional.fromNullable(baseUri),
                                  Optional.fromNullable(addImageQuery),
                                  Optional.fromNullable(images != null ? images.build() : null),
                                  Optional.fromNullable(text != null ? text.build() : null));
        }
    }
}
