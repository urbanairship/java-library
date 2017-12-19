/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model;

import com.google.common.collect.ImmutableList;

import java.util.Objects;


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

    private Campaigns(ImmutableList<String> categories){
        this.categories = categories;
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
        return Objects.hash(categories);
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
        return Objects.equals(this.categories, other.categories);
    }

    public static class Builder{
        private ImmutableList.Builder<String> categories = ImmutableList.builder();

        private Builder() {
        }

        /**
         * Add category
         *
         * @param value String
         * @return Builder
         */
        public Builder addCategory(String category) {
            this.categories.add(category);
            return this;
        }

        /**
         * Add list of categories
         *
         * @param value Iterable of categories
         * @return Builder
         */
        public Builder addAllCategories(Iterable<? extends String> categories){
            this.categories.addAll(categories);
            return this;
        }

        public Campaigns build(){
            return new Campaigns(categories.build());
        }
    }


}
