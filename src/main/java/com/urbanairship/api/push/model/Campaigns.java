/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.collect.ImmutableList;

import java.util.Objects;

/**
 * The Campaigns Object associates the notification
 * with the categories that you add in the categories field.
 */
public class Campaigns {
    private final ImmutableList<String> categories;

    /**
     * Generate a new Campaign Builder object.
     *
     * @return Builder
     */
    public static Builder newBuilder(){
        return new Builder();
    }

    private Campaigns(Builder builder) {
        this.categories = builder.categories.build();
    }

    /**
     * Get list of Categories
     *
     * @return ImmutableList of categories
     */
    public ImmutableList<String> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "Campaigns{" +
                "categories=" + categories +
                '}';
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(categories);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Campaigns other = (Campaigns) obj;
        return com.google.common.base.Objects.equal(this.categories, other.categories);
    }

    /**
     * Campaigns Builder
     */
    public static class Builder{
        private ImmutableList.Builder<String> categories = ImmutableList.builder();

       private Builder() { }

        /**
         * Add category
         *
         * @param category String
         * @return Builder
         */
        public Builder addCategory(String category) {
            this.categories.add(category);
            return this;
        }

        /**
         * Add list of categories
         *
         * @param categories Iterable of categories
         * @return Builder
         */
        public Builder addAllCategories(Iterable<? extends String> categories){
            this.categories.addAll(categories);
            return this;
        }

        public Campaigns build(){
            return new Campaigns(this);
        }
    }
}