/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.templates.model;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.common.model.ErrorDetails;

import java.util.List;
import java.util.Optional;

/**
 * Template listing response object.
 */
public class TemplateListingResponse {

    private final boolean ok;
    private final Optional<TemplateView> template;
    private final Optional<ImmutableList<TemplateView>>  templates;
    private final Optional<Integer> count;
    private final Optional<Integer> totalCount;
    private final Optional<String> nextPage;
    private final Optional<String> prevPage;
    private final Optional<String> error;
    private final Optional<ErrorDetails> errorDetails;

    private TemplateListingResponse(Builder builder) {
        this.ok = builder.ok;
        this.template = Optional.ofNullable(builder.template);
        this.templates = Optional.ofNullable(builder.templates.build());
        this.count = Optional.ofNullable(builder.count);
        this.totalCount = Optional.ofNullable(builder.totalCount);
        this.nextPage = Optional.ofNullable(builder.nextPage);
        this.prevPage = Optional.ofNullable(builder.prevPage);
        this.error = Optional.ofNullable(builder.error);
        this.errorDetails = Optional.ofNullable(builder.errorDetails);
    }

    /**
     * Return a template listing response Builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the ok status of the TemplateListingResponse.
     *
     * @return boolean representing ok status
     */
    public boolean getOk() {
        return ok;
    }

    /**
     * Get the TemplateView object for a template lookup  request.
     *
     * @return An optional TemplateView object
     */
    public Optional<TemplateView> getTemplate() {
        return template;
    }

    /**
     * Get an ImmutableList of TemplateView objects for a template listing request.
     *
     * @return An optional ImmutableList of TemplateView objects
     */
    public Optional<ImmutableList<TemplateView>> getTemplates() {
        return templates;
    }

    /**
     * Get the number of returned templates for a template listing request.
     *
     * @return An optional Integer counting the number of returned templates
     */
    public Optional<Integer> getCount() {
        return count;
    }

    /**
     * Get the total number of templates associated to an application.
     *
     * @return An optional Integer counting the total number of templates associated with an application
     */
    public Optional<Integer> getTotalCount() {
        return totalCount;
    }

    /**
     * Get the next page string for a template listing request.
     *
     * @return An optional string representing the next page URL
     */
    public Optional<String> getNextPage() {
        return nextPage;
    }

    /**
     * Get the previous page string for a template listing request.
     *
     * @return An optional string representing the previous page URL
     */
    public Optional<String> getPrevPage() {
        return prevPage;
    }

    /**
     * Get the error if present
     *
     * @return An Optional String
     */
    public Optional<String> getError() {
        return error;
    }

    /**
     * Get the error details if present
     *
     * @return An Optional String
     */
    public Optional<ErrorDetails> getErrorDetails() {
        return errorDetails;
    }

    @Override
    public String toString() {
        return "TemplateListingResponse{" +
                "ok=" + ok +
                ", template=" + template +
                ", templates=" + templates +
                ", count=" + count +
                ", totalCount=" + totalCount +
                ", nextPage=" + nextPage +
                ", prevPage=" + prevPage +
                ", error=" + error +
                ", errorDetails=" + errorDetails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemplateListingResponse that = (TemplateListingResponse) o;

        if (ok != that.ok) return false;
        if (!count.equals(that.count)) return false;
        if (!nextPage.equals(that.nextPage)) return false;
        if (!prevPage.equals(that.prevPage)) return false;
        if (!template.equals(that.template)) return false;
        if (!templates.equals(that.templates)) return false;
        if (!totalCount.equals(that.totalCount)) return false;
        if (!error.equals(that.error)) return false;
        if (!errorDetails.equals(that.errorDetails)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (ok ? 1 : 0);
        result = 31 * result + template.hashCode();
        result = 31 * result + templates.hashCode();
        result = 31 * result + count.hashCode();
        result = 31 * result + totalCount.hashCode();
        result = 31 * result + nextPage.hashCode();
        result = 31 * result + prevPage.hashCode();
        result = 31 * result + error.hashCode();
        result = 31 * result + errorDetails.hashCode();
        return result;
    }

    public static class Builder {
        private boolean ok;
        private TemplateView template = null;
        private ImmutableList.Builder<TemplateView> templates = ImmutableList.builder();
        private Integer count = null;
        private Integer totalCount = null;
        private String nextPage = null;
        private String prevPage = null;
        private String error = null;
        private ErrorDetails errorDetails = null;

        /**
         * Set the ok status.
         *
         * @param ok boolean
         * @return Builder
         */
        public Builder setOk(boolean ok) {
            this.ok = ok;
            return this;
        }

        /**
         * Set the single template object for a template lookup.
         *
         * @param template A TemplateView  object
         * @return Builder
         */
        public Builder setTemplate(TemplateView template) {
            this.template = template;
            return this;
        }

        /**
         * Set the list of template objects for a template listing.
         *
         * @param templates An ImmutableList of TemplateView objects
         * @return Builder
         */
        public Builder setTemplates(List<TemplateView> templates) {
            this.templates.addAll(templates);
            return this;
        }

        /**
         * Set the number of template views returned by a template listing.
         *
         * @param count Integer count of template views currently listed
         * @return Builder
         */
        public Builder setCount(Integer count) {
            this.count = count;
            return this;
        }

        /**
         * Set the total number of template views associated with an app.
         *
         * @param totalCount Integer count of total template views
         * @return Builder
         */
        public Builder setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
            return this;
        }

        /**
         * Set the next page url for a template listing request.
         *
         * @param nextPage String representing next page
         * @return Builder
         */
        public Builder setNextPage(String nextPage) {
            this.nextPage = nextPage;
            return this;
        }

        /**
         * Set the previous page url for a template listing request.
         *
         * @param prevPage String representing previous page
         * @return Builder
         */
        public Builder setPrevPage(String prevPage) {
            this.prevPage = prevPage;
            return this;
        }

        /**
         * Set the error
         *
         * @param error String
         * @return Builder
         */
        public Builder setError(String error) {
            this.error = error;
            return this;
        }

        /**
         * Set the errorDetails
         *
         * @param errorDetails String
         * @return Builder
         */
        public Builder setErrorDetails(ErrorDetails errorDetails) {
            this.errorDetails = errorDetails;
            return this;
        }

        /**
         * Build the TemplateListingResponse object.
         *
         * <pre>
         *     1. If templates is set, verify that template is not set, and that count and totalCount are not null.
         * </pre>
         *
         * @return TemplateListingResponse
         */
        public TemplateListingResponse build() {
            if (!templates.build().isEmpty()) {
                Preconditions.checkArgument(this.template == null && this.count != null && this.totalCount != null);
            }
            return new TemplateListingResponse(this);
        }
    }
}
